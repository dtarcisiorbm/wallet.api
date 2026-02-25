// src/main/java/com/wallert/api/infra/adapters/messaging/OutboxRelayWorker.java
package com.wallert.api.infra.adapters.messaging;

import com.wallert.api.infra.adapters.persistence.repository.SpringDataOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxRelayWorker {

    private final SpringDataOutboxRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;

    // Executa a cada 5 segundos para processar a fila de saída
    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void relayEvents() {
        var pendingEvents = outboxRepository.findByProcessedFalse();

        if (pendingEvents.isEmpty()) return;

        log.info("Processando {} eventos pendentes na Outbox", pendingEvents.size());

        for (var event : pendingEvents) {
            try {
                // Envia para a exchange do RabbitMQ definida no compose.yaml
                rabbitTemplate.convertAndSend("wallet.events", event.getType(), event.getPayload());

                // Marca como processado para não repetir o envio
                event.setProcessed(true);
                outboxRepository.save(event);
            } catch (Exception e) {
                log.error("Erro ao enviar evento {}: {}", event.getId(), e.getMessage());
                // O evento permanece como processed=false para a próxima tentativa
            }
        }
    }
}