package com.example.gifty.dto.history;

import com.example.gifty.entity.History;
import com.example.gifty.entity.WishList;
import lombok.Getter;
import lombok.Setter;

public class HistoryResponseDTO {
    @Getter
    @Setter
    public static class HistoryDTO {
        private int productId;
        private String productName;
        private String productImage;
        private int productPrice;

        public HistoryDTO(History history) {
            this.productId = history.getProduct().getId();
            this.productName = history.getProduct().getProductName();
            this.productImage = history.getProduct().getProductImage();
            this.productPrice = history.getProduct().getPrice();
        }
    }
}
