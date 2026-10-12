package PR5;

abstract class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;
    private static long accountCounter = 0;

    private static String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    } // Fixed: missing closing brace

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(long amount) {
        if (amount > 0 && canWithdraw(amount)) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber
                + ", Owner: " + ownerName
                + ", Balance: " + balance;
    }
}

class SavingsAccount extends Account {
    private long minBalance;

    public SavingsAccount(String ownerName,
                          long openingBalance,
                          long minBalance) {
        super(ownerName, openingBalance);
        this.minBalance = minBalance;
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return getBalance() - amount >= minBalance;
    }
}

class CurrentAccount extends Account {
    private long overdraftLimit;

    public CurrentAccount(String ownerName,
                          long openingBalance,
                          long overdraftLimit) {
        super(ownerName, openingBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public double interestRate() {
        return 0.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return getBalance() - amount >= -overdraftLimit;
    }
}

class FixedDepositAccount extends Account {

    public FixedDepositAccount(String ownerName,
                               long openingBalance) {
        super(ownerName, openingBalance);
    }

    @Override
    public double interestRate() {
        return 7.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return false;
    }
}

public class MiniBank5 {
    public static void main(String[] args) {

        Account[] accounts = new Account[3];

        accounts[0] = new SavingsAccount("Rahul", 10000, 5000);
        accounts[1] = new CurrentAccount("Priya", 5000, 3000);
        accounts[2] = new FixedDepositAccount("Amit", 20000);

        System.out.println("===== ACCOUNT DETAILS =====");

        for (Account account : accounts) {
            System.out.println(account);
            System.out.println(
                "Interest Rate: " + account.interestRate() + "%"
            );
            System.out.println();
        }

        System.out.println("===== WITHDRAWAL TEST =====");

        System.out.println(
            "Savings withdrawal: " + accounts[0].withdraw(4000)
        );
        System.out.println(
            "Savings balance: " + accounts[0].getBalance()
        );

        System.out.println(
            "Current withdrawal: " + accounts[1].withdraw(7000)
        );
        System.out.println(
            "Current balance: " + accounts[1].getBalance()
        );

        System.out.println(
            "Fixed Deposit withdrawal: " + accounts[2].withdraw(1000)
        );
        System.out.println(
            "Fixed Deposit balance: " + accounts[2].getBalance()
        );

        System.out.println("\n===== INSTANCEOF TEST =====");

        if (accounts[0] instanceof SavingsAccount) {
            SavingsAccount savings = (SavingsAccount) accounts[0];

            System.out.println("This is a Savings Account.");
            System.out.println(
                "Minimum balance account balance: "
                + savings.getBalance()
            );
        }
    }
}
