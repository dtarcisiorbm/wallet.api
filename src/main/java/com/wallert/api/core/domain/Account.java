// src/main/java/com/wallert/api/core/domain/Account.java
package com.wallert.api.core.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {
    private UUID id;
    private BigDecimal balance;
    private Long version;

    public Account(UUID id, BigDecimal balance, Long version) {
        this.id = id;
        this.balance = balance;
        this.version = version;
    }

    // Getters fundamentais para o Mapper e lógica de negócio
    public UUID getId() { return id; }
    public BigDecimal getBalance() { return balance; }
    public Long getVersion() { return version; }

    public void debit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Valor inválido");
        if (this.balance.compareTo(amount) < 0) throw new RuntimeException("Saldo insuficiente");
        this.balance = this.balance.subtract(amount);
    }
    // src/main/java/com/wallert/api/core/domain/Account.java

    public void credit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do crédito deve ser positivo");
        }
        this.balance = this.balance.add(amount);
    }
}