package com.example.capstone.ws.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AverageAttentionDto {

    private String identifier;
    private Double percentage;
}
