package com.example.capstone.infrasturcture.repository;

import com.example.capstone.infrasturcture.entity.Attention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttentionRepository extends JpaRepository<Attention,Integer> {
}
