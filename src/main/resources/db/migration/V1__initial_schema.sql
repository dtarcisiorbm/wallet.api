-- Criação da tabela de contas
CREATE TABLE accounts (
                          id UUID PRIMARY KEY,
                          balance DECIMAL(19, 2) NOT NULL,
                          version BIGINT NOT NULL
);

-- Criação da tabela de usuários para autenticação
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       login VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

-- Criação da tabela de histórico de transações
CREATE TABLE transactions (
                              id UUID PRIMARY KEY,
                              sender_id UUID NOT NULL,
                              receiver_id UUID NOT NULL,
                              amount DECIMAL(19, 2) NOT NULL,
                              timestamp TIMESTAMP NOT NULL,
                              CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES accounts(id),
                              CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES accounts(id)
);

-- Criação da tabela Outbox para eventos assíncronos
CREATE TABLE outbox (
                        id UUID PRIMARY KEY,
                        type VARCHAR(100) NOT NULL,
                        payload TEXT NOT NULL,
                        created_at TIMESTAMP NOT NULL,
                        processed BOOLEAN NOT NULL DEFAULT FALSE
);