package com.wallert.api.core.usecases;


import com.wallert.api.core.ports.AccountRepository;
import com.wallert.api.core.ports.OutboxRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferMoneyUseCase {

    private final AccountRepository accountRepository;
    private final OutboxRepository outboxRepository;

    @Transactional // Garante Atomicidade (DB + Outbox)
    public void execute(UUID senderId, BigDecimal amount) {
        // 1. Regra de Negócio
        var account = accountRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        account.debit(amount);

        // 2. Persistência do Saldo
        accountRepository.save(account);

        // 3. Registro na Outbox (Ainda na mesma transação)
        // Se o commit falhar aqui, o débito acima sofre Rollback.
        String payload = String.format("{\"accountId\":\"%s\", \"amount\":%s}", senderId, amount);
        outboxRepository.saveEvent("CASHBACK_PENDING", payload);
    }
}