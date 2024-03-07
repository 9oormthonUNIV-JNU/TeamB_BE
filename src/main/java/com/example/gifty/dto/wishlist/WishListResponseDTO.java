package com.example.gifty.dto.wishlist;

import com.example.gifty.entity.WishList;
import lombok.Getter;
import lombok.Setter;

public class WishListResponseDTO {
    @Getter
    @Setter
    public static class ProductWishListDTO {
        private boolean isDeleted;

        public ProductWishListDTO(WishList wishList) {
            this.isDeleted = wishList.isDeleted();
        }
    }

    @Getter
    @Setter
    public static class WishListDTO {
        private int productId;
        private String productName;
        private String productImage;
        private int productPrice;

        public WishListDTO(WishList wishList) {
            this.productId = wishList.getProduct().getId();
            this.productName = wishList.getProduct().getProductName();
            this.productImage = wishList.getProduct().getProductImage();
            this.productPrice = wishList.getProduct().getPrice();
        }
    }
}
