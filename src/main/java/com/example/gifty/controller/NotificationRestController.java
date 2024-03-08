package com.example.gifty.controller;

import com.example.gifty.ApiResponse;
import com.example.gifty.dto.notification.NotificationResponseDTO;
import com.example.gifty.security.CustomUserDetails;
import com.example.gifty.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class NotificationRestController {
    private final NotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<?> getNotificationList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<NotificationResponseDTO.NotificationDTO> responseDTOs = notificationService.getNotificationList(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTOs));
    }

    @GetMapping("/notifications/{id}")
    public ResponseEntity<?> getNotification(@PathVariable int id) {
        NotificationResponseDTO.NotificationDTO responseDTO = notificationService.getNotification(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTO));
    }
}
