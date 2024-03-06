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
}
