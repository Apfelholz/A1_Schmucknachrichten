import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SchmucknachrichtenTest {

    // test with SIG

    @Test
    void testMainWithSchmuckSIGE() {
        testMainWithFile("./data/schmuckE.txt", "SIG");
    }


    @Test
    void testMainWithSchmuckSIGD() {
        testMainWithFile("./data/schmuckD.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIGC() {
        testMainWithFile("./data/schmuckC.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIGB() {
        testMainWithFile("./data/schmuckB.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIGA() {
        testMainWithFile("./data/schmuckA.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIG0() {
        testMainWithFile("./data/schmuck0.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIG00() {
        testMainWithFile("./data/schmuck00.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIG01() {
        testMainWithFile("./data/schmuck01.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIG1() {
        testMainWithFile("./data/schmuck1.txt", "SIG"); 
    }

    @Test
    void testMainWithSchmuckSIG2() {
        testMainWithFile("./data/schmuck2.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIG3() {
        testMainWithFile("./data/schmuck3.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIG4() {
        testMainWithFile("./data/schmuck4.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIG5() {
        testMainWithFile("./data/schmuck5.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIG6() {
        testMainWithFile("./data/schmuck6.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIG7() {
        testMainWithFile("./data/schmuck7.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIG8() {
        testMainWithFile("./data/schmuck8.txt", "SIG");
    }

    @Test
    void testMainWithSchmuckSIG9() {
        testMainWithFile("./data/schmuck9.txt", "SIG");
    }

    // test with Huffman

    @Test
    void testMainWithSchmuckHuffmanE() {
        testMainWithFile("./data/schmuckE.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffmanD() {
        testMainWithFile("./data/schmuckD.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffmanC() {
        testMainWithFile("./data/schmuckC.txt", "Huffman");
    }

    @Test
    void testMainWithSchmuckHuffmanB() {
        testMainWithFile("./data/schmuckB.txt", "Huffman");
    }

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

        // Call the main method of your Java program
        try {
            Schmucknachrichten.main(new String[]{filePath, method});
            System.out.println("Test with file " + filePath + " ran successfully.");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        } finally {
            System.setOut(originalOut); // Reset System.out
        }

        // Capture and split output
        String output = outContent.toString();
        assertFalse(output.isEmpty(), "Output should not be empty for file: " + filePath);
        String[] lines = output.split("\n");

        // Prepare Python call
        List<String> command = new ArrayList<>();
        command.add("python");
        command.add("D:\\Documents\\Programiren\\Informatik Wettbewerb\\BWINF 2024\\Runde 2\\code_visualization\\src\\code_visualition.py");
        command.add("--config");
        command.add("temp_config.json");

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.inheritIO(); // optional: show Python output/errors in console
            Process process = pb.start();
            int exitCode = process.waitFor();
            assertEquals(0, exitCode, "Python visualization script should run without errors.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to run Python script: " + e.getMessage());
        }

        // Check for success marker in the output
        boolean found = Arrays.stream(lines)
            .anyMatch(line -> line.startsWith("Is of appropriate Length: true"));

        for (String line : lines) {
            System.out.println(line);
        }
        assertTrue(found, "Message is of appropriate Length.");
    }


}
