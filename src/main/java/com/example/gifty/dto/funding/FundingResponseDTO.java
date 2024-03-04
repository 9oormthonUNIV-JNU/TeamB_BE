package com.example.gifty.dto.funding;

import com.example.gifty.entity.Event;
import com.example.gifty.entity.Funding;
import lombok.Getter;
import lombok.Setter;

public class FundingResponseDTO {
    @Getter
    @Setter
    public static class FundingDTO {
        private int userId;
        private String userNickname;
        private Event event;
        private int productId;
        private String productName;
        private String productImage;
        private long progress;

        public FundingDTO(Funding funding) {
            this.userId = funding.getUser().getId();
            this.userNickname = funding.getUser().getNickanme();
            this.event = funding.getEvent();
            this.productId = funding.getProduct().getId();
            this.productName = funding.getProduct().getProductName();
            this.productImage = funding.getProduct().getProductImage();
            this.progress = funding.getTotalAmount() / funding.getProduct().getPrice();
        }
    }
}
