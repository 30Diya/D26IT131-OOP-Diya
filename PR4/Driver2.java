package PR4;

import java.util.Scanner;

public class Driver2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // (a) read the template from the user
        System.out.print("Enter template (use {placeholder} for fields): ");
        String template = scanner.nextLine();

        // read how many name/value pairs will be supplied
        System.out.print("How many values will you supply? ");
        int count = Integer.parseInt(scanner.nextLine().trim());

        String[] names = new String[count];
        String[] values = new String[count];

        for (int i = 0; i < count; i++) {
            System.out.print("Enter placeholder name #" + (i + 1) + " (without braces): ");
            names[i] = scanner.nextLine().trim();

            System.out.print("Enter value for {" + names[i] + "}: ");
            values[i] = scanner.nextLine().trim();
        }

        // (d) print the filled template
        String filled = TemplateFiller.fillTemplate(template, names, values);
        System.out.println();
        System.out.println("Filled template:");
        System.out.println(filled);

        scanner.close();
    }
}