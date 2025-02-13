import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SchmucknarichtenTest {
    @Test
    void testMainWithSchmuck0() {
        testMainWithFile("/d:/Documents/Programiren/Informatik Wettbewerb/BWINF 2024/Runde 2/A1_Schmucknachrichten/data/schmuck0.txt");
    }

    @Test
    void testMainWithSchmuck00() {
        testMainWithFile("/d:/Documents/Programiren/Informatik Wettbewerb/BWINF 2024/Runde 2/A1_Schmucknachrichten/data/schmuck00.txt");
    }

    @Test
    void testMainWithSchmuck01() {
        testMainWithFile("/d:/Documents/Programiren/Informatik Wettbewerb/BWINF 2024/Runde 2/A1_Schmucknachrichten/data/schmuck01.txt");
    }

    @Test
    void testMainWithSchmuck1() {
        testMainWithFile("/d:/Documents/Programiren/Informatik Wettbewerb/BWINF 2024/Runde 2/A1_Schmucknachrichten/data/schmuck1.txt");
    }

    @Test
    void testMainWithSchmuck2() {
        testMainWithFile("/d:/Documents/Programiren/Informatik Wettbewerb/BWINF 2024/Runde 2/A1_Schmucknachrichten/data/schmuck2.txt");
    }

    @Test
    void testMainWithSchmuck3() {
        testMainWithFile("/d:/Documents/Programiren/Informatik Wettbewerb/BWINF 2024/Runde 2/A1_Schmucknachrichten/data/schmuck3.txt");
    }

    @Test
    void testMainWithSchmuck4() {
        testMainWithFile("/d:/Documents/Programiren/Informatik Wettbewerb/BWINF 2024/Runde 2/A1_Schmucknachrichten/data/schmuck4.txt");
    }

    @Test
    void testMainWithSchmuck5() {
        testMainWithFile("/d:/Documents/Programiren/Informatik Wettbewerb/BWINF 2024/Runde 2/A1_Schmucknachrichten/data/schmuck5.txt");
    }

    @Test
    void testMainWithSchmuck6() {
        testMainWithFile("/d:/Documents/Programiren/Informatik Wettbewerb/BWINF 2024/Runde 2/A1_Schmucknachrichten/data/schmuck6.txt");
    }

    @Test
    void testMainWithSchmuck7() {
        testMainWithFile("/d:/Documents/Programiren/Informatik Wettbewerb/BWINF 2024/Runde 2/A1_Schmucknachrichten/data/schmuck7.txt");
    }

    @Test
    void testMainWithSchmuck8() {
        testMainWithFile("/d:/Documents/Programiren/Informatik Wettbewerb/BWINF 2024/Runde 2/A1_Schmucknachrichten/data/schmuck8.txt");
    }

    @Test
    void testMainWithSchmuck9() {
        testMainWithFile("/d:/Documents/Programiren/Informatik Wettbewerb/BWINF 2024/Runde 2/A1_Schmucknachrichten/data/schmuck9.txt");
    }
    
    private void testMainWithFile(String filePath) {
        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Call the main method with the input file path
        try {
            Schmucknarichten.main(new String[]{filePath});
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
        
        // Use TestResultFormatter to print the file path and the captured output for meaningful test results
        TestResultFormatter.printTestResult(filePath, output);
    }
}
