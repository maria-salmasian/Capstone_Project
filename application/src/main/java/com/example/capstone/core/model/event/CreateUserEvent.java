package com.example.capstone.core.model.event;

import io.jsonwebtoken.Claims;
import lombok.*;

@EqualsAndHashCode
@Data
@AllArgsConstructor
public class CreateUserEvent {
    Claims claims;
}
