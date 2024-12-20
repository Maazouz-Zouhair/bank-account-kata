package fr.zm.bankaccount.exceptions;

public class InvalidTransactionAmountException extends IllegalArgumentException  {
    public InvalidTransactionAmountException(String message) {
        super(message);
    }
}