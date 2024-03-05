package com.example.gifty.dto.funding;

import com.example.gifty.entity.Event;
import com.example.gifty.entity.Funding;
import lombok.Getter;
import lombok.Setter;

public class FundingResponseDTO {
    @Getter
    @Setter
    public static class FundingDTO {
        private int fundingId;
        private int userId;
        private String userNickname;
        private String userProfileImage;
        private Event event;
        private int productId;
        private String productName;
        private String productImage;
        private long progress;

        public FundingDTO(Funding funding) {
            this.fundingId = funding.getId();
            this.userId = funding.getUser().getId();
            this.userNickname = funding.getUser().getNickanme();
            this.userProfileImage = funding.getUser().getProfileImage();
            this.event = funding.getEvent();
            this.productId = funding.getProduct().getId();
            this.productName = funding.getProduct().getProductName();
            this.productImage = funding.getProduct().getProductImage();
            this.progress = funding.getTotalAmount() / funding.getProduct().getPrice();
        }
    }

    @Getter
    @Setter
    public static class FundingDetailDTO {
        private int fundingId;
        private int userId;
        private String userNickname;
        private String userProfileImage;
        private int productId;
        private String productName;
        private String productImage;
        private String message;
        private long totalAmount;
        private long productPrice;
        private long progress;

        public FundingDetailDTO(Funding funding) {
            this.fundingId = funding.getId();
            this.userId = funding.getUser().getId();
            this.userNickname = funding.getUser().getNickanme();
            this.userProfileImage = funding.getUser().getProfileImage();
            this.productId = funding.getProduct().getId();
            this.productName = funding.getProduct().getProductName();
            this.productImage = funding.getProduct().getProductImage();
            this.message = funding.getMessage();
            this.totalAmount = funding.getTotalAmount();
            this.productPrice = funding.getProduct().getPrice();
            this.progress = funding.getTotalAmount() / funding.getProduct().getPrice();
        }
    }
}
