# ATM Simulator

Console-based ATM Simulator written in Java with a clean package structure.

## Run

From the project root:

```powershell
javac -d out '@sources.txt'
java -cp out com.atm.Main
```

Do not compile from inside `src/com/atm`. Because the project uses packages, Java must be compiled from the project root.

## Seed Accounts

- Savings account: `100100`, PIN: `1234`
- Current account: `200200`, PIN: `4321`
