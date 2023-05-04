package com.example.capstone.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "authorization.client")
@Configuration("clientProperties")
@Data
public class ClientConfig {
    private String clientId;
    private String clientSecret;
    private String tokenUrl;
    private  String logoutUrl;
}
