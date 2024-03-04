package com.example.gifty.dto.user;

import com.example.gifty.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

public class UserResponseDTO {
    @Getter
    @Setter
    public static class KakaoLoginDTO {
        private String accessToken;
        private String refreshToken;

        public KakaoLoginDTO(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
