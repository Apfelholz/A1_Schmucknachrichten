import java.util.HashMap;

public class App {
    public static void main(String[] args) throws Exception {
        
        String dateipfad = "data\\schmuck0.txt";
        
        int numberOfDifferentPearlTypes = FileReaderx.readLineToInt(dateipfad, 0);

        int[] pearlTypes = FileReaderx.readToInt2DArray(dateipfad, 1, 1)[0];

        char[] message = FileReaderx.readToContinuousCharArray(dateipfad, 2, -1);

        HuffmanKodierung huffmanKodierung = new HuffmanKodierung();
        HashMap<Character,String> codeMap = huffmanKodierung.huffmanKodierung(message);

        for (Character key : codeMap.keySet()) {
            System.out.println(key + ": " + codeMap.get(key));
        }
    }
}
