# 🏦 Advanced ATM Simulator (OOP Java Project)

A robust, enterprise-grade console-based Automated Teller Machine (ATM) simulation built with **Java**. This project moves beyond a basic programming assignment by implementing real-world software architecture principles, strict encapsulation, multi-account handling, and production-level custom exception handling.

---

## 🚀 Key Features

* **Multi-Tier Account Architecture:** Utilizes abstract classes and polymorphism to support distinct account types (`SavingsAccount` with minimum balance thresholds and `CurrentAccount` with overdraft protections).
* **Robust In-Memory Database:** Uses a centralized `Bank` service layer to manage dynamic lookups, cross-account validation, and centralized data security.
* **Security & Account Locking:** Tracks consecutive failed authentication attempts per account. If a user inputs an incorrect PIN 3 times in a row, the account is systematically locked via a custom `AccountLockedException`.
* **Transaction Logging (Mini-Statements):** Generates structural `Transaction` objects tracking type, amount, status, and precise real-time timestamps (`java.time.LocalDateTime`). Users can print their last 5 activities.
* **Fail-Safe Input Handling:** Advanced input parsing ensures that common terminal input bugs (like entering text characters instead of integers) do not crash the application buffer.

---

## 🛠️ Tech Stack & OOP Concepts Demonstrated

* **Language:** Java 8+
* **Encapsulation:** All sensitive account fields (balance, PIN status) are strictly marked `private` and manipulated exclusively through structural validation methods.
* **Inheritance & Polymorphism:** Subclasses override core transactional behavior (`withdraw()`) to execute specific real-world banking business rules dynamically.
* **Custom Exception Handling:** Built-in customized checked exception hierarchy rather than generic system runtime errors.

---

## 📁 Project Structure

The project follows a standard multi-layer package architecture to separate models, business logic, and user interface handlers:

```text
src/
└── com/atm/
    ├── exception/             # Custom structural banking exceptions
    │   ├── InsufficientFundsException.java
    │   ├── AccountLockedException.java
    │   ├── InvalidPinException.java
    │   └── InvalidAmountException.java
    ├── model/                 # Domain objects and core data blueprints
    │   ├── Account.java (Abstract Base)
    │   ├── SavingsAccount.java
    │   ├── CurrentAccount.java
    │   └── Transaction.java
    ├── service/               # Core backend data processing
    │   └── Bank.java          # Virtual account database & authentication
    ├── controller/            # Console UI interface loops
    │   └── ATMController.java # User input and system menus
    └── Main.java              # Application bootstrapper and data seeder


## Project Code Path
Automated-Teller-Machine-Simulator-/ATM_Simulator/src/com/atm/controller/ATMController.java
