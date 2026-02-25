// src/main/java/com/wallert/api/infra/adapters/persistence/mappers/AccountMapper.java
package com.wallert.api.infra.adapters.persistence.mappers;

import com.wallert.api.core.domain.Account;
import com.wallert.api.infra.adapters.persistence.entities.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toDomain(AccountEntity entity) {
        if (entity == null) return null;
        return new Account(
                entity.getId(),
                entity.getBalance(),
                entity.getVersion()
        );
    }

    public AccountEntity toEntity(Account domain) {
        if (domain == null) return null;
        return new AccountEntity(
                domain.getId(),
                domain.getBalance(),
                domain.getVersion()
        );
    }
}