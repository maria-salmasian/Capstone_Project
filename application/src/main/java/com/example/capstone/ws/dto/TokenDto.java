package com.example.capstone.ws.dto;

import com.example.capstone.utils.enumeration.RoleName;
import lombok.Builder;

@Builder
public record TokenDto (RoleName role, String token){ }
