package com.example.gifty.dto.product;

import com.example.gifty.entity.Product;
import lombok.Getter;
import lombok.Setter;

public class ProductResponseDTO {
    @Getter
    @Setter
    public static class ProductDTO {
        private int productId;
        private String productName;
        private String productImage;
        private int productPrice;

        public ProductDTO(Product product) {
            this.productId = product.getId();
            this.productName = product.getProductName();
            this.productImage = product.getProductImage();
            this.productPrice = product.getPrice();
        }
    }

    @Getter
    @Setter
    public static class ProductDetailDTO {
        private int productId;
        private String productName;
        private String productImage;
        private int productPrice;
        private String productDescription;

        public ProductDetailDTO(Product product) {
            this.productId = product.getId();
            this.productName = product.getProductName();
            this.productImage = product.getProductImage();
            this.productPrice = product.getPrice();
            this.productDescription = product.getDescription();
        }
    }
}
