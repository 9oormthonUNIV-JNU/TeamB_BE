package com.example.gifty.repository;

import com.example.gifty.entity.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendJPARepository extends JpaRepository<Friend, Integer> {
    @Query("select f from Friend f where f.user.id = :userId")
    List<Friend> findAllByUser(int userId);
}
