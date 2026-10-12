import java.util.Scanner;
public class MiniBank1 {
    record BankInfo(String name, String branch) {}
    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        EXIT
}
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankInfo bank = new BankInfo("MiniBank", "Main Branch");
        System.out.println("================================");
        System.out.println("        " + bank.name());
        System.out.println("        " + bank.branch());
        System.out.println("================================");
        boolean running = true;
        while (running) {
            System.out.println("\n--- MiniBank Menu ---");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            MenuOption option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.EXIT;
                default -> null;
            };
            if (option == null) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }
            String message = switch (option) {
                case OPEN_ACCOUNT ->
                        "Open Account — to be implemented in a later lab.";
                case DEPOSIT ->
                        "Deposit — to be implemented in a later lab.";
                case WITHDRAW ->
                        "Withdraw — to be implemented in a later lab.";
                case TRANSFER ->
                        "Transfer — to be implemented in a later lab.";
                case EXIT -> {
                    running = false;
                    yield "Thank you for using MiniBank.";
                }
            };
            System.out.println(message);
        }
        scanner.close();
        System.out.println("Goodbye!");
    }
}
