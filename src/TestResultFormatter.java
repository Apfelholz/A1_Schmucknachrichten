import java.io.File;

public class TestResultFormatter {
    public static void printTestResult(String filePath, String output) {
        String fileName = new File(filePath).getName();
        
        System.out.println("====================================");
        System.out.println("Test results for file: " + fileName);
        System.out.println("====================================");

        // Extract and print the required values from the output
        String[] lines = output.split("\n");
        for (String line : lines) {
            if (line.startsWith("First 40 characters of messageCode:") ||
                line.startsWith("First 40 characters of message:") ||
                line.startsWith("Shortened pearlTypeMap:") ||
                line.startsWith("CodeMap:") ||
                line.startsWith("Length of the encoded message:") ||
                line.startsWith("Is of appropiate Length: ") ||
                line.startsWith("Is prefix-free:")) {
                System.out.println(line);
            }
        }

        System.out.println("====================================");
    }
}
