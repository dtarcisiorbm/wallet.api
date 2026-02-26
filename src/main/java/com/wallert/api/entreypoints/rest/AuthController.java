// src/main/java/com/wallert/api/entreypoints/rest/AuthController.java
package com.wallert.api.entreypoints.rest;

import com.wallert.api.entreypoints.rest.dto.AuthenticationRequest;
import com.wallert.api.infra.config.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody AuthenticationRequest request) {
        // Em um cenário real, aqui você validaria a senha no banco (BCrypt)
        // Por agora, vamos gerar o token se o login for fornecido
        var token = tokenService.generateToken(request.login());

        return ResponseEntity.ok(Map.of("token", token));
    }
}