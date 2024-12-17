package fr.zm.bankaccount.account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import fr.zm.bankaccount.enums.ErrorMessages;
import fr.zm.bankaccount.enums.TransactionType;
import fr.zm.bankaccount.exceptions.InvalidTransactionException;
import lombok.Getter;

/**
 * Client bank account
 *
 */
@Getter
public class Account {
	private Bank bank;
	private Client client;
	private final UUID number;
	private final List<Transaction> transactions;

	public Account(Bank bank, Client client) {
		this.bank = bank;
		this.number = UUID.randomUUID();
		this.client = client;
		this.transactions = new ArrayList<>();
	}

	public Transaction addTransaction(Client client, LocalDate date, BigDecimal amount, TransactionType type,
			BigDecimal newBalance)
			throws InvalidTransactionException {
		validateTransaction(client, date, amount, type);
		Transaction newTransaction = new Transaction(client, date, amount, type, newBalance);
		transactions.add(newTransaction);
		return newTransaction;
	}

	/**
	 * Verifies that a transaction is valid
	 * 
	 * @param date
	 * @param amount
	 * @throws InvalidTransactionException
	 */
	private void validateTransaction(Client client, LocalDate date, BigDecimal amount, TransactionType type)
			throws InvalidTransactionException {

		if (client.equals(client) && amount.compareTo(new BigDecimal(0)) <= 0)
			throw new InvalidTransactionException(ErrorMessages.AMOUNT_MUST_BE_POSITIVE.getMessage());
	}

	public BigDecimal getBalance() {
		return transactions.stream()
				.map(Transaction::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public List<Transaction> getTransactions() {
		return Collections.unmodifiableList(transactions);
	}

	public Client getClient() {
		return client;
	}

}
