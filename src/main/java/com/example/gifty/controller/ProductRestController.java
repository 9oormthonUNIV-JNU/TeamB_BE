package com.example.gifty.controller;

import com.example.gifty.ApiResponse;
import com.example.gifty.dto.product.ProductResponseDTO;
import com.example.gifty.dto.wishlist.WishListRequestDTO;
import com.example.gifty.dto.wishlist.WishListResponseDTO;
import com.example.gifty.security.CustomUserDetails;
import com.example.gifty.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product API", description = "상품 관련 API")
@RequiredArgsConstructor
@RestController
public class ProductRestController {
    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> getProductList(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<ProductResponseDTO.ProductDTO> responseDTOs = productService.findProductList(page);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTOs));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        ProductResponseDTO.ProductDetailDTO responseDTO = productService.findProduct(id, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTO));
    }

    @GetMapping("/products/{id}/wishlists")
    public ResponseEntity<?> getProductWishList(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        WishListResponseDTO.ProductWishListDTO responseDTO = productService.findProductWishList(id, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTO));
    }

    @PostMapping("/products/{id}/wishlists")
    public ResponseEntity<?> postProductWishList(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) throws Exception {
        productService.createProductWishList(id, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.successWithNoContent());
    }

    @PutMapping("/products/{id}/wishlists")
    public ResponseEntity<?> putProductWishList(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) throws Exception {
        productService.updateProductWishList(id, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.successWithNoContent());
    }
}
