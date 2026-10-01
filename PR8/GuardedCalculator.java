package PR8;

import java.util.Scanner;

class MyDivideByZeroException extends Exception {
    public MyDivideByZeroException(String message) {
        super(message);
    }
}

public class GuardedCalculator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean success = false;

        while (!success) {
            try {
                System.out.print("Enter first number: ");
                double num1 = Double.parseDouble(sc.nextLine());

                System.out.print("Enter operator (+, -, *, /): ");
                char operator = sc.nextLine().charAt(0);

                System.out.print("Enter second number: ");
                double num2 = Double.parseDouble(sc.nextLine());

                double result;

                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;

                    case '-':
                        result = num1 - num2;
                        break;

                    case '*':
                        result = num1 * num2;
                        break;

                    case '/':
                        if (num2 == 0) {
                            throw new MyDivideByZeroException(
                                "Cannot divide by zero."
                            );
                        }
                        result = num1 / num2;
                        break;

                    default:
                        throw new IllegalArgumentException(
                            "Invalid operator."
                        );
                }

                System.out.println("Result = " + result);
                success = true;

            } catch (NumberFormatException e) {
                System.out.println("Invalid number input. Please enter numbers only.");

            } catch (MyDivideByZeroException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());

            } finally {
                System.out.println("Attempt logged.");
                System.out.println("--------------------");
            }
        }

        sc.close();
        System.out.println("Calculation successful.");
    }
}