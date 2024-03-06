package com.example.gifty.controller;

import com.example.gifty.ApiResponse;
import com.example.gifty.dto.funding.FundingRequestDTO;
import com.example.gifty.dto.funding.FundingResponseDTO;
import com.example.gifty.security.CustomUserDetails;
import com.example.gifty.service.FundingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FundingRestController {
    private final FundingService fundingService;

    @PostMapping("/fundings")
    public ResponseEntity<?> postFunding(@RequestBody @Valid FundingRequestDTO.FundingDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) throws Exception {
        fundingService.createFunding(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.successWithNoContent());
    }

    @GetMapping("/fundings")
    public ResponseEntity<?> getFriendFundingList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<FundingResponseDTO.FundingDTO> responseDTOs = fundingService.findFriendFundingList(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTOs));
    }

    @GetMapping("/fundings/{id}")
    public ResponseEntity<?> getFriendFunding(@PathVariable int id) {
        FundingResponseDTO.FundingDetailDTO responseDTO = fundingService.findFriendFunding(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTO));
    }
}
