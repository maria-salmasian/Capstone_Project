package com.example.capstone.core.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ReportNotFound extends RuntimeException {
    public ReportNotFound(String message) {
        super(message);
    }
}