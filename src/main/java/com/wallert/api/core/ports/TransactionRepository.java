package com.wallert.api.core.ports;

import com.wallert.api.core.domain.Transaction;

public interface TransactionRepository {
    void save(Transaction transaction);
}