package com.wallert.api.infra.adapters.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OutboxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String type; // Ex: "CASHBACK_PENDING"

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload; // JSON com os dados da transação

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private boolean processed = false;
}