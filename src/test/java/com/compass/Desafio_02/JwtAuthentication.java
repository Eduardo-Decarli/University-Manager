package com.compass.Desafio_02;

import com.compass.Desafio_02.jwt.JwtToken;
import com.compass.Desafio_02.web.dto.UserLoginDto;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.http.HttpHeaders;


import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password) {
        String token = client
                .post()
                .uri("/api/v1/auth")
                .bodyValue(new UserLoginDto(username, password))
                .exchange()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody().getToken();

        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
