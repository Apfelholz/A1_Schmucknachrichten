import java.util.HashMap;

public class Schmucknarichten {
    public static void main(String[] args) throws Exception {
        
        String dateipfad = args[0];
        
        int numberOfDifferentPearlTypes = FileReaderx.readLineToInt(dateipfad, 0);

        int[] pearlTypes = FileReaderx.readToInt2DArray(dateipfad, 1, 1)[0];
        HashMap<Integer, Integer> pearlTypeMap = new HashMap<Integer,Integer>();

        for(int i = 0; i < numberOfDifferentPearlTypes; i++) {
            pearlTypeMap.put(i,pearlTypes[i]);
        }

        char[] message = FileReaderx.readToContinuousCharArray(dateipfad, 2, -1);

        HuffmanKodierungNonBinary huffmanKodierungnNonBinary = new HuffmanKodierungNonBinary();
        HashMap<Character,String> codeMap = huffmanKodierungnNonBinary.huffmanKodierung(message, numberOfDifferentPearlTypes);

        String messageCode = "";
        int messageLength = 0;
        for (char c : message){
            messageCode = messageCode + codeMap.get(c);
        }
        for (char m : messageCode.toCharArray()){
            messageLength += pearlTypeMap.get(Integer.parseInt(Character.toString(m)));
        }

        // Capture the required values
        String shortenedMessageCode = messageCode.length() > 40 ? messageCode.substring(0, 40) + "..." : messageCode;
        String shortenedMessage = new String(message).length() > 40 ? new String(message).substring(0, 40) + "..." : new String(message);
        String shortenedPearlTypeMap = pearlTypeMap.toString().length() > 100 ? pearlTypeMap.toString().substring(0, 100) + "..." : pearlTypeMap.toString();
        String codeMapRepresentation = codeMap.toString().length() > 100 ? codeMap.toString().substring(0, 100) + "..." : codeMap.toString();

        // Print the captured values
        System.out.println("First 40 characters of messageCode: " + shortenedMessageCode);
        System.out.println("First 40 characters of message: " + shortenedMessage);
        System.out.println("Shortened pearlTypeMap: " + shortenedPearlTypeMap);
        System.out.println("CodeMap: " + codeMapRepresentation);
        System.out.println("Length of the encoded message: " + messageLength);
    }
}
