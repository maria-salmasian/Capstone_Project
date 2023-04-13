package com.example.capstone.core.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class Course {
    private Integer id;
    private String title;
    private String description;
    private Integer cluster;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;
    private User user;
}
