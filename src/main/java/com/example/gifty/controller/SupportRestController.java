package com.example.gifty.controller;

import com.example.gifty.ApiResponse;
import com.example.gifty.dto.support.SupportRequestDTO;
import com.example.gifty.dto.support.SupportResponseDTO;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.KakaopayCancelException;
import com.example.gifty.exception.KakaopayFailException;
import com.example.gifty.security.CustomUserDetails;
import com.example.gifty.service.SupportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class SupportRestController {
    private final SupportService supportService;

    @PostMapping("/supports/kakao-pay/ready")
    public ResponseEntity<?> kakaoPayReady(@RequestBody @Valid SupportRequestDTO.SupportDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) throws Exception {
        SupportResponseDTO.KakaoPayReadyDTO responseDTO = supportService.kakaoPayReady(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTO));
    }

    @GetMapping("/supports/kakao-pay/approvement")
    public ResponseEntity<?> kakaoPayApprovement(@RequestParam(value = "pg_token") String pg_token, @RequestBody @Valid SupportRequestDTO.SupportDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) throws Exception {
        SupportResponseDTO.KakaoPayApprovementDTO responseDTO = supportService.kakaoPayApprovement(requestDTO, pg_token, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTO));
    }

    @GetMapping("/supports/kakao-pay/cancel")
    public void kakaoPayCancel() {
        throw new KakaopayCancelException(ErrorCode.KAKAOPAY_CANCEL);
    }

    @GetMapping("/supports/kakao-pay/fail")
    public void kakaoPayFail() {
        throw new KakaopayFailException(ErrorCode.KAKAOPAY_FAIL);
    }

    @PostMapping("/supports")
    public ResponseEntity<?> postSupport(@RequestBody @Valid SupportRequestDTO.SupportDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) throws Exception {
        supportService.createSupport(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.successWithNoContent());
    }
}
