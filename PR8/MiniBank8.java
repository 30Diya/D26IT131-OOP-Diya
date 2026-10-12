package PR8;
public class MiniBank8 {
    static class BankException extends Exception {
        BankException(String msg) {
            super(msg);
        }
    }
    static class InsufficientFundsException extends BankException {
        long shortfall;
        InsufficientFundsException(String msg, long shortfall) {
            super(msg);
            this.shortfall = shortfall;
        }
    }
    static class AccountNotFoundException extends BankException {
        AccountNotFoundException(String msg) {
            super(msg);
        }
    }
    static class InvalidAmountException extends BankException {
        InvalidAmountException(String msg) {
            super(msg);
        }
    }
    static class Account {
        private String number;
        private String name;
        private long balance;
        Account(String number, String name, long balance) {
            this.number = number;
            this.name = name;
            this.balance = balance;
        }
        long getBalance() {
            return balance;
        }
        void deposit(long amount) throws InvalidAmountException {
            if (amount <= 0)
                throw new InvalidAmountException("Amount must be greater than zero");
            balance += amount;
        }
        void withdraw(long amount)
                throws InsufficientFundsException,
                InvalidAmountException {
            if (amount <= 0)
                throw new InvalidAmountException("Amount must be greater than zero");
            if (amount > balance) {
                long shortfall = amount - balance;
                throw new InsufficientFundsException("Insufficient funds", shortfall);
            }
            balance -= amount;
        }
        void transfer(Account to, long amount)
                throws BankException {
            try {
                if (to == null)
                    throw new AccountNotFoundException("Destination account not found");
                withdraw(amount);
                to.deposit(amount);
                System.out.println("Transfer successful: " + amount);
            } catch (BankException e) {
                System.out.println("Transfer failed: " + e.getMessage());
                throw e;
            } finally {
                System.out.println("Transfer operation completed");
            }
        }
        void show() {
            System.out.println(number + " | " + name +" | Balance: " + balance);
        }
    }
    static class BankResource implements AutoCloseable {
        BankResource() {
            System.out.println("Resource opened");
        }
        void use() throws BankException {
            System.out.println("Using resource");
            throw new BankException("Error inside resource");
        }
        public void close() {
            System.out.println("Resource closed");
        }
    }
    public static void main(String[] args) {
        Account a1 =new Account("A101", "Rahul", 10000);
        Account a2 =new Account("A102", "Amit", 5000);
        System.out.println("===== MINIBANK =====");
        System.out.println("\nInitial Accounts:");
        a1.show();
        a2.show();
        System.out.println("\n--- Deposit ---");
        try {
            a1.deposit(2000);
            System.out.println("Deposit successful");
            a1.show();
        } catch (InvalidAmountException e) {
            System.out.println("Deposit error: " + e.getMessage());
        } finally {
            System.out.println("Deposit operation finished");
        }
        System.out.println("\n--- Invalid Deposit ---");
        try {
            a1.deposit(-100);
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Invalid deposit finished");
        }
        System.out.println("\n--- Withdrawal ---");
        try {
            a1.withdraw(3000);
            System.out.println("Withdrawal successful");
            a1.show();
        } catch (InvalidAmountException e) {
            System.out.println("Invalid amount: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("Insufficient funds");
            System.out.println("Shortfall: " + e.shortfall);
        } finally {
            System.out.println("Withdrawal operation finished");
        }
        System.out.println("\n--- Invalid Withdrawal ---");
        try {
            a1.withdraw(0);
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("Shortfall: " + e.shortfall);
        } finally {
            System.out.println("Invalid withdrawal finished");
        }
        System.out.println("\n--- Insufficient Funds ---");
        try {
            a1.withdraw(50000);
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed");
            System.out.println("Shortfall: " + e.shortfall);
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println(
                    "Funds check finished");
        }
        System.out.println("\n--- Transfer ---");
        try {
            a1.transfer(a2, 2000);
        } catch (BankException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Transfer process finished");
        }
        System.out.println("\n--- Try With Resources ---");
        try (BankResource r = new BankResource()) {
            r.use();
        } catch (BankException e) {
            System.out.println("Caught: " + e.getMessage());
        } finally {
            System.out.println("Resource test finished");
        }
        System.out.println("\nFinal Accounts:");
        a1.show();
        a2.show();
        System.out.println("\n===== END =====");
    }
}
