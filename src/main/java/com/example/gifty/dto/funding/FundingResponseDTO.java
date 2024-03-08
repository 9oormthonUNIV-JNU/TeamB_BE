package com.example.gifty.dto.funding;

import com.example.gifty.entity.Event;
import com.example.gifty.entity.Friend;
import com.example.gifty.entity.Funding;
import com.example.gifty.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class FundingResponseDTO {
    @Getter
    @Setter
    public static class FundingDTO {
        private int fundingId;
        private int friendId;
        private String friendNickname;
        private String friendProfileImage;
        private Event event;
        private int productId;
        private String productName;
        private String productImage;
        private long progress;

        public FundingDTO(Friend friend) {
            this.fundingId = -1;
            this.friendId = friend.getId();
            this.friendNickname = friend.getNickname();
            this.friendProfileImage = friend.getProfileImage();
            this.event = null;
            this.productId = -1;
            this.productName = null;
            this.productImage = null;
            this.progress = 0;
        }

        public FundingDTO(Friend friend, Funding funding) {
            this.fundingId = funding.getId();
            this.friendId = friend.getId();
            this.friendNickname = friend.getNickname();
            this.friendProfileImage = friend.getProfileImage();
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
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime startDate;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime endDate;
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
            this.startDate = funding.getStartDate();
            this.endDate = funding.getEndDate();
            this.message = funding.getMessage();
            this.totalAmount = funding.getTotalAmount();
            this.productPrice = funding.getProduct().getPrice();
            this.progress = funding.getTotalAmount() / funding.getProduct().getPrice();
        }
    }
}
