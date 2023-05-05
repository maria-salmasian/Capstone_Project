package com.example.capstone.utils.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;
import java.util.Date;

import static com.example.capstone.core.service.impl.SecurityServiceImpl.getClaims;

public class IsAuthenticatedValidator implements ConstraintValidator<
        IsAuthenticated, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return getClaims(value).getExpiration().after(Date.from(Instant.now()));
    }
}
