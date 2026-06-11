package com.atm.model;

import com.atm.exception.InsufficientFundsException;
import com.atm.exception.InvalidAmountException;

public class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 5000.0;

    public CurrentAccount(String accountNumber, String pin, double balance) {
        super(accountNumber, pin, balance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {
        validateAmount(amount);

        if (getBalance() - amount < -OVERDRAFT_LIMIT) {
            addTransaction("Withdrawal", amount, "FAILED");
            throw new InsufficientFundsException(
                    "Withdrawal denied. Current account overdraft limit is Rs. "
                            + String.format("%.2f", OVERDRAFT_LIMIT) + ".");
        }

        setBalance(getBalance() - amount);
        addTransaction("Withdrawal", amount, "SUCCESS");
    }
}
