import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Schmucknachrichten {
    public static void main(String[] args) throws Exception {
        // Input parameters
        String dateipfad = args[0];
        String method = args[1];

        // Read pearl types and their lengths
        int numberOfDifferentPearlTypes = FileReaderx.readLineToInt(dateipfad, 0);
        int[] pearlTypes = FileReaderx.readToInt2DArray(dateipfad, 1, 1)[0];
        HashMap<Integer, Integer> pearlTypeMap = new HashMap<>();

        for (int i = 0; i < numberOfDifferentPearlTypes; i++) {
            pearlTypeMap.put(i, pearlTypes[i]);
        }

        // Read message
        char[] message = FileReaderx.readToContinuousCharArray(dateipfad, 2, -1);

        // Create code map
        Map<Character, String> codeMap = new HashMap<>();
        if (method.equals("SIG")) {
            codeMap = UnequalCostPrefixFreeCode.findOptimalPrefixFreeCode(message, pearlTypes);
        } else if (method.equals("Huffman")) {
            codeMap = HuffmanKodierungNonBinary.huffmanKodierung(message, numberOfDifferentPearlTypes);
        } 

        // Encode message
        StringBuilder messageCodeBuilder = new StringBuilder();
        for (char c : message) {
            messageCodeBuilder.append(codeMap.get(c));
        }
        String messageCode = messageCodeBuilder.toString();

        // Calculate the length of the encoded message
        int messageLength = 0;
        for (char m : messageCode.toCharArray()) {
            messageLength += pearlTypeMap.get(Integer.parseInt(Character.toString(m)));
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

        System.out.println("First 40 characters of messageCode: " + shortenedMessageCode);
        System.out.println("First 40 characters of message: " + shortenedMessage);
        System.out.println("Shortened pearlTypeMap: " + shortenedPearlTypeMap);
        System.out.println("CodeMap: " + codeMapRepresentation);
        System.out.println("Length of the encoded message: " + messageLength + " mm");
        System.out.println("Is of appropriate Length: " + isOfAppropriateLength);
        System.out.println("Is prefix-free: " + isPrefixFree);
        System.out.println("Encoding is Accurate: " + isMessageEncodingAccurate);
        // Create JSON representation
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");
        jsonBuilder.append("  \"pearl\": {\n");
        pearlTypeMap.forEach((key, value) -> 
            jsonBuilder.append("    \"").append(key).append("\": ").append(value).append(",\n")
        );
        if (!pearlTypeMap.isEmpty()) {
            jsonBuilder.setLength(jsonBuilder.length() - 2); // Remove trailing comma
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
            jsonBuilder.setLength(jsonBuilder.length() - 2); // Remove trailing comma
        }
        jsonBuilder.append("\n  },\n");
        jsonBuilder.append("  \"output\": \"output\\\\tree_").append(method).append("_").append(dateipfad.substring(14, 16)).append("\"\n");
        jsonBuilder.append("}");

        // Write JSON to file
        java.nio.file.Files.write(
            java.nio.file.Paths.get("temp_config.json"), 
            jsonBuilder.toString().getBytes()
        );
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
        } else if (fileName.equals("schmuck5.txt") && messageLength >= 3162) {
            return false;
        } else if (fileName.equals("schmuck9.txt") && messageLength >= 36597) {
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
}

