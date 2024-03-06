package com.example.gifty.repository;

import com.example.gifty.entity.Funding;
import com.example.gifty.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FundingJPARepository extends JpaRepository<Funding, Integer> {
    @Query("select f from Funding f where f.user.id = :userId and f.state = :state")
    Optional<Funding> findByUserAndState(int userId, State state);
}
