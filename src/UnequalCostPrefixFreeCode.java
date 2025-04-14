import java.util.*;

public class UnequalCostPrefixFreeCode {

    private static float[] getfrequency(char[] message) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : message) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        float[] frequencies = new float[frequencyMap.size()];
        int index = 0;
        int total = message.length;

        for (int count : frequencyMap.values()) {
            frequencies[index++] = (float) count / total;
        }

        return frequencies;
    }

    private static char[] getAlphabet(char[] message) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : message) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        char[] alphabet = new char[frequencyMap.size()];
        int index = 0;


        for (char c : frequencyMap.keySet()) {
            alphabet[index++] = c;
        }

        return alphabet;
    }

    private static int[] getdeph(int[] cost){
        int maxCost = Arrays.stream(cost).max().orElse(0);
        int[] deph = new int[maxCost];
        for (int i = 0; i < cost.length; i++) {
            deph[cost[i]-1]++;
        }
        return deph;
    }

    private static void sortByProbabilityDescending(float[] p, char[] symbols) {
        List<Map.Entry<Character, Float>> pairs = new ArrayList<>();
        for (int i = 0; i < p.length; i++) {
            pairs.add(new AbstractMap.SimpleEntry<>(symbols[i], p[i]));
        }
        pairs.sort((a, b) -> Float.compare(b.getValue(), a.getValue()));
    
        for (int i = 0; i < p.length; i++) {
            symbols[i] = pairs.get(i).getKey();
            p[i] = pairs.get(i).getValue();
        }
    }

    private static Map<Character, String> getCodeMap(SIG lastSIG, int[] letterCosts, char[] symbols) {
        Map<Character, String> codeMap = new HashMap<>();

        Queue<Character> sortedSymbols = new LinkedList<>();
        for (char symbol : symbols) {
            sortedSymbols.add(symbol);
        }

        LinkedList<SIG> path = new LinkedList<>();
        SIG current = lastSIG;
        while (current != null) {
            path.addFirst(current);
            current = current.dad;
        }
    
        int r = letterCosts.length;

        String[] alphabet = new String[r];
        for (int i = 0; i < r; i++) {
            alphabet[i] = Integer.toString(i);
        }
    
        class Node {
            String code;
            Node(String code) {
                this.code = code;
            }
        }
    
        Queue<Node> nodes = new LinkedList<>();

        for (int k = 0; k < r; k++) {
            Node child = new Node(alphabet[k]);
            nodes.add(child);
        }

        for (int i = 0; i < path.size()-1; i++) {
            int q = path.get(i+1).q;

            int lastl1 = path.get(i).getLevels()[0];

            for (int j = 0; j < q; j++) {
                Node parent = nodes.poll();
                for (int k = 0; k < r; k++) {
                    Node child = new Node(parent.code + alphabet[k]);
                    nodes.add(child);
                }
            }

            for (int j = q; j < lastl1; j++) {
                Node leaf = nodes.poll();
                codeMap.put(sortedSymbols.poll(), leaf.code);
            }
        }
    
        return codeMap;
    }
    

    public static Map<Character, String> findOptimalPrefixFreeCode(char[] message, int[] c) {

        float[] p = getfrequency(message);

        char[] symbols = getAlphabet(message);
        sortByProbabilityDescending(p, symbols);

        int n = p.length;

        int[] d = getdeph(c);
        
        Map<SIG, Integer> OPT = new HashMap<>();

        int m = 0;
        int[] levels = Arrays.copyOf(d, d.length);
        SIG akkSIG = new SIG(m, levels, null, 1);

        OPT.put(akkSIG, 0);

        PriorityQueue<SIG> queue = new PriorityQueue<>(new SignatureComparator());
        queue.add(akkSIG);

        SIG perfektSIG = null;

        while(true){
            akkSIG = queue.poll();

            int additonalCost = 0; 
            for (int t = akkSIG.getM(); t < n; t++) {
                additonalCost += p[t];
            }

            int newcost = OPT.getOrDefault(akkSIG, Integer.MAX_VALUE) + additonalCost;

            for (int q = 0; q <= akkSIG.getLevels()[0]; q++){

                int newM = akkSIG.getM() + akkSIG.getLevels()[0] - q;;

                int[] oldLevels = akkSIG.getLevels();
                int[] newLevels = new int[oldLevels.length];

                for (int i = 1; i < oldLevels.length; i++) {
                    newLevels[i - 1] = oldLevels[i];
                }
                newLevels[oldLevels.length - 1] = 0;

                for (int i = 0; i < d.length; i++) {
                    newLevels[i] += q * d[i];
                }

                SIG newSIG = new SIG(newM, newLevels, akkSIG, q);

                if (newcost < OPT.getOrDefault(newSIG, Integer.MAX_VALUE)){
                    OPT.put(newSIG, newcost);
                    queue.add(newSIG);
                }

                if (newSIG.getM() >= n) {
                    perfektSIG = newSIG;
                    break;
                }
            }
            
            if (perfektSIG != null){
                break;
            }
        }

        return getCodeMap(perfektSIG, c, symbols);
    }
}  