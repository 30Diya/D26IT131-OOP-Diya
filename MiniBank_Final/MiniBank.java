package MiniBank_Final;

import java.io.*;
import java.util.*;

public class MiniBank {

    static class Account implements Serializable {
        String number;
        String name;
        long balance;

        Account(String number, String name, long balance) {
            this.number = number;
            this.name = name;
            this.balance = balance;
        }

        public String toString() {
            return number + " | " + name + " | Rs. " + balance;
        }
    }

    static Scanner sc = new Scanner(System.in);
    static Map<String, Account> accounts = new LinkedHashMap<>();

    static long totalDeposits = 0;
    static long totalWithdrawals = 0;
    static int nextAccount = 1;

    static final String FILE_NAME = "accounts.dat";

    static void loadData() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No saved account data found.");
            return;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(file))) {

            accounts = (Map<String, Account>) in.readObject();
            nextAccount = accounts.size() + 1;
            System.out.println("Account data loaded successfully.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load saved account data.");
        }
    }

    static void saveData() {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(accounts);
            System.out.println("Account data saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving account data.");
        }
    }

    static long readAmount(String message) {
        System.out.print(message);

        try {
            return Long.parseLong(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static Account findAccount(String number) {
        Account a = accounts.get(number);

        if (a == null) {
            System.out.println("Account not found!");
        }

        return a;
    }

    static void openAccount() {
        System.out.print("Enter customer name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Customer name is required!");
            return;
        }

        long balance = readAmount("Enter opening balance: ");

        if (balance < 0) {
            System.out.println("Invalid opening balance!");
            return;
        }

        String number = String.format("AC%04d", nextAccount++);
        Account a = new Account(number, name, balance);
        accounts.put(number, a);

        System.out.println("Account opened successfully!");
        System.out.println("Account Number: " + number);
        System.out.println("Customer Name: " + name);
        System.out.println("Balance: Rs. " + balance);
    }

    static void deposit() {
        System.out.print("Enter account number: ");
        Account a = findAccount(sc.nextLine().trim());

        if (a == null) return;

        long amount = readAmount("Enter deposit amount: ");

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero!");
            return;
        }

        a.balance += amount;
        totalDeposits += amount;

        System.out.println("Deposit successful!");
        System.out.println("Updated Balance: Rs. " + a.balance);
    }

    static void withdraw() {
        System.out.print("Enter account number: ");
        Account a = findAccount(sc.nextLine().trim());

        if (a == null) return;

        long amount = readAmount("Enter withdrawal amount: ");

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero!");
            return;
        }

        if (amount > a.balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        a.balance -= amount;
        totalWithdrawals += amount;

        System.out.println("Withdrawal successful!");
        System.out.println("Updated Balance: Rs. " + a.balance);
    }

    static void transfer() {
        System.out.print("Enter sender account number: ");
        Account sender = findAccount(sc.nextLine().trim());

        if (sender == null) return;

        System.out.print("Enter receiver account number: ");
        Account receiver = findAccount(sc.nextLine().trim());

        if (receiver == null) return;

        if (sender == receiver) {
            System.out.println("Cannot transfer to the same account!");
            return;
        }

        long amount = readAmount("Enter transfer amount: ");

        if (amount <= 0 || amount > sender.balance) {
            System.out.println("Invalid amount or insufficient balance!");
            return;
        }

        sender.balance -= amount;
        receiver.balance += amount;

        System.out.println("Transfer successful!");
        System.out.println("Sender Balance: Rs. " + sender.balance);
        System.out.println("Receiver Balance: Rs. " + receiver.balance);
    }

    static void statement() {
        System.out.print("Enter account number: ");
        Account a = findAccount(sc.nextLine().trim());

        if (a == null) return;

        System.out.println("\n========== ACCOUNT STATEMENT ==========");
        System.out.println("Account Number: " + a.number);
        System.out.println("Customer Name: " + a.name);
        System.out.println("Current Balance: Rs. " + a.balance);
        System.out.println("=======================================");
    }

    static void listAccounts() {
        System.out.println("\n========== ALL ACCOUNTS ==========");

        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
        } else {
            for (Account a : accounts.values()) {
                System.out.println(a);
            }
        }
    }

    static void report() {
        System.out.println("\n========== MINIBANK REPORT ==========");
        System.out.println("Total Accounts: " + accounts.size());
        System.out.println("Total Deposits: Rs. " + totalDeposits);
        System.out.println("Total Withdrawals: Rs. " + totalWithdrawals);
        System.out.println("Net Transaction Change: Rs. "
                + (totalDeposits - totalWithdrawals));
        System.out.println("=====================================");
    }

    public static void main(String[] args) {
        System.out.println("================================");
        System.out.println("          MiniBank");
        System.out.println("        Main Branch");
        System.out.println("================================");

        System.out.println("\nLoading saved account data...");
        loadData();

        boolean running = true;

        while (running) {
            System.out.println("\n========== MINIBANK MENU ==========");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Account Statement");
            System.out.println("6. List All Accounts");
            System.out.println("7. Run Bank Report");
            System.out.println("8. Exit");
            System.out.println("===================================");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine().trim();

            switch (input) {
                case "1": openAccount(); break;
                case "2": deposit(); break;
                case "3": withdraw(); break;
                case "4": transfer(); break;
                case "5": statement(); break;
                case "6": listAccounts(); break;
                case "7": report(); break;
                case "8":
                    System.out.println("\nSaving account data...");
                    saveData();
                    System.out.println("Thank you for using MiniBank!");
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }

        sc.close();
    }
}
