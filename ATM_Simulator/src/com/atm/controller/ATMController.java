package com.atm.controller;

import com.atm.exception.AccountLockedException;
import com.atm.exception.InsufficientFundsException;
import com.atm.exception.InvalidAmountException;
import com.atm.exception.InvalidPinException;
import com.atm.model.Account;
import com.atm.model.Transaction;
import com.atm.service.Bank;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ATMController {
    private final Bank bank;
    private final Scanner scanner;

    public ATMController(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the Console ATM Simulator");
        boolean running = true;

        while (running) {
            try {
                Account account = login();
                showMainMenu(account);
            } catch (AccountLockedException ex) {
                System.out.println("Error: This account has been locked due to 3 failed login attempts. Please contact support.");
            } catch (InvalidPinException ex) {
                System.out.println("Authentication failed: " + ex.getMessage());
            } catch (NoSuchElementException ex) {
                System.out.println("Input stream closed. Shutting down ATM session.");
                running = false;
            } catch (Exception ex) {
                System.out.println("Unexpected error: " + ex.getMessage());
            }
        }
    }

    private Account login() throws InvalidPinException, AccountLockedException {
        System.out.println();
        System.out.println("Login");
        String accountNumber = readLine("Account Number: ");

        if (!bank.accountExists(accountNumber)) {
            throw new InvalidPinException("Invalid account number.");
        }

        while (true) {
            try {
                String pin = readLine("PIN: ");
                return bank.authenticate(accountNumber, pin);
            } catch (InvalidPinException ex) {
                System.out.println("Authentication failed: " + ex.getMessage());
            }
        }
    }

    private void showMainMenu(Account account) {
        boolean loggedIn = true;

        while (loggedIn) {
            printMenu();
            try {
                int choice = readInt("Choose an option: ");

                switch (choice) {
                    case 1:
                        withdraw(account);
                        break;
                    case 2:
                        deposit(account);
                        break;
                    case 3:
                        checkBalance(account);
                        break;
                    case 4:
                        printMiniStatement(account);
                        break;
                    case 5:
                        changePin(account);
                        break;
                    case 6:
                        loggedIn = false;
                        System.out.println("Logged out successfully.");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose between 1 and 6.");
                }
            } catch (InsufficientFundsException | InvalidAmountException ex) {
                System.out.println("Transaction failed: " + ex.getMessage());
            } catch (NoSuchElementException ex) {
                System.out.println("Input stream closed. Logging out.");
                loggedIn = false;
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("Main Menu");
        System.out.println("1. Withdraw Money");
        System.out.println("2. Deposit Money");
        System.out.println("3. Check Balance");
        System.out.println("4. View Mini-Statement");
        System.out.println("5. Change PIN");
        System.out.println("6. Logout");
    }

    private void withdraw(Account account) throws InsufficientFundsException, InvalidAmountException {
        double amount = readDouble("Enter withdrawal amount: ");
        account.withdraw(amount);
        System.out.println("Withdrawal successful. Current balance: Rs. " + String.format("%.2f", account.getBalance()));
    }

    private void deposit(Account account) throws InvalidAmountException {
        double amount = readDouble("Enter deposit amount: ");
        account.deposit(amount);
        System.out.println("Deposit successful. Current balance: Rs. " + String.format("%.2f", account.getBalance()));
    }

    private void checkBalance(Account account) {
        System.out.println("Current balance: Rs. " + String.format("%.2f", account.getBalance()));
    }

    private void printMiniStatement(Account account) {
        List<Transaction> transactions = account.getTransactionHistory();

        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        System.out.println();
        System.out.println("Mini-Statement");
        System.out.println("Timestamp            | Type         | Amount       | Status");
        System.out.println("---------------------------------------------------------------");

        int startIndex = Math.max(0, transactions.size() - 5);
        for (int i = startIndex; i < transactions.size(); i++) {
            System.out.println(transactions.get(i).format());
        }
    }

    private void changePin(Account account) {
        try {
            String oldPin = readLine("Enter current PIN: ");
            String newPin = readLine("Enter new PIN (4 to 6 digits): ");

            account.changePin(oldPin, newPin);
            System.out.println("PIN changed successfully.");
        } catch (IllegalArgumentException | InvalidPinException ex) {
            System.out.println("PIN change failed: " + ex.getMessage());
        }
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readLine(prompt));
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a valid amount.");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
