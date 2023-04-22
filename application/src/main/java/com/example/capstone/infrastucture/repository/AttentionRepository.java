package com.example.capstone.infrastucture.repository;

import com.example.capstone.infrastucture.entity.Attention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttentionRepository extends JpaRepository<Attention,Long> {
}
