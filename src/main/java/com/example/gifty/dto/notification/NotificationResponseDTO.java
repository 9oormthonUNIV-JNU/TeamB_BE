package com.example.gifty.dto.notification;

import com.example.gifty.entity.Funding_Support;
import com.example.gifty.entity.Notification;
import lombok.Getter;
import lombok.Setter;

public class NotificationResponseDTO {
    @Getter
    @Setter
    public static class NotificationDTO {
        private int friendId;
        private String nickname;
        private String profileImage;
        private int productId;
        private String productName;
        private boolean isComplete;

        public NotificationDTO(Notification notification) {
            this.friendId = notification.getFundingSupport().getSupport().getUser().getId();
            this.nickname = notification.getFundingSupport().getSupport().getUser().getNickanme();
            this.profileImage = notification.getFundingSupport().getSupport().getUser().getProfileImage();
            this.productId = notification.getFundingSupport().getFunding().getProduct().getId();
            this.productName = notification.getFundingSupport().getFunding().getProduct().getProductName();
            this.isComplete =notification.isComplete();
        }
    }

    @Getter
    @Setter
    public static class NotificationDetailDTO {
        private int friendId;
        private String nickname;
        private String profileImage;
        private String message;
        private long amount;
        private long progress;
        private long totalAmount;
        private int price;

        public NotificationDetailDTO(Notification notification) {
            this.friendId = notification.getFundingSupport().getSupport().getUser().getId();
            this.nickname = notification.getFundingSupport().getSupport().getUser().getNickanme();
            this.profileImage = notification.getFundingSupport().getSupport().getUser().getProfileImage();
            this.message = notification.getFundingSupport().getSupport().getMessage();
            this.amount = notification.getFundingSupport().getSupport().getAmount();
            this.progress = notification.getFundingSupport().getSupport().getAmount() / notification.getFundingSupport().getFunding().getProduct().getPrice();
            this.totalAmount = notification.getFundingSupport().getFunding().getTotalAmount();
            this.price = notification.getFundingSupport().getFunding().getProduct().getPrice();
        }
    }
}
