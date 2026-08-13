package PR3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Fraction> fractions = new ArrayList<>();

        System.out.println("Enter fractions as: numerator denominator");
        System.out.println("Type 'exit' to stop entering and see the results.");

        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("quit")) {
                break;
            }
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Please enter two integers, e.g. 1 2");
                continue;
            }

            try {
                int num = Integer.parseInt(parts[0]);
                int den = Integer.parseInt(parts[1]);

                if (den == 0) {
                    System.out.println("Denominator cannot be 0. Try again.");
                    continue;
                }

                Fraction f = new Fraction(num, den);
                fractions.add(f);
                System.out.println("Added: " + f);
            } catch (NumberFormatException e) {
                System.out.println("Please enter two valid integers, e.g. 1 2");
            }
        }

        System.out.println();
        System.out.println("You entered " + fractions.size() + " fraction(s):");
        for (Fraction f : fractions) {
            System.out.println(f);
        }

        // Compare every pair and report which ones are equal
        System.out.println();
        System.out.println("Equality check:");
        for (int i = 0; i < fractions.size(); i++) {
            for (int j = i + 1; j < fractions.size(); j++) {
                Fraction a = fractions.get(i);
                Fraction b = fractions.get(j);
                System.out.println(a + " equals " + b + " -> " + a.equals(b));
            }
        }

        scanner.close();
    }
}