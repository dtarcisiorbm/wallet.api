package com.wallert.api.core.domain;

public record OutboxEvent(
        String type,
        String payload
) {}