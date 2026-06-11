package com.atm.exception;

public class AccountLockedException extends Exception {
    public AccountLockedException(String message) {
        super(message);
    }
}
