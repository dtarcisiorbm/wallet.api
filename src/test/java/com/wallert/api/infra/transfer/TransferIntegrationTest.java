package com.wallert.api.infra.transfer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallert.api.entreypoints.rest.dto.TransferRequest;
import com.wallert.api.infra.adapters.persistence.entities.AccountEntity;
import com.wallert.api.infra.adapters.persistence.repository.SpringDataAccountRepository;
import com.wallert.api.infra.config.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
class TransferIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpringDataAccountRepository accountRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve realizar uma transferência com sucesso entre duas contas")
    void shouldTransferMoneySuccessfully() throws Exception {
        // GIVEN: Duas contas no banco
        var senderId = UUID.randomUUID();
        var receiverId = UUID.randomUUID();

        accountRepository.save(new AccountEntity(senderId, new BigDecimal("100.00"), 0L));
        accountRepository.save(new AccountEntity(receiverId, new BigDecimal("50.00"), 0L));

        // E um token válido (usando o TokenService que criaste em infra)
        var token = tokenService.generateToken("test-user");

        var request = new TransferRequest(senderId, receiverId, new BigDecimal("20.00"));

        mockMvc.perform(post("/v1/wallets/transfer")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE) // Corrigido aqui
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted());

        // THEN: Os saldos devem estar atualizados no banco
        var senderAfter = accountRepository.findById(senderId).get();
        var receiverAfter = accountRepository.findById(receiverId).get();

        assertThat(senderAfter.getBalance()).isEqualByComparingTo("80.00");
        assertThat(receiverAfter.getBalance()).isEqualByComparingTo("70.00");
    }
}