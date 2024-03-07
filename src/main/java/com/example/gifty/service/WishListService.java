package com.example.gifty.service;

import com.example.gifty.dto.wishlist.WishListRequestDTO;
import com.example.gifty.dto.wishlist.WishListResponseDTO;
import com.example.gifty.entity.Product;
import com.example.gifty.entity.User;
import com.example.gifty.entity.WishList;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.ProductNotExistException;
import com.example.gifty.exception.UserNotExistException;
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
public class WishListService {
    private final WishListJPARepository wishListJPARepository;

    public List<WishListResponseDTO.WishListDTO> findWishList(int page, CustomUserDetails userDetails) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<WishList> pageContent = wishListJPARepository.findAllByUser(userDetails.getUser().getId(), pageable);

        List<WishListResponseDTO.WishListDTO> responseDTOs = pageContent.getContent().stream()
                .map(WishListResponseDTO.WishListDTO::new)
                .toList();
        return responseDTOs;
    }
}
