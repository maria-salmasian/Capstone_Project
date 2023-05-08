package com.example.capstone.core.service;


import com.example.capstone.ws.dto.TokenDto;

public interface SecurityService {
    TokenDto exchangeAuthorizationCode(String code);

}
