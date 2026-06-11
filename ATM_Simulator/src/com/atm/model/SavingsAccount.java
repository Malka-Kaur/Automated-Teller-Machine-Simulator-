package com.atm.model;

import com.atm.exception.InsufficientFundsException;
import com.atm.exception.InvalidAmountException;

public class SavingsAccount extends Account {
    private static final double MINIMUM_BALANCE = 1000.0;

    public SavingsAccount(String accountNumber, String pin, double balance) {
        super(accountNumber, pin, balance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {
        validateAmount(amount);

        if (getBalance() - amount < MINIMUM_BALANCE) {
            addTransaction("Withdrawal", amount, "FAILED");
            throw new InsufficientFundsException(
                    "Withdrawal denied. Savings account must maintain a minimum balance of Rs. "
                            + String.format("%.2f", MINIMUM_BALANCE) + ".");
        }

        setBalance(getBalance() - amount);
        addTransaction("Withdrawal", amount, "SUCCESS");
    }
}
