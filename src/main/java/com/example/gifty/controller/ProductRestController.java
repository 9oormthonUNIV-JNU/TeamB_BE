package com.example.gifty.controller;

import com.example.gifty.ApiResponse;
import com.example.gifty.dto.product.ProductResponseDTO;
import com.example.gifty.security.CustomUserDetails;
import com.example.gifty.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<?> getProduct(@PathVariable int id) {
        ProductResponseDTO.ProductDetailDTO responseDTO = productService.findProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTO));
    }
}
