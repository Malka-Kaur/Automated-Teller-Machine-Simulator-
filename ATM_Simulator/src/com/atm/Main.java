package com.atm;

import com.atm.controller.ATMController;
import com.atm.model.CurrentAccount;
import com.atm.model.SavingsAccount;
import com.atm.service.Bank;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        seedAccounts(bank);

        ATMController controller = new ATMController(bank);
        controller.start();
    }

    private static void seedAccounts(Bank bank) {
        bank.addAccount(new SavingsAccount("100100", "1234", 10000.00));
        bank.addAccount(new CurrentAccount("200200", "4321", 5000.00));
    }
}
