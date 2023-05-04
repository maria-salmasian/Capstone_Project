package com.example.capstone.core.service;

import org.springframework.stereotype.Service;

@Service
public interface SecurityService {
    String exchangeAuthorizationCode(String code);

}
