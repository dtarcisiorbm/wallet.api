package com.wallert.api.core.ports;

public interface OutboxRepository {
    void saveEvent(String type, String payload);
}
