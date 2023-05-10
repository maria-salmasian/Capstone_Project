package com.example.capstone.ws.dto;

import lombok.Builder;

@Builder
public record AverageAttentionDto(String identifier, Double percentage) { }
