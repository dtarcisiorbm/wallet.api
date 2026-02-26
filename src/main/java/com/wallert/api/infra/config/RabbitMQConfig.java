// src/main/java/com/wallert/api/infra/config/RabbitMQConfig.java
package com.wallert.api.infra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "wallet.events";
    public static final String QUEUE_NAME = "wallet.transfer.completed";
    public static final String ROUTING_KEY = "TRANSFER_COMPLETED";

    // Declara a Exchange do tipo Topic (flexível para múltiplos consumidores)
    @Bean
    public TopicExchange walletExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Declara uma fila para processar as transferências
    @Bean
    public Queue transferQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    // Faz o "Binding" (ligação) da fila com a exchange através da Routing Key
    @Bean
    public Binding binding(Queue transferQueue, TopicExchange walletExchange) {
        return BindingBuilder.bind(transferQueue).to(walletExchange).with(ROUTING_KEY);
    }
}