// src/main/java/com/wallert/api/entreypoints/rest/dto/AuthenticationRequest.java
package com.wallert.api.entreypoints.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank(message = "O login é obrigatório")
        String login,

        @NotBlank(message = "A senha é obrigatória")
        String password
) {}