import java.util.HashMap;

public class Schmucknarichten {
    public static void main(String[] args) throws Exception {
        
        String dateipfad = "data\\schmuck0.txt";
        
        int numberOfDifferentPearlTypes = FileReaderx.readLineToInt(dateipfad, 0);

        int[] pearlTypes = FileReaderx.readToInt2DArray(dateipfad, 1, 1)[0];
        HashMap<Integer, Integer> pearlTypeMap = new HashMap<Integer,Integer>();

        for(int i = 0; i < numberOfDifferentPearlTypes; i++) {
            pearlTypeMap.put(i,pearlTypes[i]);
        }

        char[] message = FileReaderx.readToContinuousCharArray(dateipfad, 2, -1);

        HuffmanKodierungNonBinary huffmanKodierungnNonBinary = new HuffmanKodierungNonBinary();
        HashMap<Character,String> codeMap = huffmanKodierungnNonBinary.huffmanKodierung(message, numberOfDifferentPearlTypes);

        for (Character key : codeMap.keySet()) {
            System.out.println(key + ": " + codeMap.get(key));
        }

        String messageCode = "";
        int messageLegth = 0;
        for (char c : message){
            messageCode = messageCode + codeMap.get(c);
        }
        for (char m : messageCode.toCharArray()){
            messageLegth += pearlTypeMap.get(Integer.parseInt(Character.toString(m)));
        }
        System.out.println("The encoded message: " + messageCode);
        System.out.println("The length of the encoded message: " + messageLegth);
    }
}
