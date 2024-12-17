package fr.zm.bankaccount.restapi.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.zm.bankaccount.restapi.dto.DepositRequest;
import fr.zm.bankaccount.restapi.dto.TransactionResponseDTO;
import fr.zm.bankaccount.restapi.service.TransactionService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final TransactionService transactionService;

    @Autowired
    public AccountController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/{clientId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String clientId, @RequestBody DepositRequest request) {
        TransactionResponseDTO response = transactionService.deposit(clientId, LocalDate.now(), request.getAmount());
        return response.isSuccess() ? ResponseEntity.ok("Deposit successful!")
                : ResponseEntity.badRequest().body(response.getMessage());

    }

}
