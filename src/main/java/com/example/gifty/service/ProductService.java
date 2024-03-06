package com.example.gifty.service;

import com.example.gifty.dto.product.ProductResponseDTO;
import com.example.gifty.dto.wishlist.WishListRequestDTO;
import com.example.gifty.dto.wishlist.WishListResponseDTO;
import com.example.gifty.entity.Product;
import com.example.gifty.entity.User;
import com.example.gifty.entity.WishList;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.ProductNotExistException;
import com.example.gifty.exception.UserNotExistException;
import com.example.gifty.exception.WishListNotExistException;
import com.example.gifty.repository.ProductJPARepository;
import com.example.gifty.repository.UserJPARepository;
import com.example.gifty.repository.WishListJPARepository;
import com.example.gifty.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {
    private final UserJPARepository userJPARepository;
    private final ProductJPARepository productJPARepository;
    private final WishListJPARepository wishListJPARepository;

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

    public WishListResponseDTO.ProductWishListDTO findProductWishList(int id, CustomUserDetails userDetails) {
        WishList wishList = wishListJPARepository.findByUserIdAndProductId(userDetails.getUser().getId(), id)
                .orElseThrow(() -> new WishListNotExistException(ErrorCode.WISHLIST_NOT_EXIST));
        return new WishListResponseDTO.ProductWishListDTO(wishList);
    }

    public void createWishList(int id, CustomUserDetails userDetails) {
        Optional<WishList> wishList = wishListJPARepository.findByUserIdAndProductId(userDetails.getUser().getId(), id);
        if (wishList.isEmpty()) {
            User user = userJPARepository.findById(userDetails.getUser().getId())
                    .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_EXIST));
            Product product = productJPARepository.findById(id)
                    .orElseThrow(() -> new ProductNotExistException(ErrorCode.PRODUCT_NOT_EXIST));

            WishList newWishList = WishList.builder()
                    .user(user)
                    .product(product)
                    .isDeleted(false)
                    .build();
            wishListJPARepository.save(newWishList);
        } else {
            wishList.get().updateIsDeleted();
        }
    }
}
