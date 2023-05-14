package com.example.capstone.core.service.impl;

import com.example.capstone.core.service.AttentionService;
import com.example.capstone.core.service.exception.NotFoundException;
import com.example.capstone.core.service.exception.ReportNotFound;
import com.example.capstone.infrastucture.repository.AttentionRepository;
import com.example.capstone.ws.dto.AverageAttentionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AttentionServiceImpl implements AttentionService {

    private final AttentionRepository attentionRepository;

    @Override
    @Transactional
    public AverageAttentionDto getAverageByUserAndCourseAndDate(Long userId, Long courseId, LocalDateTime date) {
        final Double avgAttention = attentionRepository.findAverageAttentionByUserAndCourseAndDate(userId, courseId, date);
        final String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));

        if (avgAttention == null)
            throw new ReportNotFound(String.format("no available report for given date"));

        return AverageAttentionDto.builder()
                .identifier(
                        String.format("Average attention of user for the course on date: %s",
                                userId, courseId, formattedDate))
                .percentage(avgAttention)
                .build();
    }

    @Override
    @Transactional
    public AverageAttentionDto getAverageByCourseAndDate(Long courseId, LocalDateTime date) {
        final Double avgAttention = attentionRepository.findAverageAttentionByCourseAndDate(courseId, date);
        final String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));

        if (avgAttention == null)
            throw new ReportNotFound(String.format("no available report for given date"));
        return AverageAttentionDto.builder()
                .identifier(String.format("Average attention for the course on date: %s", courseId, formattedDate))
                .percentage(avgAttention)
                .build();
    }
}
