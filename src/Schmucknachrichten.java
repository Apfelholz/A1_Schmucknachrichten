import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Schmucknachrichten {
    public static void main(String[] args) throws Exception {
        
        String dateipfad = args[0];
        String method = args[1];
        
        int numberOfDifferentPearlTypes = FileReaderx.readLineToInt(dateipfad, 0);

        int[] pearlTypes = FileReaderx.readToInt2DArray(dateipfad, 1, 1)[0];
        HashMap<Integer, Integer> pearlTypeMap = new HashMap<Integer,Integer>();

        for(int i = 0; i < numberOfDifferentPearlTypes; i++) {
            pearlTypeMap.put(i,pearlTypes[i]);
        }

        char[] message = FileReaderx.readToContinuousCharArray(dateipfad, 2, -1);

        HashMap<Character,String> codeMap = new HashMap<>();

        if (method.equals("ILP")){
            codeMap = SchmucknachrichtenILP.findCodes(message, numberOfDifferentPearlTypes, pearlTypes);
        }else if(method.equals("Huffman")){
            codeMap = HuffmanKodierungNonBinary.huffmanKodierung(message, numberOfDifferentPearlTypes);
        }
        

        String messageCode = "";
        int messageLength = 0;
        for (char c : message){
            messageCode = messageCode + codeMap.get(c);
        }
        for (char m : messageCode.toCharArray()){
            messageLength += pearlTypeMap.get(Integer.parseInt(Character.toString(m)));
        }

        // Verify prefix-free property
        boolean isPrefixFree = verifyPrefixFree(codeMap);
        boolean isOfAppropriateLength = verifyMessageLength(dateipfad, messageLength);
        boolean ismessageEncodingIsAccurate = verifyMessageEncoding(message, messageCode,codeMap); 

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
        System.out.println("Length of the encoded message: " + messageLength + " mm");
        System.out.println("Is of appropriate Length: " + isOfAppropriateLength);
        System.out.println("Is prefix-free: " + isPrefixFree);
        System.out.println("Encoding is Accurate: " + ismessageEncodingIsAccurate);
    }

    private static boolean verifyPrefixFree(HashMap<Character, String> codeMap) {
        Set<String> codes = new HashSet<>(codeMap.values());
        for (String code : codes) {
            boolean f = false;
            for (String otherCode : codes) {
                if (code.equals(otherCode)){
                    if (f){
                        return false;
                    }
                    f = true;
                }else if (otherCode.startsWith(code)) {
                    return false;
                }
            }
        }
        
        return true;
        }

        private static boolean verifyMessageLength(String filePath, int messageLength) {
        boolean isOfAppropriateLength = true;

        String fileName = new File(filePath).getName();

        if (fileName.equals("schmuck0.txt") && messageLength != 113){
            isOfAppropriateLength = false;
        }else if (fileName.equals("schmuck5.txt") && messageLength >= 3162){
            isOfAppropriateLength = false;
        } else if (fileName.equals("schmuck9.txt") && messageLength >= 36597){
            isOfAppropriateLength = false;
        }

        return isOfAppropriateLength;
        }

        private static boolean verifyMessageEncoding(char[] message, String messageCode, HashMap<Character, String> codeMap){
        boolean messageEncodingIsAccurate = false;

        String decodeedMassage = "";
        String akCode = "";

        for(char c : messageCode.toCharArray()){
            akCode += c;
            if(codeMap.containsValue(akCode)){
                decodeedMassage = decodeedMassage + getKeyByValue(codeMap, akCode);
                akCode = "";
            }
        }

        if(decodeedMassage.equals(new String(message))){
            messageEncodingIsAccurate = true;
        }

        return messageEncodingIsAccurate;
    }

    private static Character getKeyByValue(HashMap<Character, String> map, String value) {
        for (HashMap.Entry<Character, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
} 

