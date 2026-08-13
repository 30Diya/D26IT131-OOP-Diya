package PR4;

import java.util.Scanner;

public class Driver1 {
    public static void main(String[] args) {
        // (a) log lines, including one malformed line (fewer than 3 parts)
        String[] logLines = {
            "10:05 alice Hello there",
            "10:06 bob How are you",
            "10:07 carol"                  // malformed - only 2 parts
        };

        // (b) read keyword from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter keyword to search for: ");
        String keyword = scanner.nextLine().trim();

        // (c)-(e) filter and print the report
        String report = ChatFilter.filterLog(logLines, keyword);
        System.out.println(report);

        scanner.close();
    }
}
