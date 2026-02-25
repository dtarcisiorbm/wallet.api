package com.wallert.api.core.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {
    private UUID id;
    private BigDecimal balance;
    private Long version; // Optimistic Locking

    public Account(UUID id, BigDecimal balance, Long version) {
        this.id = id;
        this.balance = balance;
        this.version = version;
    }

    public void debit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Valor inválido");
        if (this.balance.compareTo(amount) < 0) throw new RuntimeException("Saldo insuficiente");

        this.balance = this.balance.subtract(amount);
    }

    // Getters...
}