package com.example.capstone.core.service;


public interface SecurityService {
    String exchangeAuthorizationCode(String code);

    void logout(String  token);

}
