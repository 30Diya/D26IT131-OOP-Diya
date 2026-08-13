package PR4;

import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        System.out.println("\nPassword Analysis");
        System.out.println("-----------------");
        System.out.println("Length >= 8: "+ PasswordChecker.hasMinLength(password));
        System.out.println("Contains Uppercase: "+ PasswordChecker.hasUpperCase(password));
        System.out.println("Contains Digit: "+ PasswordChecker.hasDigit(password));
        System.out.println("Contains Special Character: "+ PasswordChecker.hasSpecialCharacter(password));
        System.out.println("\nPassword Strength: "+ PasswordChecker.strength(password));

        sc.close();
    }
}