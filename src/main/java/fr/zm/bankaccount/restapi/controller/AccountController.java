package fr.zm.bankaccount.restapi.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.zm.bankaccount.restapi.dto.RequestDTO;
import fr.zm.bankaccount.restapi.dto.TransactionHistoryDTO;
import fr.zm.bankaccount.restapi.dto.TransactionResponseDTO;
import fr.zm.bankaccount.restapi.service.TransactionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final TransactionService transactionService;

    @Autowired
    public AccountController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/{clientId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String clientId, @Valid @RequestBody RequestDTO request) {
        TransactionResponseDTO response = transactionService.deposit(clientId, LocalDateTime.now(),
                request.getAmount());
        return response.isSuccess() ? ResponseEntity.ok("Deposit successful!")
                : ResponseEntity.badRequest().body(response.getMessage());

    }

    @PostMapping("/{clientId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String clientId, @Valid @RequestBody RequestDTO request) {
        TransactionResponseDTO response = transactionService.withdraw(clientId, LocalDateTime.now(),
                request.getAmount());
        return response.isSuccess() ? ResponseEntity.ok("Withdraw successful!")
                : ResponseEntity.badRequest().body(response.getMessage());

    }

    @GetMapping("/{clientId}/statement")
    public ResponseEntity<List<TransactionHistoryDTO>> getStatement(@PathVariable String clientId) {
        List<TransactionHistoryDTO> transactions = transactionService.getStatement(clientId);
        return ResponseEntity.ok(transactions);
    }
}