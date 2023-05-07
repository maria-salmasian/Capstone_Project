package com.example.capstone.core.service.impl;

import com.example.capstone.config.ClientConfig;
import com.example.capstone.core.model.event.CreateUserEvent;
import com.example.capstone.core.service.SecurityService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    private final ClientConfig clientConfig;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public String exchangeAuthorizationCode(String code) {

        var requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("client_id", clientConfig.getClientId())
                .addFormDataPart("client_secret", clientConfig.getClientSecret())
                .addFormDataPart("code", code)
                .addFormDataPart("grant_type", "authorization_code")
                .addFormDataPart("redirect_uri", "http://localhost:8080/user/login")
                .build();

        var request = new Request.Builder()
                .url(clientConfig.getTokenUrl())
                .post(requestBody)
                .build();

        String token = this.makeHttpCall(request).get("id_token").toString();

        applicationEventPublisher.publishEvent(new CreateUserEvent((getClaims(token))));

        return token;

    }




    public static Claims getClaims(final String token) {
        final int signatureStartIndex = token.lastIndexOf('.') + 1;
        final String withoutSignature = token.substring(0, signatureStartIndex);
        return Jwts.parser().parseClaimsJwt(withoutSignature).getBody();
    }


    protected Map<String, Object> makeHttpCall(final Request request) {
        try {
            final var response = this.httpClient.newCall(request).execute();
            return response.isSuccessful()
                    ? handleSuccessfulResponse(response)
                    : handleErrorResponse(response);
        } catch (final IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private Map<String, Object> handleSuccessfulResponse(@NotNull final Response response) {
        try {
            final var responseBody = Objects.requireNonNull(response.peekBody(Long.MAX_VALUE)).string();

            final var typeRef = new TypeReference<Map<String, Object>>() {
            };

            return this.objectMapper.readValue(responseBody, typeRef);
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private HashMap<String, Object> handleErrorResponse(@NotNull final Response response) {
        final var message = "Exchange failed. Cause: %s".formatted(response.message());
        throw new RuntimeException(message);
    }

}
