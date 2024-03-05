package com.example.gifty.service;

import com.example.gifty.dto.funding.FundingRequestDTO;
import com.example.gifty.dto.funding.FundingResponseDTO;
import com.example.gifty.entity.Funding;
import com.example.gifty.entity.Product;
import com.example.gifty.entity.User;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.FundingNotExistException;
import com.example.gifty.exception.ProductNotExistException;
import com.example.gifty.exception.UserNotExistException;
import com.example.gifty.repository.FundingJPARepository;
import com.example.gifty.repository.ProductJPARepository;
import com.example.gifty.repository.UserJPARepository;
import com.example.gifty.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class FundingService {
    private final UserJPARepository userJPARepository;
    private final ProductJPARepository productJPARepository;
    private final FundingJPARepository fundingJPARepository;

    public void createFunding(FundingRequestDTO.FundingDTO requestDTO, CustomUserDetails userDetails) throws Exception {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_EXIST));

        Product product = productJPARepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new ProductNotExistException(ErrorCode.PRODUCT_NOT_EXIST));

        Funding newFunding = Funding.builder()
                .user(user)
                .product(product)
                .event(requestDTO.getEvent())
                .startDate(requestDTO.getStartDate())
                .endDate(requestDTO.getEndDate())
                .message(requestDTO.getMessage())
                .totalAmount(0)
                .build();

        fundingJPARepository.save(newFunding);
    }

//    public List<FundingResponseDTO.FundingDTO> findFundingList(int page, CustomUserDetails userDetails) {
//        User user = userJPARepository.findById(userDetails.getUser().getId())
//                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_EXIST));
//
//    }

    public FundingResponseDTO.FundingDetailDTO findFunding(int id) {
        Funding funding = fundingJPARepository.findById(id)
                .orElseThrow(() -> new FundingNotExistException(ErrorCode.FUNDING_NOT_EXIST));

        return new FundingResponseDTO.FundingDetailDTO(funding);
    }
}
