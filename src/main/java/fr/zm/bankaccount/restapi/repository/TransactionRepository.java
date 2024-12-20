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

<<<<<<< HEAD
    public List<Transaction> findByClientId(String clientId) {
        List<Transaction> clientTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getClient().getClientId().equals(clientId)) {
                clientTransactions.add(transaction);
            }
        }
        return clientTransactions;
=======
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
>>>>>>> origin/bank-account-feature
    }
}
