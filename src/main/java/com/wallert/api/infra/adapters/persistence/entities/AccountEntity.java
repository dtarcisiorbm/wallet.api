// src/main/java/com/wallert/api/infra/adapters/persistence/entities/AccountEntity.java
package com.wallert.api.infra.adapters.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AccountEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private BigDecimal balance;

    @Version // Atende ao NFR3: Optimistic Locking do seu PRD
    private Long version;
}