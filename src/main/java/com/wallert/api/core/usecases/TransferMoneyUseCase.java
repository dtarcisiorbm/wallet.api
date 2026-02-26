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

    @Transactional // Essencial: se um falhar, nada é gravado
    public void execute(UUID senderId, UUID receiverId, BigDecimal amount) {
        // 1. Recuperar ambas as contas
        var sender = accountRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada"));

        var receiver = accountRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada"));

        // 2. Aplicar as regras de negócio no domínio
        sender.debit(amount);   // Já implementado
        receiver.credit(amount); // Novo método

        // 3. Persistir as alterações
        accountRepository.save(sender);
        accountRepository.save(receiver);

        // 4. Registro na Outbox para eventos posteriores (ex: Cashback ou Notificação)
        String payload = String.format("{\"senderId\":\"%s\", \"receiverId\":\"%s\", \"amount\":%s}",
                senderId, receiverId, amount);
        outboxRepository.saveEvent("TRANSFER_COMPLETED", payload);
    }
}