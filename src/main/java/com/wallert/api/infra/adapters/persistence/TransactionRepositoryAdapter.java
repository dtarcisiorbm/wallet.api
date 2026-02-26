package com.wallert.api.infra.adapters.persistence;

import com.wallert.api.core.domain.Transaction;
import com.wallert.api.core.ports.TransactionRepository;
import com.wallert.api.infra.adapters.persistence.entities.TransactionEntity;
import com.wallert.api.infra.adapters.persistence.repository.SpringDataTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final SpringDataTransactionRepository repository;

    @Override
    public void save(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity(
                transaction.id(),
                transaction.senderId(),
                transaction.receiverId(),
                transaction.amount(),
                transaction.timestamp()
        );
        repository.save(entity);
    }
}