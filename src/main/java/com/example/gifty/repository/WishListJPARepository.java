package com.example.gifty.repository;

import com.example.gifty.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WishListJPARepository extends JpaRepository<WishList, Integer> {
    @Query("select w from WishList w where w.user.id = :userId and w.product.id = :productId")
    Optional<WishList> findByUserIdAndProductId(@Param("userId") int userId, @Param("productId") int productId);
}
