package com.wallert.api.infra.config;

import com.wallert.api.core.ports.AccountRepository;
import com.wallert.api.core.ports.OutboxRepository;
import com.wallert.api.core.usecases.TransferMoneyUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public TransferMoneyUseCase transferMoneyUseCase(AccountRepository accRepo, OutboxRepository outRepo) {
        return new TransferMoneyUseCase(accRepo, outRepo);
    }
}