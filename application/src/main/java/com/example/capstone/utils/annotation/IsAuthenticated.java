package com.example.capstone.utils.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(
        validatedBy = IsAuthenticatedValidator.class)
public @interface IsAuthenticated {

    String message() default "user is not authenticated";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
