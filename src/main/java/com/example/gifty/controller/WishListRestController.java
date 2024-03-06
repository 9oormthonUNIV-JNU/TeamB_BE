package com.example.gifty.controller;

import com.example.gifty.ApiResponse;
import com.example.gifty.dto.wishlist.WishListRequestDTO;
import com.example.gifty.security.CustomUserDetails;
import com.example.gifty.service.WishListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WishListRestController {
    private final WishListService wishListService;

}
