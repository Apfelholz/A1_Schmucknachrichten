import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SchmucknachrichtenTest {
    @Test
    void testMainWithSchmuck0() {
        testMainWithFile("./data/schmuck0.txt");
    }

    @Test
    void testMainWithSchmuck00() {
        testMainWithFile("./data/schmuck00.txt");
    }

    @Test
    void testMainWithSchmuck01() {
        testMainWithFile("./data/schmuck01.txt");
    }

    @Test
    void testMainWithSchmuck1() {
        testMainWithFile("./data/schmuck1.txt"); 
    }

    @Test
    void testMainWithSchmuck2() {
        testMainWithFile("./data/schmuck2.txt");
    }

    @Test
    void testMainWithSchmuck3() {
        testMainWithFile("./data/schmuck3.txt");
    }

    @Test
    void testMainWithSchmuck4() {
        testMainWithFile("./data/schmuck4.txt");
    }

    @Test
    void testMainWithSchmuck5() {
        testMainWithFile("./data/schmuck5.txt");
    }

    @Test
    void testMainWithSchmuck6() {
        testMainWithFile("./data/schmuck6.txt");
    }

    @Test
    void testMainWithSchmuck7() {
        testMainWithFile("./data/schmuck7.txt");
    }

    @Test
    void testMainWithSchmuck8() {
        testMainWithFile("./data/schmuck8.txt");
    }

    @Test
    void testMainWithSchmuck9() {
        testMainWithFile("./data/schmuck9.txt");
    }
    
    private void testMainWithFile(String filePath) {
        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Call the main method with the input file path
        try {
            Schmucknachrichten.main(new String[]{filePath});
            System.out.println("Test with file " + filePath + " ran successfully.");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        } finally {
            // Reset System.out
            System.setOut(originalOut);
        }

        // Verify the output
        String output = outContent.toString();
        assertFalse(output.isEmpty(), "Output should not be empty for file: " + filePath);
        String[] lines = output.split("\n");
        boolean found = false;
        for (String line : lines) {
            if (line.startsWith("Is of appropriate Length: true")) {
                found = true;
                break;
            }
        }
        
        // Use TestResultFormatter to print the file path and the captured output for meaningful test results
        TestResultFormatter.printTestResult(filePath, output);
        
        assertTrue(found, "Message is of appropriate Length.");
    }
}
