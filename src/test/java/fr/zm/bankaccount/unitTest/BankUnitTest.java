package fr.zm.bankaccount.unitTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.zm.bankaccount.account.Account;
import fr.zm.bankaccount.account.Bank;
import fr.zm.bankaccount.account.Client;

public class BankUnitTest {

    private Bank bank;
    private Client client;

    @BeforeEach
    public void setUp() {
        bank = new Bank("My Bank");
        client = new Client("C001", "John", "Doe");
    }

    @Test
    public void testAddAccount() {
        Account account = new Account(bank, client);
        bank.addAccount(account);
        
        assertTrue(bank.getAccount(client).isPresent());
    }

    @Test
    public void testGetAccountNotFound() {
        assertFalse(bank.getAccount(client).isPresent());
    }
}
