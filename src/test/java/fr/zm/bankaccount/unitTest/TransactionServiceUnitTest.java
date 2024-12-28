package fr.zm.bankaccount.unitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.zm.bankaccount.account.Account;
import fr.zm.bankaccount.account.Bank;
import fr.zm.bankaccount.account.Client;
import fr.zm.bankaccount.enums.ErrorMessages;
import fr.zm.bankaccount.enums.TransactionType;
import fr.zm.bankaccount.exceptions.InvalidTransactionException;
import fr.zm.bankaccount.exceptions.UnknownClientException;
import fr.zm.bankaccount.restapi.dto.TransactionHistoryDTO;
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
        account.addTransaction(client, LocalDateTime.now(), BigDecimal.valueOf(100),
                TransactionType.DEPOSIT,
                BigDecimal.valueOf(100));

    }

    @Test
    public void testDepositSuccess() throws InvalidTransactionException {
        when(clientRepository.findById("C001")).thenReturn(Optional.of(client));
        when(accountRepository.findByClientId("C001")).thenReturn(Optional.of(account));

        TransactionResponseDTO response = transactionService.deposit("C001", LocalDateTime.now(),
                new BigDecimal("100.00"));

        BigDecimal expectedBalance = new BigDecimal("200.00");
        assertTrue(response.isSuccess());
        assertEquals("Transaction successful", response.getMessage());
        assertEquals(expectedBalance, account.getBalance());
    }

    @Test
    public void testDepositClientNotFound() {
        String invalidClientId = "xxx";
        when(clientRepository.findById(invalidClientId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UnknownClientException.class, () -> {
            transactionService.deposit(invalidClientId, LocalDateTime.now(), new BigDecimal("100.00"));
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(invalidClientId + ErrorMessages.UNKNOWN_CLIENT.getMessage()));
    }

    @Test
    public void testWithdrawalSuccess() throws InvalidTransactionException {
        when(clientRepository.findById("C001")).thenReturn(Optional.of(client));
        when(accountRepository.findByClientId("C001")).thenReturn(Optional.of(account));

        TransactionResponseDTO response = transactionService.withdraw("C001", LocalDateTime.now(),
                new BigDecimal("10.00"));

        BigDecimal expectedBalance = new BigDecimal("90.00");
        assertTrue(response.isSuccess());
        assertEquals("Transaction successful", response.getMessage());
        assertEquals(expectedBalance, account.getBalance());

    }

    @Test
    public void testWithdrawalInsufficientFunds() throws InvalidTransactionException {
        BigDecimal amount = new BigDecimal("1000.00");

        when(clientRepository.findById("C001")).thenReturn(Optional.of(client));
        when(accountRepository.findByClientId("C001")).thenReturn(Optional.of(account));

        TransactionResponseDTO response = transactionService.withdraw("C001", LocalDateTime.now(),
                amount);

        String expectedResponseMsg = ErrorMessages.INSUFFICIENT_FUNDS.getMessage() + " : " + account.getBalance()
                + " < "
                + amount.abs();
        assertTrue(!response.isSuccess());
        assertEquals(expectedResponseMsg, response.getMessage());
    }

    @Test
    public void testWithdrawalClientNotFound() {
        String invalidClientId = "xxx";
        when(clientRepository.findById(invalidClientId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UnknownClientException.class, () -> {
            transactionService.deposit(invalidClientId, LocalDateTime.now(), new BigDecimal("100.00"));
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(invalidClientId + ErrorMessages.UNKNOWN_CLIENT.getMessage()));
    }

    @Test
    public void testGetStatement_Success() {
        when(clientRepository.findById(client.getClientId())).thenReturn(Optional.of(client));
        when(accountRepository.findByClientId(client.getClientId())).thenReturn(Optional.of(account));

        when(transactionRepository.findByClientId(client.getClientId())).thenReturn(account.getTransactions());

        List<TransactionHistoryDTO> statement = transactionService.getStatement(client.getClientId());

        assertNotNull(statement);
        assertEquals(1, statement.size());
        assertEquals(new BigDecimal(100.00), statement.get(0).getAmount());
        assertEquals(TransactionType.DEPOSIT.get(), statement.get(0).getOperationType());
    }

    @Test
    public void testGetStatement_ClientNotFound() {
        when(clientRepository.findById(client.getClientId())).thenReturn(Optional.empty());

        assertThrows(UnknownClientException.class, () -> transactionService.getStatement(client.getClientId()));
    }
}
