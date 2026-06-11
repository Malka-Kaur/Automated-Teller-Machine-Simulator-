package com.atm.model;

import com.atm.exception.InsufficientFundsException;
import com.atm.exception.InvalidAmountException;
import com.atm.exception.InvalidPinException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Account {
    private String accountNumber;
    private String pin;
    private double balance;
    private final List<Transaction> transactionHistory;
    private int failedPinAttempts;
    private boolean locked;

    protected Account(String accountNumber, String pin, double balance) {
        this.accountNumber = validateAccountNumber(accountNumber);
        this.pin = validatePin(pin);
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
        this.failedPinAttempts = 0;
        this.locked = false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = validateAccountNumber(accountNumber);
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    public int getFailedPinAttempts() {
        return failedPinAttempts;
    }

    public void setFailedPinAttempts(int failedPinAttempts) {
        if (failedPinAttempts < 0) {
            throw new IllegalArgumentException("Failed PIN attempts cannot be negative.");
        }
        this.failedPinAttempts = failedPinAttempts;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean verifyPin(String pin) {
        return this.pin.equals(pin);
    }

    public void changePin(String oldPin, String newPin) throws InvalidPinException {
        if (!verifyPin(oldPin)) {
            throw new InvalidPinException("Current PIN is incorrect.");
        }
        this.pin = validatePin(newPin);
        addTransaction("PIN Change", 0.0, "SUCCESS");
    }

    public void incrementFailedPinAttempts() {
        failedPinAttempts++;
    }

    public void resetFailedPinAttempts() {
        failedPinAttempts = 0;
    }

    public abstract void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException;

    public void deposit(double amount) throws InvalidAmountException {
        validateAmount(amount);
        balance += amount;
        addTransaction("Deposit", amount, "SUCCESS");
    }

    public void addTransaction(String type, double amount, String status) {
        transactionHistory.add(new Transaction(type, amount, LocalDateTime.now(), status));
    }

    protected void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero.");
        }
    }

    private String validateAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty.");
        }
        return accountNumber.trim();
    }

    private String validatePin(String pin) {
        if (pin == null || !pin.matches("\\d{4,6}")) {
            throw new IllegalArgumentException("PIN must contain 4 to 6 digits.");
        }
        return pin;
    }
}
