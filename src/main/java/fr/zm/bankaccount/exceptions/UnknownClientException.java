package fr.zm.bankaccount.exceptions;

public class UnknownClientException extends RuntimeException {
    public UnknownClientException(String message) {
        super(message);
    }
}
