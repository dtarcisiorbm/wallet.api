package com.wallert.api.infra.adapters.persistence;

import com.wallert.api.core.ports.OutboxRepository;
import com.wallert.api.infra.adapters.persistence.entities.OutboxEntity;
import com.wallert.api.infra.adapters.persistence.repository.SpringDataOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxRepositoryAdapter implements OutboxRepository {

    private final SpringDataOutboxRepository repository;

    @Override
    public void saveEvent(String type, String payload) {
        OutboxEntity entity = new OutboxEntity();
        entity.setType(type);
        entity.setPayload(payload);
        repository.save(entity);
    }
}