import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SchmucknachrichtenTest {

    // test with ILP

    @Test
    void testMainWithSchmuckILPA() {
        testMainWithFile("./data/schmuckA.txt", "ILP");
    }

    @Test
    void testMainWithSchmuckILP0() {
        testMainWithFile("./data/schmuck0.txt", "ILP");
    }

    @Test
    void testMainWithSchmuckILP00() {
        testMainWithFile("./data/schmuck00.txt", "ILP");
    }

    @Test
    void testMainWithSchmuckILP01() {
        testMainWithFile("./data/schmuck01.txt", "ILP");
    }

    @Test
    void testMainWithSchmuckILP1() {
        testMainWithFile("./data/schmuck1.txt", "ILP"); 
    }

    @Test
    void testMainWithSchmuckILP2() {
        testMainWithFile("./data/schmuck2.txt", "ILP");
    }

    @Test
    void testMainWithSchmuckILP3() {
        testMainWithFile("./data/schmuck3.txt", "ILP");
    }

    @Test
    void testMainWithSchmuckILP4() {
        testMainWithFile("./data/schmuck4.txt", "ILP");
    }

    @Test
    void testMainWithSchmuckILP5() {
        testMainWithFile("./data/schmuck5.txt", "ILP");
    }

    @Test
    void testMainWithSchmuckILP6() {
        testMainWithFile("./data/schmuck6.txt", "ILP");
    }

    @Test
    void testMainWithSchmuckILP7() {
        testMainWithFile("./data/schmuck7.txt", "ILP");
    }

    @Test
    void testMainWithSchmuckILP8() {
        testMainWithFile("./data/schmuck8.txt", "ILP");
    }

    @Test
    void testMainWithSchmuckILP9() {
        testMainWithFile("./data/schmuck9.txt", "ILP");
    }

    // test with Huffman

    @Test
    void testMainWithSchmuckHuffmanA() {
        testMainWithFile("./data/schmuckA.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffman0() {
        testMainWithFile("./data/schmuck0.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffman00() {
        testMainWithFile("./data/schmuck00.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffman01() {
        testMainWithFile("./data/schmuck01.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffman1() {
        testMainWithFile("./data/schmuck1.txt", "Huffman"); 
    }

    @Test
    void testMainWithSchmuckHuffman2() {
        testMainWithFile("./data/schmuck2.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffman3() {
        testMainWithFile("./data/schmuck3.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffman4() {
        testMainWithFile("./data/schmuck4.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffman5() {
        testMainWithFile("./data/schmuck5.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffman6() {
        testMainWithFile("./data/schmuck6.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffman7() {
        testMainWithFile("./data/schmuck7.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffman8() {
        testMainWithFile("./data/schmuck8.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffman9() {
        testMainWithFile("./data/schmuck9.txt", "Huffman");
    }
    
    private void testMainWithFile(String filePath, String method) {
        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Call the main method with the input file path
        try {
            Schmucknachrichten.main(new String[]{filePath, method});
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
