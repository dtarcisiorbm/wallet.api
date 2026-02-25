package com.wallert.api.infra.adapters.persistence.repository;

import com.wallert.api.infra.adapters.persistence.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface SpringDataAccountRepository extends JpaRepository<AccountEntity, UUID> {
}