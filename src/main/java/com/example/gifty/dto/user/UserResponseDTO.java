package com.example.gifty.dto.user;

import com.example.gifty.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.Token;

import java.util.HashMap;

public class UserResponseDTO {
    @Getter
    @Setter
    public static class TokenDTO {
        private String accessToken;
        private String refreshToken;

        public TokenDTO(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    @Getter
    @Setter
    public static class KakaoLoginDTO {
        private TokenDTO tokenDTO;
        private UserDTO userDTO;

        public KakaoLoginDTO(TokenDTO tokenDTO, User user) {
            this.tokenDTO = tokenDTO;
            this.userDTO = new UserDTO(user);
        }

        @Getter
        @Setter
        public static class UserDTO {
            private int userId;
            private String email;
            private String nickname;
            private String profileImage;

            public UserDTO(User user) {
                this.userId = user.getId();
                this.email = user.getEmail();
                this.nickname = user.getNickanme();
                this.profileImage = user.getProfileImage();
            }
        }
    }
}
