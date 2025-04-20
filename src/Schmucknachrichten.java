import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Schmucknachrichten {
    public static void main(String[] args) throws Exception {
        // Input parameters
        String dateipfad = args[0];
        String method;
        if (args.length > 1){
            method = args[1];
        }else{
            method = "SIG";
        }

        // Read pearl types and their lengths
        int numberOfDifferentPearlTypes = FileReaderx.readLineToInt(dateipfad, 0);
        int[] pearlTypes = FileReaderx.readToInt2DArray(dateipfad, 1, 1)[0];
        HashMap<Integer, Integer> pearlTypeMap = new HashMap<>();

        for (int i = 0; i < numberOfDifferentPearlTypes; i++) {
            pearlTypeMap.put(i, pearlTypes[i]);
        }

        // Read message
        char[] message = FileReaderx.readToContinuousCharArray(dateipfad, 2, 2);

        // Read Length request
        int lengthRequest = 0;
        try {
            lengthRequest = FileReaderx.readLineToInt(dateipfad, 3);
        } catch (Exception e) {
            System.err.println("Error reading length request: " + e.getMessage());
        }
        

        // Create code map
        Map<Character, String> codeMap = new HashMap<>();
        if (method.equals("SIG")) {
            codeMap = UnequalCostPrefixFreeCode.findOptimalPrefixFreeCode(message, pearlTypes);
        } else if (method.equals("Huffman")) {
            codeMap = HuffmanKodierungNonBinary.huffmanKodierung(message, numberOfDifferentPearlTypes);
        } 

        // Encode message
        String messageCode = encodeMessage(message, codeMap);

        // Calculate the length of the encoded message
        int messageLength = calculateEncodedMessageLength(messageCode, pearlTypeMap);

        if (lengthRequest != 0){
            adjusMessageLength(lengthRequest, pearlTypeMap, codeMap, messageLength, message);
            messageCode = encodeMessage(message, codeMap);
            messageLength = calculateEncodedMessageLength(messageCode, pearlTypeMap);
        }

        // Verify properties of the encoding
        boolean isPrefixFree = verifyPrefixFree(codeMap);
        boolean isOfAppropriateLength = verifyMessageLength(dateipfad, messageLength);
        boolean isMessageEncodingAccurate = verifyMessageEncoding(message, messageCode, codeMap);

        // Shorten values and output
        String shortenedMessageCode = messageCode.length() > 40 ? messageCode.substring(0, 40) + "..." : messageCode;
        String shortenedMessage = new String(message).length() > 40 ? new String(message).substring(0, 40) + "..." : new String(message);
        String shortenedPearlTypeMap = pearlTypeMap.toString().length() > 100 ? pearlTypeMap.toString().substring(0, 100) + "..." : pearlTypeMap.toString();
        String codeMapRepresentation = codeMap.toString().length() > 100 ? codeMap.toString().substring(0, 100) + "..." : codeMap.toString();

        FileLogger fileLogger = new FileLogger("output\\output_" + method + "_" + dateipfad.substring(14, 16) + ".txt");

        System.out.println("====================================");
        System.out.println("Results for file: " + dateipfad);

        System.out.println("The message: ");
        System.out.println(new String(message));
        System.out.println("The massageCode: ");
        System.out.println(messageCode);
        System.out.println("The pearlTypeMap (PearlType : Length in mm): ");
        System.out.println(pearlTypeMap.toString());
        System.out.println("The CodeMap/'Codetabelle' (Character : PearlSequence): ");
        System.out.println(codeMap.toString());

        System.out.println("====================================");
        System.out.println("Results for file: " + dateipfad);
        System.out.println("====================================");

        System.out.println("First 40 characters of message: " + shortenedMessage);
        System.out.println("First 40 characters of messageCode: " + shortenedMessageCode);
        System.out.println("Shortened pearlTypeMap (PearlType : Length in mm): " + shortenedPearlTypeMap);
        System.out.println("Shortened CodeMap/'Codetabelle' (Character : PearlSequence): " + codeMapRepresentation);
        System.out.println("Length of the encoded message: " + messageLength + " mm");
        System.out.println("Is of appropriate Length: " + isOfAppropriateLength);
        System.out.println("Is prefix-free: " + isPrefixFree);
        System.out.println("Encoding is Accurate: " + isMessageEncodingAccurate);
        System.out.println("Note: The output is also written to a file where the encoding works better, including the Unicode codes in plain text as an extra precaution.");

        System.out.println("====================================");


        fileLogger.println("====================================");
        fileLogger.println("Results for file: " + dateipfad);
        fileLogger.println("====================================");

        fileLogger.println("First 40 characters of messageCode: " + shortenedMessageCode);
        fileLogger.println("First 40 characters of message: " + shortenedMessage);
        fileLogger.println("Shortened pearlTypeMap (PearlType : Length in mm): " + shortenedPearlTypeMap);
        fileLogger.println("Shortened CodeMap/'Codetabelle' (Character : PearlSequence): " + codeMapRepresentation);
        fileLogger.println("Length of the encoded message: " + messageLength + " mm");
        fileLogger.println("Is of appropriate Length: " + isOfAppropriateLength);
        fileLogger.println("Is prefix-free: " + isPrefixFree);
        fileLogger.println("Encoding is Accurate: " + isMessageEncodingAccurate);

        fileLogger.println("====================================");
        fileLogger.println("");

        fileLogger.println("The message: ");
        fileLogger.println(new String(message));
        fileLogger.println("");
        fileLogger.println("The massageCode: ");
        fileLogger.println(messageCode);
        fileLogger.println("");
        fileLogger.println("The pearlTypeMap (PearlType : Length in mm): ");
        fileLogger.println(pearlTypeMap.toString());
        fileLogger.println("");
        fileLogger.println("The CodeMap/'Codetabelle' (Character : PearlSequence): ");
        fileLogger.println(codeMap.toString());
        fileLogger.println("");

        fileLogger.println("====================================");
        fileLogger.println("");

        fileLogger.println("The message as Unicode codes");
        new String(message).codePoints().forEach(cp -> fileLogger.print(
            String.format("U+%04X ", cp)
        ));
        fileLogger.println("");
        fileLogger.println("The pearlTypeMap with Unicode codes: ");
        fileLogger.println(getUnicodeKeyMap(codeMap));


        fileLogger.close();
        
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");
        jsonBuilder.append("  \"pearl\": {\n");
        pearlTypeMap.forEach((key, value) -> 
            jsonBuilder.append("    \"").append(key).append("\": ").append(value).append(",\n")
        );
        if (!pearlTypeMap.isEmpty()) {
            jsonBuilder.setLength(jsonBuilder.length() - 2);
        }
        jsonBuilder.append("\n  },\n");
        jsonBuilder.append("  \"codes\": {\n");
        codeMap.forEach((key, value) -> 
            jsonBuilder.append("    \"")
               .append(key.toString().replace("\"", "\\\"").replace("’", "\\u2019").replace("，", "\\uFF0C")) // Escape problematic characters in key
               .append("\": \"")
               .append(value.replace("\"", "\\\"").replace("’", "\\u2019").replace("，", "\\uFF0C")) // Escape problematic characters in value
               .append("\",\n")
        );
        if (!codeMap.isEmpty()) {
            jsonBuilder.setLength(jsonBuilder.length() - 2);
        }
        jsonBuilder.append("\n  },\n");
        jsonBuilder.append("  \"output\": \"output\\\\tree_").append(method).append("_").append(dateipfad.substring(14, 16)).append("\"\n");
        jsonBuilder.append("}");

        java.nio.file.Files.write(
            java.nio.file.Paths.get("temp_config.json"), 
            jsonBuilder.toString().getBytes()
        );
    }

    public static String getUnicodeKeyMap(Map<Character, String> codeMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        boolean first = true;
        for (Map.Entry<Character, String> entry : codeMap.entrySet()) {
            if (!first) {
                sb.append(", ");
            } else {
                first = false;
            }

            char ch = entry.getKey();
            int codePoint = Character.codePointAt(new char[]{ch}, 0);
            String unicodeKey;

            if (Character.isSupplementaryCodePoint(codePoint)) {
                unicodeKey = String.format("U+%04X ", codePoint); // Für Supplementary Characters
            } else {
                unicodeKey = String.format("U+%04X ", codePoint); // Für BMP-Zeichen
            }

            sb.append(unicodeKey).append("=").append(entry.getValue());
        }

        sb.append("}");
        return sb.toString();
    }

    // Checks if the code map is prefix-free
    private static boolean verifyPrefixFree(Map<Character, String> codeMap) {
        Set<String> codes = new HashSet<>(codeMap.values());
        for (String code : codes) {
            boolean found = false;
            for (String otherCode : codes) {
                if (code.equals(otherCode)) {
                    if (found) {
                        return false;
                    }
                    found = true;
                } else if (otherCode.startsWith(code)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Checks if the length of the encoded message is correct
    private static boolean verifyMessageLength(String filePath, int messageLength) {
        String fileName = new File(filePath).getName();

        if (fileName.equals("schmuck0.txt") && messageLength != 113) {
            return false;
        } else if (fileName.equals("schmuck5.txt") && messageLength > 3162) {
            return false;
        } else if (fileName.equals("schmuck9.txt") && messageLength > 36597) {
            return false;
        }

        return true;
    }

    // Checks if the message was encoded correctly
    private static boolean verifyMessageEncoding(char[] message, String messageCode, Map<Character, String> codeMap) {
        StringBuilder decodedMessageBuilder = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        for (char c : messageCode.toCharArray()) {
            currentCode.append(c);
            if (codeMap.containsValue(currentCode.toString())) {
                decodedMessageBuilder.append(getKeyByValue(codeMap, currentCode.toString()));
                currentCode.setLength(0);
            }
        }

        return decodedMessageBuilder.toString().equals(new String(message));
    }

    // Find key by value in the map
    private static Character getKeyByValue(Map<Character, String> map, String value) {
        for (HashMap.Entry<Character, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static void adjusMessageLength(int lengthRequest, HashMap<Integer, Integer> pearlTypeMap, Map<Character, String> codeMap, int messageLength, char[] message) {
        if (lengthRequest - messageLength > 0){

            int smallestPerl = Integer.MAX_VALUE;
            for (int p : pearlTypeMap.keySet()){
                if (pearlTypeMap.get(p) < smallestPerl){
                    smallestPerl = pearlTypeMap.get(p);
                }
            }

            char[] alphabet = UnequalCostPrefixFreeCode.extractAlphabet(message);
            int[] frequencies = UnequalCostPrefixFreeCode.calculateFrequencies(message);
            char leastFrequentCaracter = alphabet[0]; 
            int frequencie_leastFrequentCaracter = Integer.MAX_VALUE;
            for (int i = 0; i < alphabet.length; i++){
                if (frequencies[i] < frequencie_leastFrequentCaracter){
                    frequencie_leastFrequentCaracter = frequencies[i];
                    leastFrequentCaracter = alphabet[i];
                }
            }

            int toAddPerls = (lengthRequest - messageLength) / (pearlTypeMap.get(smallestPerl) * frequencie_leastFrequentCaracter);

            String toAddCodePart = "";

            for (int i = 0; i < toAddPerls; i++){
                toAddCodePart = toAddCodePart + Integer.toString(smallestPerl);
            }

            codeMap.replace(leastFrequentCaracter, codeMap.get(leastFrequentCaracter) + toAddCodePart);
        }
    }

    public static String encodeMessage(char[] message, Map<Character, String> codeMap) {
        StringBuilder messageCodeBuilder = new StringBuilder();
        for (char c : message) {
            messageCodeBuilder.append(codeMap.get(c)); 
        }
        return messageCodeBuilder.toString();
    }

    public static int calculateEncodedMessageLength(String encodedMessage, Map<Integer, Integer> pearlTypeMap) {
        int messageLength = 0;
        for (char m : encodedMessage.toCharArray()) {
            int pearlType = Integer.parseInt(Character.toString(m));
            messageLength += pearlTypeMap.getOrDefault(pearlType, 0);
        }
        return messageLength;
    }
}

