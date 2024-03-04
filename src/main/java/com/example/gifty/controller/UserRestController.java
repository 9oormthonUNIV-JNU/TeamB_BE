package com.example.gifty.controller;

import com.example.gifty.ApiResponse;
import com.example.gifty.dto.user.UserResponseDTO;
import com.example.gifty.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Tag(name = "User API", description = "사용자 관련 API")
@RequiredArgsConstructor
@RestController
public class UserRestController {
    private final UserService userService;

    @Operation(summary = "카카오 로그인", description = "카카오 로그인")
    @GetMapping("/users/login") // 테스트를 위해 GetMapping 사용. 이후에는 PostMapping으로 변경해야 함.
    public RedirectView kakaoAuthorizeCode() throws Exception {
        return userService.getKakaoAuthorizeCode();
    }

    @GetMapping("/users/kakao-login")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) throws Exception {
        UserResponseDTO.KakaoLoginDTO responseDTO = userService.kakaoLogin(code);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTO));
    }

}
