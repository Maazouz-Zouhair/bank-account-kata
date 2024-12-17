package fr.zm.bankaccount.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;

@Getter
public class Bank {
	private final String name;
	private List<Account> accounts = new ArrayList<Account>();

	public Bank(String name) {
		this.name = name;
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

	public Optional<Account> getAccount(Client client) {
		if (client == null)
			return Optional.empty();
		return accounts.stream()
				.filter(account -> client.equals(account.getClient()))
				.findFirst();
	}

}
