package PR11;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class LogAnalyzer {

    public static void main(String[] args) {

        Path file1 = Paths.get("log1.txt");
        Path file2 = Paths.get("log2.txt");
        Path file3 = Paths.get("log3.txt");

        try {
            // Create sample files
            Files.write(file1, List.of(
                "INFO Server started",
                "ERROR Database connection failed",
                "INFO User logged in",
                "ERROR File not found"
            ));

            Files.write(file2, List.of(
                "INFO Application running",
                "WARNING Low memory",
                "ERROR Network failure",
                "INFO User logged out"
            ));

            Files.write(file3, List.of(
                "INFO Backup started",
                "INFO Backup completed",
                "ERROR Backup failed"
            ));

            Path[] files = {file1, file2, file3};

            String keyword = "ERROR";

            int totalLines = 0;
            int keywordLines = 0;

            for (Path file : files) {

                int fileLines = 0;
                int fileKeywordLines = 0;

                List<String> lines = Files.readAllLines(file);

                for (String line : lines) {
                    fileLines++;
                    totalLines++;

                    if (line.contains(keyword)) {
                        fileKeywordLines++;
                        keywordLines++;
                    }
                }

                long size = Files.size(file);

                System.out.println("File: " + file.getFileName());
                System.out.println("Size: " + size + " bytes");
                System.out.println("Lines: " + fileLines);
                System.out.println("Lines containing \"" +
                                   keyword + "\": " + fileKeywordLines);
                System.out.println();
            }

            System.out.println("===== Overall Result =====");
            System.out.println("Total lines: " + totalLines);
            System.out.println("Total lines containing \"" +
                               keyword + "\": " + keywordLines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}