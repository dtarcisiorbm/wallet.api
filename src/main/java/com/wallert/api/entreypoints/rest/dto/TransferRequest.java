package com.wallert.api.entreypoints.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransferRequest(
        @NotNull(message = "O ID da conta de origem é obrigatório")
        UUID senderId,

        @NotNull(message = "O ID da conta de destino é obrigatório")
        UUID receiverId,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal amount
) {}