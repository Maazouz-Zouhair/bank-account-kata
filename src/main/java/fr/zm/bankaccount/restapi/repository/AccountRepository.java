package fr.zm.bankaccount.restapi.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import fr.zm.bankaccount.account.Account;

@Repository
public class AccountRepository {
    private final List<Account> accounts = new ArrayList<>();

    public void save(Account account) {
        accounts.add(account);
    }

    public Optional<Account> findByClientId(String clientId) {
        return accounts.stream()
                .filter(account -> account.getClient().getClientId().equals(clientId))
                .findFirst();
    }
}
