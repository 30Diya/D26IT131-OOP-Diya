package PR4.Minibank;
import java.util.regex.Pattern;
class Validator {
    private static final Pattern MOBILE =
            Pattern.compile("^[6-9][0-9]{9}$");
    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PAN =
            Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");
    private static final Pattern IFSC =
            Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");
    public static boolean isValidMobile(String mobile) {
        return MOBILE.matcher(mobile).matches();
    }
    public static boolean isValidEmail(String email) {
        return EMAIL.matcher(email).matches();
    }
    public static boolean isValidPan(String pan) {
        return PAN.matcher(pan).matches();
    }
    public static boolean isValidIfsc(String ifsc) {
        return IFSC.matcher(ifsc).matches();
    }
}
enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER
}
class Command {
    private TransactionType type;
    private String accountNumber;
    private long amount;
    public Command(TransactionType type, String accountNumber, long amount) {
        this.type = type;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
    public TransactionType type() {
        return type;
    }
    public String accountNumber() {
        return accountNumber;
    }
    public long amount() {
        return amount;
    }
}
class CommandParser {
    public static Command parse(String line) {
        String[] parts = line.split(" ");
        TransactionType type =
                TransactionType.valueOf(parts[0]);
        String accountNumber = parts[1];
        long amount = Long.parseLong(parts[2]);
        return new Command(type, accountNumber, amount);
    }
}
class StatementFormatter {
    public static String buildStatement(Account account) {
        StringBuilder sb = new StringBuilder();
        sb.append("Account Number: ");
        sb.append(account.getAccountNumber());
        sb.append("\n");
        sb.append("Owner Name: ");
        sb.append(account.getOwnerName());
        sb.append("\n");
        sb.append("Balance: ");
        sb.append(account.getBalance());
        return sb.toString();
    }
}
public class MiniBank4 {
    public static void main(String[] args) {
        System.out.println("Mobile:");
        System.out.println(Validator.isValidMobile("9876543210"));
        System.out.println(Validator.isValidMobile("12345"));

        System.out.println("\nEmail:");
        System.out.println(Validator.isValidEmail("rahul@gmail.com"));
        System.out.println(Validator.isValidEmail("rahul@gmail"));

        System.out.println("\nPAN:");
        System.out.println(Validator.isValidPan("ABCDE1234F"));
        System.out.println(Validator.isValidPan("ABC123"));

        System.out.println("\nIFSC:");
        System.out.println(Validator.isValidIfsc("SBIN0001234"));
        System.out.println(Validator.isValidIfsc("SBI123"));
        Command command =
                CommandParser.parse("DEPOSIT AC0001 500");

        System.out.println("\nCommand Details:");
        System.out.println("Type: " + command.type());
        System.out.println("Account Number: "
                + command.accountNumber());
        System.out.println("Amount: "
                + command.amount());
        Account account =
                new Account("Rahul", 5000);
        System.out.println("\nStatement:");
        System.out.println(
                StatementFormatter.buildStatement(account)
        );
    }
}
