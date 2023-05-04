package com.example.capstone.config;

import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OkHttpConfig {
    @Bean
//    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}