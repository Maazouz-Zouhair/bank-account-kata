package fr.zm.bankaccount.restapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.zm.bankaccount.account.Account;
import fr.zm.bankaccount.account.Client;
import fr.zm.bankaccount.account.Transaction;
import fr.zm.bankaccount.enums.ErrorMessages;
import fr.zm.bankaccount.enums.TransactionType;
import fr.zm.bankaccount.exceptions.AccountNotFoundException;
import fr.zm.bankaccount.exceptions.InvalidTransactionException;
import fr.zm.bankaccount.exceptions.UnknownClientException;
import fr.zm.bankaccount.restapi.dto.TransactionResponseDTO;
import fr.zm.bankaccount.restapi.repository.AccountRepository;
import fr.zm.bankaccount.restapi.repository.ClientRepository;
import fr.zm.bankaccount.restapi.repository.TransactionRepository;

@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository,
            ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
    }

    public TransactionResponseDTO deposit(String clientId, LocalDate date, BigDecimal amount) {
        return processTransaction(clientId, date, amount, TransactionType.DEPOSIT);
    }

    public TransactionResponseDTO withdraw(String clientId, LocalDate date, BigDecimal amount) {
        return processTransaction(clientId, date, amount, TransactionType.WITHDRAWAL);
    }

    private TransactionResponseDTO processTransaction(String clientId, LocalDate date, BigDecimal amount,
            TransactionType type) {
        Client client = validateClient(clientId);
        Account account = validateAccount(clientId);
        return handleTransaction(account, client, date, amount, type);
    }

    private Account validateAccount(String clientId) {
        return accountRepository.findByClientId(clientId)
                .orElseThrow(() -> new AccountNotFoundException(
                        ErrorMessages.ACCOUNT_NOT_FOUND.getMessage() + clientId));
    }

    private Client validateClient(String clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new UnknownClientException(clientId+
                        ErrorMessages.UNKNOWN_CLIENT.getMessage()));
    }

    private TransactionResponseDTO handleTransaction(Account account, Client client, LocalDate date, BigDecimal amount,
            TransactionType type) {
        try {

            // Calculate the new balance based on the transaction type
            BigDecimal newBalance = account.getBalance();
            if (type == TransactionType.DEPOSIT)
                newBalance = newBalance.add(amount);

            // Create the transaction with the new balance
            Transaction transaction = new Transaction(client, date, amount, type, newBalance);
            account.addTransaction(client, date, amount, type, newBalance); // This method can be updated to accept the
                                                                            // balance if
            // needed
            transactionRepository.save(transaction);
            logger.info("{} successful for clientId: {}. Amount: {}", type, client.getClientId(), amount);
            return new TransactionResponseDTO(true, "Transaction successful");

        } catch (InvalidTransactionException e) {
            logger.error("Transaction failed for clientId: {}. Reason: {}", client.getClientId(), e.getMessage());
            return new TransactionResponseDTO(false, e.getMessage());
        }
    }

}
