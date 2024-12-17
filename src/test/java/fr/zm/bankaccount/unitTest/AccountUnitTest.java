package fr.zm.bankaccount.unitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.zm.bankaccount.account.Account;
import fr.zm.bankaccount.account.Bank;
import fr.zm.bankaccount.account.Client;
import fr.zm.bankaccount.enums.TransactionType;
import fr.zm.bankaccount.exceptions.InvalidTransactionException;

public class AccountUnitTest {
    
     private Account account;
    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client("C001", "John", "Doe");
        account = new Account(new Bank("My Bank"), client);
    }

    @Test
    public void testAddTransaction() throws InvalidTransactionException {
        BigDecimal amount = new BigDecimal("100.00");
        LocalDate date = LocalDate.now();
        account.addTransaction(client, date, amount, TransactionType.DEPOSIT, account.getBalance().add(amount));
        
        assertEquals(amount, account.getBalance());
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    public void testInvalidTransaction() {
        BigDecimal amount = new BigDecimal("-50.00");
        LocalDate date = LocalDate.now();
        
        Exception exception = assertThrows(InvalidTransactionException.class, () -> {
            account.addTransaction(client, date, amount, TransactionType.DEPOSIT, account.getBalance());
        });
        
        assertEquals("Amount must be positive.", exception.getMessage());
    }
}
