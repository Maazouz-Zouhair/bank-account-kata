package fr.zm.bankaccount.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import fr.zm.bankaccount.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A transaction on an account
 */
@Getter
@AllArgsConstructor
public final class Transaction {

    private final Client client;
    private final LocalDateTime date;
    private final BigDecimal amount;
    private final TransactionType type;
    private final BigDecimal balance;

}
