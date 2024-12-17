package fr.zm.bankaccount.unitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.zm.bankaccount.account.Account;
import fr.zm.bankaccount.account.Bank;
import fr.zm.bankaccount.account.Client;
import fr.zm.bankaccount.enums.ErrorMessages;
import fr.zm.bankaccount.exceptions.InvalidTransactionException;
import fr.zm.bankaccount.exceptions.UnknownClientException;
import fr.zm.bankaccount.restapi.dto.TransactionResponseDTO;
import fr.zm.bankaccount.restapi.repository.AccountRepository;
import fr.zm.bankaccount.restapi.repository.ClientRepository;
import fr.zm.bankaccount.restapi.repository.TransactionRepository;
import fr.zm.bankaccount.restapi.service.TransactionService;

public class TransactionServiceUnitTest {

    private TransactionService transactionService;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private ClientRepository clientRepository;
    private Client client;
    private Account account;

    @BeforeEach
    public void setUp() {
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        clientRepository = mock(ClientRepository.class);
        transactionService = new TransactionService(accountRepository, transactionRepository, clientRepository);

        client = new Client("C001", "John", "Doe");
        account = new Account(new Bank("My Bank"), client);
    }

    @Test
    public void testDepositSuccess() throws InvalidTransactionException {
        when(clientRepository.findById("C001")).thenReturn(Optional.of(client));
        when(accountRepository.findByClientId("C001")).thenReturn(Optional.of(account));

        TransactionResponseDTO response = transactionService.deposit("C001", LocalDate.now(), new BigDecimal("100.00"));

        assertTrue(response.isSuccess());
        assertEquals("Transaction successful", response.getMessage());
    }

    @Test
    public void testDepositClientNotFound() {
        String invalidClientId = "xxx";
        when(clientRepository.findById(invalidClientId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UnknownClientException.class, () -> {
            transactionService.deposit(invalidClientId, LocalDate.now(), new BigDecimal("100.00"));
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(invalidClientId+ErrorMessages.UNKNOWN_CLIENT.getMessage()));
    }
}
