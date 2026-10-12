public class MiniBank9 {

    static class Account {
        private long balance;

        Account(long balance) {
            this.balance = balance;
        }

        synchronized void deposit(long amount) {
            String threadName = Thread.currentThread().getName();

            System.out.println(threadName + " depositing " + amount);

            long temp = balance;

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            balance = temp + amount;

            System.out.println(
                    threadName +
                    " finished deposit. Balance = " +
                    balance);
        }

        synchronized boolean withdraw(long amount) {
            String threadName = Thread.currentThread().getName();

            System.out.println(
                    threadName + " trying to withdraw " + amount);

            if (amount > 0 && amount <= balance) {
                balance -= amount;
                System.out.println(
                        threadName + " withdrawal successful");
                return true;
            }

            System.out.println(threadName + " withdrawal failed");
            return false;
        }

        long getBalance() {
            return balance;
        }
    }

    static class AccountWorker implements Runnable {
        private Account account;
        private int times;
        private long amount;

        AccountWorker(Account account, int times, long amount) {
            this.account = account;
            this.times = times;
            this.amount = amount;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();

            System.out.println(threadName + " started.");

            for (int i = 0; i < times; i++) {
                account.deposit(amount);
            }

            System.out.println(threadName + " finished.");
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        System.out.println("========== MINIBANK ==========");

        long startingBalance = 0;
        int numberOfThreads = 10;
        int times = 10;
        long amount = 100;

        Account account = new Account(startingBalance);

        System.out.println(
                "\nInitial balance: " + account.getBalance());
        System.out.println("Threads: " + numberOfThreads);
        System.out.println("Deposits per thread: " + times);
        System.out.println("Amount per deposit: " + amount);

        long expected =
                startingBalance + (numberOfThreads * times * amount);

        System.out.println("Expected balance: " + expected);

        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            AccountWorker worker =
                    new AccountWorker(account, times, amount);

            threads[i] = new Thread(worker, "Worker-" + (i + 1));
        }

        System.out.println("\nStarting threads...\n");

        for (Thread thread : threads) {
            System.out.println("Starting: " + thread.getName());
            thread.start();
        }

        System.out.println("\nWaiting for threads...\n");

        for (Thread thread : threads) {
            thread.join();
            System.out.println(thread.getName() + " joined.");
        }

        System.out.println("\n========== RESULT ==========");

        System.out.println("Expected balance: " + expected);
        System.out.println("Actual balance:   " + account.getBalance());

        if (account.getBalance() == expected) {
            System.out.println("Result is CORRECT.");
        } else {
            System.out.println("Result is WRONG due to race condition.");
        }

        System.out.println("\n========== WITHDRAW TEST ==========");

        long withdrawalAmount = 500;

        System.out.println("Current balance: " + account.getBalance());
        System.out.println("Trying to withdraw: " + withdrawalAmount);

        boolean success = account.withdraw(withdrawalAmount);

        if (success) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Withdrawal failed.");
        }

        System.out.println(
                "Balance after withdrawal: " + account.getBalance());

        System.out.println("\n========== FINAL ==========");
        System.out.println("All threads completed.");
        System.out.println("Final balance: " + account.getBalance());
        System.out.println("Program finished.");
    }
}