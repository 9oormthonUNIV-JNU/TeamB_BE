package com.example.gifty.service;

import com.example.gifty.dto.product.ProductResponseDTO;
import com.example.gifty.entity.Product;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.ProductNotExistException;
import com.example.gifty.repository.ProductJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {
    private final ProductJPARepository productJPARepository;

    public List<ProductResponseDTO.ProductDTO> findProductList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Product> pageContent = productJPARepository.findAll(pageable);

        List<ProductResponseDTO.ProductDTO> responseDTOs = pageContent.getContent().stream()
                .map(ProductResponseDTO.ProductDTO::new)
                .collect(Collectors.toList());

        return responseDTOs;
    }

    public ProductResponseDTO.ProductDetailDTO findProduct(int id) {
        Product product = productJPARepository.findById(id)
                .orElseThrow(() -> new ProductNotExistException(ErrorCode.PRODUCT_NOT_EXIST));
        return new ProductResponseDTO.ProductDetailDTO(product);
    }
}
