package fr.zm.bankaccount.enums;

public enum ErrorMessages {

    ACCOUNT_NOT_FOUND("Account not found for client: "),
    UNKNOWN_CLIENT(": You don't belong here !"),
    INSUFFICIENT_FUNDS("Insufficient funds for withdrawal.");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}