package com.example.gifty.service;

import com.example.gifty.dto.wishlist.WishListRequestDTO;
import com.example.gifty.entity.Product;
import com.example.gifty.entity.User;
import com.example.gifty.entity.WishList;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.ProductNotExistException;
import com.example.gifty.exception.UserNotExistException;
import com.example.gifty.repository.ProductJPARepository;
import com.example.gifty.repository.UserJPARepository;
import com.example.gifty.repository.WishListJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class WishListService {
    private final UserJPARepository userJPARepository;
    private final ProductJPARepository productJPARepository;
    private final WishListJPARepository wishListJPARepository;
}
