package com.example.capstone.core.service.impl;

import com.example.capstone.infrastucture.repository.AttentionRepository;
import com.example.capstone.ws.dto.AverageAttentionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttentionServiceImplTest {

    @Mock
    private AttentionRepository mockAttentionRepository;

    private AttentionServiceImpl attentionServiceImplUnderTest;


    @BeforeEach
    void setUp() {
        attentionServiceImplUnderTest = new AttentionServiceImpl(mockAttentionRepository);
    }

    @Test
    void testGetAverageByUserAndCourseAndDate() {
        final AverageAttentionDto expectedResult = AverageAttentionDto.builder()
                .identifier("Average attention of user with id: 0 for the course with id: 0, date: 2020-01-01 00")
                .percentage(0.0)
                .build();
        when(mockAttentionRepository.findAverageAttentionByUserAndCourseAndDate(0L, 0L,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0))).thenReturn(0.0);

        final AverageAttentionDto result = attentionServiceImplUnderTest.getAverageByUserAndCourseAndDate(0L, 0L,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAverageByCourseAndDate() {
        final AverageAttentionDto expectedResult = AverageAttentionDto.builder()
                .identifier("Average attention for the course with id: 0, date: 2020-01-01 00")
                .percentage(0.0)
                .build();
        when(mockAttentionRepository.findAverageAttentionByCourseAndDate(0L,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0))).thenReturn(0.0);

        final AverageAttentionDto result = attentionServiceImplUnderTest.getAverageByCourseAndDate(0L,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        assertThat(result).isEqualTo(expectedResult);
    }
}
