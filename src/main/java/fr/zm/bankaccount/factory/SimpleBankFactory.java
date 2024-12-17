package fr.zm.bankaccount.factory;

import fr.zm.bankaccount.account.Bank;
import fr.zm.bankaccount.account.Account;
import fr.zm.bankaccount.account.Client;

public class SimpleBankFactory implements BankFactory {
    @Override
    public Bank createBank(String name) {
        return new Bank(name);
    }

    @Override
    public Account createAccount(Bank bank, Client client) {
        return new Account(bank, client); 
    }

    @Override
    public Client createClient(String clientId, String firstName, String lastName) {
        return new Client(clientId, firstName, lastName); 
    }
}
