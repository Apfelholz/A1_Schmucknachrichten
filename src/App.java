public class App {
    public static void main(String[] args) throws Exception {
        
        String dateipfad = "data\\schmuck1.txt";
        
        int numberOfDifferentPearlTypes = FileReaderx.readLineToInt(dateipfad, 0);

        int[] pearlTypes = FileReaderx.readToInt2DArray(dateipfad, 1, 1)[0];

        char[] message = FileReaderx.readToContinuousCharArray(dateipfad, 2, -1);

        System.out.println("suscess");
    }
}
