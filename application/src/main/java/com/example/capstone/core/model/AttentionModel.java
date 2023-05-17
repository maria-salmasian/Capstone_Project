package com.example.capstone.core.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class AttentionModel {

    private Long id;
    private LocalDateTime date;
    private UserModel user;
    private float percent;
}

