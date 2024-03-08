package com.example.gifty.repository;

import com.example.gifty.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationJPARepository extends JpaRepository<Notification, Integer> {
    @Query("select n from Notification n where n.fundingSupport.funding.user.id = :userId")
    List<Notification> findAllByUser(int userId);
}
