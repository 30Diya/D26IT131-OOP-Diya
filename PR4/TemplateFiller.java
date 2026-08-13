package PR4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateFiller {

    // (b)-(c) Compiles the pattern, walks the matches, and builds the filled template
    public static String fillTemplate(String template, String[] names, String[] values) {
        Pattern pattern = Pattern.compile("\\{(\\w+)\\}");
        Matcher matcher = pattern.matcher(template);

        StringBuilder result = new StringBuilder();
        int lastEnd = 0;

        while (matcher.find()) {
            String placeholder = matcher.group(1);

            // append the literal text before this placeholder
            result.append(template, lastEnd, matcher.start());

            String value = lookup(placeholder, names, values);
            result.append(value);

            lastEnd = matcher.end();
        }

        // append any remaining literal text after the last placeholder
        result.append(template.substring(lastEnd));

        return result.toString();
    }

    // looks up a placeholder's value in the parallel arrays, or "[?]" if not found
    private static String lookup(String placeholder, String[] names, String[] values) {
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(placeholder)) {
                return values[i];
            }
        }
        return "[?]";
    }
}