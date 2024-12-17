package fr.zm.bankaccount.factory;

import fr.zm.bankaccount.account.Bank;
import fr.zm.bankaccount.account.Account;
import fr.zm.bankaccount.account.Client;

public interface BankFactory {
    Bank createBank(String name);
    Account createAccount(Bank bank, Client client);
    Client createClient(String clientId, String firstName, String lastName); 
}
