package com.atm.service;

import com.atm.exception.AccountLockedException;
import com.atm.exception.InvalidPinException;
import com.atm.model.Account;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private static final int MAX_FAILED_PIN_ATTEMPTS = 3;

    private final Map<String, Account> accounts;

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public void addAccount(Account acc) {
        if (acc == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        accounts.put(acc.getAccountNumber(), acc);
    }

    public boolean accountExists(String accountNumber) {
        return accounts.containsKey(normalize(accountNumber));
    }

    public Account authenticate(String accountNumber, String pin) throws InvalidPinException, AccountLockedException {
        String normalizedAccountNumber = normalize(accountNumber);
        String normalizedPin = normalize(pin);
        Account account = accounts.get(normalizedAccountNumber);

        if (account == null) {
            throw new InvalidPinException("Invalid account number or PIN.");
        }

        if (account.isLocked()) {
            throw new AccountLockedException("Account is locked due to too many failed PIN attempts.");
        }

        if (!account.verifyPin(normalizedPin)) {
            account.incrementFailedPinAttempts();

            if (account.getFailedPinAttempts() >= MAX_FAILED_PIN_ATTEMPTS) {
                account.setLocked(true);
                throw new AccountLockedException("Account locked after 3 failed PIN attempts.");
            }

            int remainingAttempts = MAX_FAILED_PIN_ATTEMPTS - account.getFailedPinAttempts();
            throw new InvalidPinException("Invalid account number or PIN. Attempts remaining: " + remainingAttempts);
        }

        account.resetFailedPinAttempts();
        return account;
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}
