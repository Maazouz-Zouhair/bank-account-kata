package fr.zm.bankaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import fr.zm.bankaccount.account.Account;
import fr.zm.bankaccount.account.Bank;
import fr.zm.bankaccount.account.Client;
import fr.zm.bankaccount.factory.BankFactory;
import fr.zm.bankaccount.factory.SimpleBankFactory;
import fr.zm.bankaccount.restapi.repository.AccountRepository;
import fr.zm.bankaccount.restapi.repository.ClientRepository;

@SpringBootApplication
public class BankAccountKataApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BankAccountKataApplication.class, args);
        
        // Initialize the bank and clients
        initializeBank(context);
    }

    private static void initializeBank(ApplicationContext context) {
        // Create a factory
        BankFactory bankFactory = new SimpleBankFactory();
        
        // Create a bank
        Bank bank = bankFactory.createBank("My Bank");
        
        // Create clients with explicit clientIds
        Client johnDoe = bankFactory.createClient("C001", "John", "Doe");
        Client janeSmith = bankFactory.createClient("C002", "Jane", "Smith");
        
        // Create accounts using the factory
        Account johnsAccount = bankFactory.createAccount(bank, johnDoe);
        Account janesAccount = bankFactory.createAccount(bank, janeSmith);
        
        // Add accounts to the bank
        bank.addAccount(johnsAccount);
        bank.addAccount(janesAccount);
        
        // Optionally, save clients and accounts to the repositories if needed
        ClientRepository clientRepository = context.getBean(ClientRepository.class);
        AccountRepository accountRepository = context.getBean(AccountRepository.class);
        
        clientRepository.save(johnDoe);
        clientRepository.save(janeSmith);
        accountRepository.save(johnsAccount);
        accountRepository.save(janesAccount);
        
        System.out.println("Bank and clients initialized successfully.");
    }
}
