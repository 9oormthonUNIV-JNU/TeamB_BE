package com.example.gifty.dto.user;

import com.example.gifty.entity.Funding;
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
        private TokenDTO tokenInfo;
        private UserDTO userInfo;

        public KakaoLoginDTO(TokenDTO tokenInfo, User user) {
            this.tokenInfo = tokenInfo;
            this.userInfo = new UserDTO(user);
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

    @Getter
    @Setter
    public static class MyPageDTO {
        private int userId;
        private String nickname;
        private String profileImage;
        private int fundingId;
        private String productName;
        private String productImage;
        private int productPrice;
        private long progress;

        public MyPageDTO(User user, Funding funding) {
            this.userId = user.getId();
            this.nickname = user.getNickanme();
            this.profileImage = user.getProfileImage();
            this.fundingId = funding.getId();
            this.productName = funding.getProduct().getProductName();
            this.productImage = funding.getProduct().getProductImage();
            this.productPrice = funding.getProduct().getPrice();
            this.progress = funding.getTotalAmount() / funding.getProduct().getPrice();
        }
    }
}
