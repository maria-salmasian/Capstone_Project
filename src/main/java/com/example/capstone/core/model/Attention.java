package com.example.capstone.core.model;

import com.example.capstone.infrasturcture.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class Attention {
    private Long id;
    private LocalDateTime date;
    private User user;
    private float angle;
}

