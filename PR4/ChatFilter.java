package PR4;

public class ChatFilter {

    // (c)-(d) Filters the log lines by keyword and builds the report
    public static String filterLog(String[] lines, String keyword) {
        int count = 0;
        StringBuilder report = new StringBuilder();
        String lowerKeyword = keyword.toLowerCase();

        for (String line : lines) {
            String[] parts = line.split(" ", 3);

            // skip malformed lines (fewer than 3 parts)
            if (parts.length < 3) {
                continue;
            }

            String time = parts[0];
            String user = parts[1];
            String message = parts[2];

            if (message.toLowerCase().contains(lowerKeyword)) {
                count++;
                report.append(time).append(" ").append(user)
                      .append(": ").append(message).append("\n");
            }
        }

        return "Matches: " + count + "\n" + report;
    }
}