import java.util.HashMap;

public class Schmucknarichten {
    public static void main(String[] args) throws Exception {
        
        String dateipfad = "data\\schmuck5.txt";
        
        int numberOfDifferentPearlTypes = FileReaderx.readLineToInt(dateipfad, 0);

        int[] pearlTypes = FileReaderx.readToInt2DArray(dateipfad, 1, 1)[0];

        char[] message = FileReaderx.readToContinuousCharArray(dateipfad, 2, -1);

        HuffmanKodierungNonBinary huffmanKodierungnNonBinary = new HuffmanKodierungNonBinary();
        HashMap<Character,String> codeMap = huffmanKodierungnNonBinary.huffmanKodierung(message, numberOfDifferentPearlTypes);

        for (Character key : codeMap.keySet()) {
            System.out.println(key + ": " + codeMap.get(key));
        }
    }
}
