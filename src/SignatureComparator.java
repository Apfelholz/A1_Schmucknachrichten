import java.util.Comparator;


public class SignatureComparator implements Comparator<SIG> {
    @Override
    public int compare(SIG a, SIG b) {
        int mA = a.getM();
        int[] lA = a.getLevels();
        int mB = b.getM();
        int[] lB = b.getLevels();

        int[] partialA = partialSums(mA, lA);
        int[] partialB = partialSums(mB, lB);

        // Vergleiche rückwärts: höchste Summe hat höchste Priorität
        for (int i = partialA.length - 1; i >= 0; i--) {
            if (partialA[i] < partialB[i]) return -1;
            if (partialA[i] > partialB[i]) return 1;
        }
        return 0; // exakt gleich
    }

    private int[] partialSums(int m, int[] levels) {
        int[] result = new int[levels.length + 1];
        int sum = m;
        result[0] = sum;
        for (int i = 0; i < levels.length; i++) {
            sum += levels[i];
            result[i + 1] = sum;
        }
        return result;
    }
}