package com.wallert.api.infra.adapters.persistence;

import com.wallert.api.core.domain.Account;
import com.wallert.api.core.ports.AccountRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaAccountRepositoryAdapter implements AccountRepository {

    private final SpringDataAccountRepository jpaRepo; // Interface JpaRepository
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE) // NFR3: Proteção contra Race Conditions
    public Optional<Account> findById(UUID id) {
        // Tenta ler do Cache primeiro (US02)
        var cached = redisTemplate.opsForValue().get("balance:" + id);
        // ... lógica de cache-aside omitida para brevidade
        return jpaRepo.findById(id).map(this::toDomain);
    }

    @Override
    public void save(Account account) {
        var entity = toEntity(account);
        jpaRepo.save(entity);
        // Invalida ou atualiza o cache no Redis
        redisTemplate.opsForValue().set("balance:" + account.getId(), account.getBalance());
    }
}
