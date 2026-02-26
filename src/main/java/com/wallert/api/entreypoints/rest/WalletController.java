package com.wallert.api.entreypoints.rest;

import com.wallert.api.core.usecases.TransferMoneyUseCase;
import com.wallert.api.entreypoints.rest.dto.TransferRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/wallets")
public class WalletController {

    private final TransferMoneyUseCase transferUseCase;

    public WalletController(TransferMoneyUseCase transferUseCase) {
        this.transferUseCase = transferUseCase;
    }

    // src/main/java/com/wallert/api/entreypoints/rest/WalletController.java

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequest request) {
        // Agora passamos os dois IDs e o valor
        transferUseCase.execute(request.senderId(), request.receiverId(), request.amount());
        return ResponseEntity.accepted().build();
    }
}
