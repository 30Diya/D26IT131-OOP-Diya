package PR6;
import static java.lang.System.out;
public class MiniBank6 {
    interface Transactable {
        void deposit(long amount);
        boolean withdraw(long amount);
    }
    interface InterestBearing {
        double interestRate();
        default double yearlyInterest(long balance) {
            return interestRate() * balance;
        }
    }
    interface Premium {
    }
    @FunctionalInterface
    interface WithdrawRule {
        boolean allow(Account account, long amount);
    }
    static class Account implements Transactable, InterestBearing, Premium {
        private long balance;
        Account(long balance) {
            this.balance = balance;
        }
        public long getBalance() {
            return balance;
        }
        @Override
        public void deposit(long amount) {
            if (amount > 0) {
                balance += amount;
            }
        }
        @Override
        public boolean withdraw(long amount) {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                return true;
            }
            return false;
        }
        @Override
        public double interestRate() {
            return 0.05;
        }
        public double yearlyInterest() {
            return interestRate() * balance;
        }
    }
    public static void main(String[] args) {
        Account account = new Account(10000);
        out.println("Initial balance: " + account.getBalance());
        account.deposit(5000);
        out.println("After deposit: " + account.getBalance());
        WithdrawRule rule1 = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount > 0 && amount <= account.getBalance();
            }
        };
        long amount1 = 3000;
        if (rule1.allow(account, amount1)) {
            account.withdraw(amount1);
            out.println("Anonymous class withdrawal successful.");
        } else {
            out.println("Anonymous class withdrawal denied.");
        }
        out.println("Balance: " + account.getBalance());
        WithdrawRule rule2 =
                (acc, amount) -> amount > 0 && amount <= 5000;
        long amount2 = 4000;
        if (rule2.allow(account, amount2)) {
            account.withdraw(amount2);
            out.println("Lambda withdrawal successful.");
        } else {
            out.println("Lambda withdrawal denied.");
        }
        out.println("Balance: " + account.getBalance());
        out.println("Yearly interest: " + account.yearlyInterest());
        if (account instanceof Premium) {
            out.println("Account is Premium.");
        }
    }
}
