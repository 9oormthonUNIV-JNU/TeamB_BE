package com.example.gifty.service;

import com.example.gifty.dto.support.SupportRequestDTO;
import com.example.gifty.dto.support.SupportResponseDTO;
import com.example.gifty.entity.*;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.FundingNotExistException;
import com.example.gifty.exception.UserNotExistException;
import com.example.gifty.exception.WrongAmountException;
import com.example.gifty.repository.*;
import com.example.gifty.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class SupportService {
    private final UserJPARepository userJPARepository;
    private final FundingJPARepository fundingJPARepository;
    private final SupportJPARepository supportJPARepository;
    private final Funding_SupportJPARepository funding_supportJPARepository;
    private final NotificationJPARepository notificationJPARepository;

    @Value("${kakao-pay.secret-key}")
    private String kakaoPaySecretKey;

    @Value("${kakao-pay.ready-uri}")
    private String readyUri;

    @Value("${kakao-pay.approval-uri}")
    private String approvalUri;

    @Value("${kakao-pay.cancel-uri}")
    private String cancelUri;

    @Value("${kakao-pay.fail-uri}")
    private String failUri;

    @Value("${kakao-pay.approvement-uri}")
    private String approvementUri;

    private SupportResponseDTO.KakaoPayReadyDTO kakaoPayReadyDTO;


    public SupportResponseDTO.KakaoPayReadyDTO kakaoPayReady(SupportRequestDTO.SupportDTO requestDTO, CustomUserDetails userDetails) {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_EXIST));

        Funding funding = fundingJPARepository.findById(requestDTO.getFundingId())
                .orElseThrow(() -> new FundingNotExistException(ErrorCode.FUNDING_NOT_EXIST));

        if (requestDTO.getAmount() < 0 || funding.getTotalAmount() + requestDTO.getAmount() > funding.getProduct().getPrice()) {
            throw new WrongAmountException(ErrorCode.WRONG_AMOUNT);
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY " + kakaoPaySecretKey);
        headers.add("Content-Type", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "1");
        params.add("partner_user_id", String.valueOf(user.getId()));
        params.add("item_name", funding.getProduct().getProductName());
        params.add("quantity", "1");
        params.add("total_amount", String.valueOf(requestDTO.getAmount()));
        params.add("tax_free_amount", "0");
        params.add("approval_url", approvalUri);
        params.add("cancel_url", cancelUri);
        params.add("fail_url", failUri);

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyDTO = restTemplate.postForObject(readyUri, body, SupportResponseDTO.KakaoPayReadyDTO.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return kakaoPayReadyDTO;
    }

    public SupportResponseDTO.KakaoPayApprovementDTO kakaoPayApprovement(SupportRequestDTO.SupportDTO requestDTO, String pg_token, CustomUserDetails userDetails) {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_EXIST));

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY " + kakaoPaySecretKey);
        headers.add("Content-Type", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyDTO.getTid());
        params.add("partner_order_id", "1");
        params.add("partner_user_id", String.valueOf(user.getId()));
        params.add("pg_token", pg_token);

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        SupportResponseDTO.KakaoPayApprovementDTO responseDTO = new SupportResponseDTO.KakaoPayApprovementDTO();
        try {
            responseDTO = restTemplate.postForObject(approvementUri, body, SupportResponseDTO.KakaoPayApprovementDTO.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return responseDTO;
    }

    public void createSupport(SupportRequestDTO.SupportDTO requestDTO, CustomUserDetails userDetails) {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_EXIST));

        Funding funding = fundingJPARepository.findById(requestDTO.getFundingId())
                .orElseThrow(() -> new FundingNotExistException(ErrorCode.FUNDING_NOT_EXIST));

        Support newSupport = Support.builder()
                .user(user)
                .amount(requestDTO.getAmount())
                .message(requestDTO.getMessage())
                .build();

        Support support = supportJPARepository.save(newSupport);

        Funding updatedFunding = funding.updateTotalAmount(requestDTO.getAmount());

        Funding_Support new_funding_support = Funding_Support.builder()
                .funding(updatedFunding)
                .support(support)
                .build();

        Funding_Support funding_support = funding_supportJPARepository.save(new_funding_support);

        Notification newNotification = Notification.builder()
                .fundingSupport(funding_support)
                .isComplete(false)
                .build();

        notificationJPARepository.save(newNotification);

        if (updatedFunding.getTotalAmount() == updatedFunding.getProduct().getPrice()) {
            Notification fundingCompleteNotification = Notification.builder()
                    .fundingSupport(new_funding_support)
                    .isComplete(true)
                    .build();
            notificationJPARepository.save(fundingCompleteNotification);
        }
    }
}
