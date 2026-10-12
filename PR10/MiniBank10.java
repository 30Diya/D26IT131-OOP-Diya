import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class MiniBank10 {
    static class Account {
        int accountNumber;
        long balance;
        Account(int accountNumber, long balance) {
            this.accountNumber = accountNumber;
            this.balance = balance;
        }
        synchronized void deposit(long amount) {
            balance += amount;
        }
        synchronized boolean withdraw(long amount) {
            if (balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        }
        public String toString() {
            return "Account " + accountNumber +
                   " Balance = " + balance;
        }
    }
    static class TransactionProcessor {
        private ExecutorService executor;
        TransactionProcessor() {
            executor = Executors.newFixedThreadPool(4);
        }
        void submit(Runnable task) {
            executor.execute(task);
        }
        void stop() {
            executor.shutdown();
            try {
                executor.awaitTermination(
                        1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    static class Transaction implements Runnable {
        Account account;
        long amount;
        boolean deposit;
        Transaction(
                Account account,
                long amount,
                boolean deposit) {
            this.account = account;
            this.amount = amount;
            this.deposit = deposit;
        }
        public void run() {
            String name =Thread.currentThread().getName();
            if (deposit) {
                account.deposit(amount);
                System.out.println(
                        name + " deposited " +
                        amount + " into Account " +
                        account.accountNumber);
            } else {
                boolean success =account.withdraw(amount);
                System.out.println(
                        name + " withdrew " +
                        amount + " from Account " +
                        account.accountNumber +
                        " : " +
                        (success ? "Success" : "Failed"));
            }
        }
    }
    static class TransactionBuffer {
        private Transaction[] buffer =new Transaction[5];
        private int count = 0;
        private int putIndex = 0;
        private int getIndex = 0;
        synchronized void put(Transaction t)
                throws InterruptedException {
            while (count == buffer.length) {
                wait();
            }
            buffer[putIndex] = t;
            putIndex =
                    (putIndex + 1) % buffer.length;
            count++;
            System.out.println("Producer added transaction");
            notify();
        }
        synchronized Transaction get()
                throws InterruptedException {
            while (count == 0) {
                wait();
            }
            Transaction t = buffer[getIndex];
            getIndex = (getIndex + 1) % buffer.length;
            count--;
            System.out.println("Consumer took transaction");
            notify();
            return t;
        }
    }
    static void transfer(
            Account a,
            Account b,
            long amount) {
        Account first;
        Account second;
        if (a.accountNumber < b.accountNumber) {
            first = a;
            second = b;
        } else {
            first = b;
            second = a;
        }
        synchronized (first) {
            synchronized (second) {
                if (a.balance >= amount) {
                    a.balance -= amount;
                    b.balance += amount;
                    System.out.println(
                            "Transferred " +
                            amount +
                            " from " +
                            a.accountNumber +
                            " to " +
                            b.accountNumber);
                }
            }
        }
    }
    public static void main(String[] args)
            throws InterruptedException {
        System.out.println("========== MINIBANK ==========");
        Account a1 =new Account(101, 10000);
        Account a2 =new Account(102, 5000);
        System.out.println("\nInitial Accounts:");
        System.out.println(a1);
        System.out.println(a2);
        System.out.println("\n--- Thread Pool Transactions ---");
        TransactionProcessor processor =
                new TransactionProcessor();
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                processor.submit(
                        new Transaction(
                                a1, 100, true));
            } else {
                processor.submit(
                        new Transaction(
                                a1, 50, false));
            }
        }
        processor.stop();
        System.out.println("\nAfter thread pool:");
        System.out.println(a1);
        System.out.println("\n--- Producer Consumer ---");
        TransactionBuffer buffer =
                new TransactionBuffer();
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    Transaction t =
                            new Transaction(
                                    a2,
                                    100,
                                    true);
                    buffer.put(t);
                    System.out.println(
                            "Produced transaction "
                            + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    Transaction t =
                            buffer.get();
                    t.run();
                    System.out.println(
                            "Consumed transaction "
                            + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("\nProducer and Consumer completed.");
        System.out.println("\n--- Safe Transfers ---");
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                transfer(a1, a2, 100);
            }
        }, "Transfer-A-to-B");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                transfer(a2, a1, 50);
            }
        }, "Transfer-B-to-A");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("\n========== FINAL ==========");
        System.out.println(a1);
        System.out.println(a2);
        System.out.println("\nAll transactions completed.");
    }
}
