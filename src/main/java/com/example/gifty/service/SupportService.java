package com.example.gifty.service;

import com.example.gifty.dto.support.SupportRequestDTO;
import com.example.gifty.entity.Funding;
import com.example.gifty.entity.Funding_Support;
import com.example.gifty.entity.Support;
import com.example.gifty.entity.User;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.FundingNotExistException;
import com.example.gifty.exception.UserNotExistException;
import com.example.gifty.repository.FundingJPARepository;
import com.example.gifty.repository.Funding_SupportJPARepository;
import com.example.gifty.repository.SupportJPARepository;
import com.example.gifty.repository.UserJPARepository;
import com.example.gifty.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class SupportService {
    private final UserJPARepository userJPARepository;
    private final FundingJPARepository fundingJPARepository;
    private final SupportJPARepository supportJPARepository;
    private final Funding_SupportJPARepository funding_supportJPARepository;

    public void createSupport(SupportRequestDTO.SupportDTO requestDTO, CustomUserDetails userDetails) {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_EXIST));

        Support newSupport = Support.builder()
                .user(user)
                .amount(requestDTO.getAmount())
                .message(requestDTO.getMessage())
                .build();

        supportJPARepository.save(newSupport);

        Funding funding = fundingJPARepository.findById(requestDTO.getFundingId())
                .orElseThrow(() -> new FundingNotExistException(ErrorCode.FUNDING_NOT_EXIST));

        Funding_Support funding_support = Funding_Support.builder()
                .funding(funding)
                .support(newSupport)
                .build();

        funding_supportJPARepository.save(funding_support);
    }
}
