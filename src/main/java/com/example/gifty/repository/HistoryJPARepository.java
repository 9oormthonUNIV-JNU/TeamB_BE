package com.example.gifty.repository;

import com.example.gifty.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HistoryJPARepository extends JpaRepository<History, Integer> {
    @Query("select h from History h where h.user.id = :userId and h.product.id = :productId")
    Optional<History> findByUserAndProduct(@Param("userId") int userId, @Param("productId") int productId);

    @Query("select h from History h where h.user.id = :userId order by h.modifiedDate desc")
    Page<History> findAllByUserOrderByModifiedDate(int userId, Pageable pageable);
}
