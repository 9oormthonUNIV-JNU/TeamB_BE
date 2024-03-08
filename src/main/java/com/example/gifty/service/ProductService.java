package com.example.gifty.service;

import com.example.gifty.dto.product.ProductResponseDTO;
import com.example.gifty.dto.wishlist.WishListRequestDTO;
import com.example.gifty.dto.wishlist.WishListResponseDTO;
import com.example.gifty.entity.History;
import com.example.gifty.entity.Product;
import com.example.gifty.entity.User;
import com.example.gifty.entity.WishList;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.ProductNotExistException;
import com.example.gifty.exception.UserNotExistException;
import com.example.gifty.exception.WishListNotExistException;
import com.example.gifty.repository.HistoryJPARepository;
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
    private final HistoryJPARepository historyJPARepository;

    public List<ProductResponseDTO.ProductDTO> findProductList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Product> pageContent = productJPARepository.findAll(pageable);

        List<ProductResponseDTO.ProductDTO> responseDTOs = pageContent.getContent().stream()
                .map(ProductResponseDTO.ProductDTO::new)
                .collect(Collectors.toList());

        return responseDTOs;
    }

    public ProductResponseDTO.ProductDetailDTO findProduct(int id, CustomUserDetails userDetails) {
        Product product = productJPARepository.findById(id)
                .orElseThrow(() -> new ProductNotExistException(ErrorCode.PRODUCT_NOT_EXIST));

        Optional<History> history = historyJPARepository.findByUserAndProduct(userDetails.getUser().getId(), id);
        if (history.isEmpty()) {
            History newHistory = History.builder()
                    .user(userDetails.getUser())
                    .product(product)
                    .build();
            historyJPARepository.save(newHistory);
        } else {
            history.get().updateModifiedDate(product);
        }

        return new ProductResponseDTO.ProductDetailDTO(product);
    }

    public void createProductWishList(int id, CustomUserDetails userDetails) {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_EXIST));
        Product product = productJPARepository.findById(id)
                .orElseThrow(() -> new ProductNotExistException(ErrorCode.PRODUCT_NOT_EXIST));

        WishList newWishList = WishList.builder()
                .user(user)
                .product(product)
                .build();
        wishListJPARepository.save(newWishList);
    }

    public void findProductWishList(int id, CustomUserDetails userDetails) {
        WishList wishList = wishListJPARepository.findByUserAndProduct(userDetails.getUser().getId(), id)
                .orElseThrow(() -> new WishListNotExistException(ErrorCode.WISHLIST_NOT_EXIST));
    }

    public void deleteProductWishList(int id, CustomUserDetails userDetails) {
        wishListJPARepository.deleteByUserAndProduct(userDetails.getUser().getId(), id);
    }
}
