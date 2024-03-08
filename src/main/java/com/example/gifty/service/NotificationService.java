package com.example.gifty.service;

import com.example.gifty.dto.notification.NotificationResponseDTO;
import com.example.gifty.entity.Notification;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.NotificationNotExistException;
import com.example.gifty.repository.NotificationJPARepository;
import com.example.gifty.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class NotificationService {
    private final NotificationJPARepository notificationJPARepository;

    public List<NotificationResponseDTO.NotificationDTO> getNotificationList(CustomUserDetails userDetails) {
        List<Notification> notifications = notificationJPARepository.findAllByUser(userDetails.getUser().getId());
        List<NotificationResponseDTO.NotificationDTO> responseDTOs = notifications.stream()
                .map(NotificationResponseDTO.NotificationDTO::new)
                .toList();
        return responseDTOs;
    }

    public NotificationResponseDTO.NotificationDTO getNotification(int id) {
        Notification notification = notificationJPARepository.findById(id)
                .orElseThrow(() -> new NotificationNotExistException(ErrorCode.NOTIFICATION_NOT_EXIST));
        return new NotificationResponseDTO.NotificationDTO(notification);
    }
}
