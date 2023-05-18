package com.example.capstone.core.service;

import com.example.capstone.ws.dto.AverageAttentionDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AttentionService {

    AverageAttentionDto getAverageByUserAndCourseAndDate(Long userId, Long courseId, LocalDateTime date);

    AverageAttentionDto getAverageByCourseAndDate(Long courseId, LocalDateTime date);
    List<Double> getAverageByUserAndCourseAndDateINTERVAL(Long userId, Long courseId, LocalDateTime startDate, LocalDateTime endDate);
}
