package com.atm.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Transaction {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String status;

    public Transaction(String type, double amount, LocalDateTime timestamp, String status) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String format() {
        return String.format("%-20s | %-12s | Rs. %10.2f | %-10s",
                timestamp.format(FORMATTER),
                type,
                amount,
                status);
    }
}
