package PR11;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
class Customer implements Serializable {
    private static final long serialVersionUID = 1;
    private String customerId;
    private String name;
    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }
    @Override
    public String toString() {
        return name + " (" + customerId + ")";
    }
}
class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String accountNumber;
    protected double balance;
    protected Customer customer;
    public Account(String accountNumber, Customer customer, double balance) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
    }
    public void deposit(double amount) {
        balance += amount;
    }
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        }
    }
    @Override
    public String toString() {
        return "Account No: " + accountNumber
                + ", Customer: " + customer
                + ", Balance: " + balance;
    }
}
class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    public SavingsAccount(String accountNumber,
                          Customer customer,
                          double balance) {
        super(accountNumber, customer, balance);
    }
    @Override
    public String toString() {
        return "Savings " + super.toString();
    }
}
class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;
    public CurrentAccount(String accountNumber,
                          Customer customer,
                          double balance) {
        super(accountNumber, customer, balance);
    }
    @Override
    public String toString() {
        return "Current " + super.toString();
    }
}

class StatePersister {
    public static void save(Account[] accounts, Path file) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                         new FileOutputStream(file.toFile()))) {
            out.writeObject(accounts);
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Account[] load(Path file) {
        try (ObjectInputStream in =
                     new ObjectInputStream(
                         new FileInputStream(file.toFile()))) {
            return (Account[]) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Account[0];
        }
    }
}
class TransactionLog {
    public static void append(Path file, String line) {
        try {
            Files.write(
                file,
                (line + System.lineSeparator()).getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class ReportGenerator {
    public static void generate(Path logsFolder,
                                Path reportFile) {
        long totalDeposits = 0;
        long totalWithdrawals = 0;
        StringBuilder report = new StringBuilder();
        report.append("===== MiniBank End-of-Day Report =====\n\n");
        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(logsFolder)) {
            for (Path file : stream) {
                if (!Files.isRegularFile(file)) {
                    continue;
                }
                report.append("File: ")
                      .append(file.getFileName())
                      .append("\n");
                BasicFileAttributes attributes =
                        Files.readAttributes(
                            file,
                            BasicFileAttributes.class
                        );
                report.append("Size: ")
                      .append(attributes.size())
                      .append(" bytes\n");
                report.append("Last Modified: ")
                      .append(attributes.lastModifiedTime())
                      .append("\n");
                try (BufferedReader reader =
                             Files.newBufferedReader(file)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        report.append(line).append("\n");
                        String[] parts = line.split(",");
                        if (parts.length >= 2) {
                            String type = parts[0].trim();
                            long amount =
                                    Long.parseLong(parts[1].trim());
                            if (type.equalsIgnoreCase("DEPOSIT")) {
                                totalDeposits += amount;
                            }
                            if (type.equalsIgnoreCase("WITHDRAW")) {
                                totalWithdrawals += amount;
                            }
                        }
                    }
                }
                report.append("\n");
            }
            report.append("===== Totals =====\n");
            report.append("Total Deposits: ")
                  .append(totalDeposits)
                  .append("\n");
            report.append("Total Withdrawals: ")
                  .append(totalWithdrawals)
                  .append("\n");
            Files.writeString(
                reportFile,
                report.toString(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
public class MiniBank11 {
    public static void main(String[] args) {
        try {
            Path logsFolder = Paths.get("logs");
            if (!Files.exists(logsFolder)) {
                Files.createDirectory(logsFolder);
            }
            Customer c1 =
                    new Customer("C001", "Rahul");
            Customer c2 =
                    new Customer("C002", "Priya");
            Account[] accounts = {
                new SavingsAccount(
                    "A001", c1, 10000
                ),
                new CurrentAccount(
                    "A002", c2, 20000
                )
            };
            Path log1 =
                    logsFolder.resolve("transactions1.txt");
            Path log2 =
                    logsFolder.resolve("transactions2.txt");
            TransactionLog.append(
                log1,
                "DEPOSIT,5000"
            );
            TransactionLog.append(
                log1,
                "WITHDRAW,1000"
            );
            TransactionLog.append(
                log2,
                "DEPOSIT,3000"
            );
            TransactionLog.append(
                log2,
                "WITHDRAW,500"
            );
            Path stateFile =
                    Paths.get("accounts.dat");
            StatePersister.save(
                accounts,
                stateFile
            );
            Account[] loadedAccounts =
                    StatePersister.load(stateFile);
            System.out.println(
                "\n===== Loaded Accounts ====="
            );
            for (Account account : loadedAccounts) {
                System.out.println(account);
            }
            Path reportFile =
                    Paths.get("report.txt");
            ReportGenerator.generate(
                logsFolder,
                reportFile
            );
            System.out.println(
                "\n===== Report ====="
            );
            String report =
                    Files.readString(reportFile);
            System.out.println(report);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
