package com.example.capstone.core.model;

import com.example.capstone.infrasturcture.entity.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class User {

    private Long id;
    private String username;
    private String name;
    private String lastName;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Role role;
}
