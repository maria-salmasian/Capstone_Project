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
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttentionServiceImpl implements AttentionService {

    private final AttentionRepository attentionRepository;

    @Override
    @Transactional
    public AverageAttentionDto getAverageByUserAndCourseAndDate(Long userId, Long courseId, LocalDateTime date) {
        final Double avgAttention = attentionRepository.findAverageAttentionByUserAndCourseAndDate(userId, courseId, date);
        final String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (avgAttention == null)
            throw new ReportNotFound(String.format("no available report for given date"));

        return AverageAttentionDto.builder()
                .identifier(
                        String.format("Average attention of user for the course on date: %s",
                                formattedDate))
                .percentage(avgAttention.longValue())
                .build();
    }


    @Override
    @Transactional
    public List<Double> getAverageByUserAndCourseAndDateINTERVAL(Long userId, Long courseId, LocalDateTime startDate, LocalDateTime endDate) {
        final List<Double> avgAttention = attentionRepository.findAverageAttentionByUserAndCourseAndDateIn(userId, courseId, startDate, endDate);
        final String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        final String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        if (avgAttention == null)
            throw new ReportNotFound(String.format("no available report for given date"));

        return avgAttention;
    }

    @Override
    @Transactional
    public AverageAttentionDto getAverageByCourseAndDate(Long courseId, LocalDateTime date) {
        final Double avgAttention = attentionRepository.findAverageAttentionByCourseAndDate(courseId, date);
        final String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (avgAttention == null)
            throw new ReportNotFound(String.format("no available report for given date"));
        return AverageAttentionDto.builder()
                .identifier(String.format("Average attention for the course on date: %s", formattedDate))
                .percentage(avgAttention.longValue())
                .build();
    }
}
