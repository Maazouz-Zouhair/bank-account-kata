package fr.zm.bankaccount.enums;

public enum TransactionType {
    DEPOSIT("Deposit");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String get() {
        return description;
    }
}