package com.wallert.api.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Transaction(
        UUID id,
        UUID senderId,
        UUID receiverId,
        BigDecimal amount,
        LocalDateTime timestamp
) {}