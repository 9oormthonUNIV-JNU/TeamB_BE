package com.example.gifty.repository;

import com.example.gifty.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJPARepository extends JpaRepository<Notification, Integer> {
}
