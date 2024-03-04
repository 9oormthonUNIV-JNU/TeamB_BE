package com.example.gifty.repository;

import com.example.gifty.entity.Funding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingJPARepository extends JpaRepository<Funding, Integer> {
}
