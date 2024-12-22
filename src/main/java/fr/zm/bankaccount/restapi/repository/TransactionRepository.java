package fr.zm.bankaccount.restapi.repository;

import fr.zm.bankaccount.account.Transaction;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();

    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> findByClientId(String clientId) {
        List<Transaction> clientTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getClient().getClientId().equals(clientId)) {
                clientTransactions.add(transaction);
            }
        }
        return clientTransactions;
    }
}
