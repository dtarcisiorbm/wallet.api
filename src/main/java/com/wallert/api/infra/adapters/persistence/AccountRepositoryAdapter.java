package com.wallert.api.infra.adapters.persistence;

import com.wallert.api.core.domain.Account;
import com.wallert.api.core.ports.AccountRepository;
import com.wallert.api.infra.adapters.persistence.mappers.AccountMapper;
import com.wallert.api.infra.adapters.persistence.repository.SpringDataAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepository {

    private final SpringDataAccountRepository jpaRepo;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AccountMapper mapper;

    private String getCacheKey(UUID id) {
        return "balance:" + id.toString();
    }

    @Override
    public Optional<Account> findById(UUID id) {
        String key = getCacheKey(id);

        // 1. Tenta buscar no Redis (Consulta rápida - US02)
        Object cachedBalance = redisTemplate.opsForValue().get(key);

        if (cachedBalance != null) {
            // Se encontrar, busca apenas os dados básicos para montar o domínio
            // Em um cenário real, você pode armazenar o objeto Account inteiro no Redis
            return jpaRepo.findById(id).map(mapper::toDomain);
        }

        // 2. Cache Miss: Busca no PostgreSQL e atualiza o Redis
        return jpaRepo.findById(id).map(entity -> {
            Account account = mapper.toDomain(entity);
            redisTemplate.opsForValue().set(key, account.getBalance(), Duration.ofMinutes(10));
            return account;
        });
    }

    @Override
    public void save(Account account) {
        // Persiste no Banco (Atomicidade)
        jpaRepo.save(mapper.toEntity(account));

        // Atualiza o Cache (Consistência)
        redisTemplate.opsForValue().set(getCacheKey(account.getId()), account.getBalance());
    }
}