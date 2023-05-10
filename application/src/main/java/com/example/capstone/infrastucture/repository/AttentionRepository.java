package com.example.capstone.infrastucture.repository;

import com.example.capstone.infrastucture.entity.Attention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AttentionRepository extends JpaRepository<Attention, Long> {

    @Query("select avg(a.angle) from Attention a where a.user.id = :userId and a.course.id = :courseId and DATE(a.date) = DATE(:date)")
    Double findAverageAttentionByUserAndCourseAndDate(Long userId, Long courseId, LocalDateTime date);

    @Query("select avg(a.angle) from Attention a where a.course.id = :courseId and DATE(a.date) = DATE(:date)")
    Double findAverageAttentionByCourseAndDate(Long courseId, LocalDateTime date);
}