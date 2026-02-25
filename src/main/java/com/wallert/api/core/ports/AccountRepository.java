package com.wallert.api.core.ports;

import com.wallert.api.core.domain.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Optional<Account> findById(UUID id);
    void save(Account account);
}