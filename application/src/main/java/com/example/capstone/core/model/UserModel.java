package com.example.capstone.core.model;

import com.example.capstone.infrastucture.entity.Role;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class UserModel {

    private Long id;
    private String username;
    private String name;
    private String lastName;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Role role;
}
