import java.util.*;

public class UnequalCostPrefixFreeCode {
    static Map<Character, String> encodeMap;

    private static int[] calculateFrequencies(char[] message) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : message) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        int[] frequencies = new int[frequencyMap.size()];
        int index = 0;
        int total = message.length;

        for (int count : frequencyMap.values()) {
            frequencies[index++] = (int) count;
        }

        return frequencies;
    }

    private static char[] extractAlphabet(char[] message) {
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : message) {
            uniqueChars.add(c);
        }

        char[] alphabet = new char[uniqueChars.size()];
        int index = 0;
        for (char c : uniqueChars) {
            alphabet[index++] = c;
        }

        return alphabet;
    }

    private static int[] calculateDepths(int[] costs) {
        int maxCost = Arrays.stream(costs).max().orElse(0);
        int[] depths = new int[maxCost];
        for (int cost : costs) {
            depths[cost - 1]++;
        }
        return depths;
    }

    private static void sortSymbolsByProbability(int[] probabilities, char[] symbols) {
        List<Map.Entry<Character, Integer>> pairs = new ArrayList<>();
        for (int i = 0; i < probabilities.length; i++) {
            pairs.add(new AbstractMap.SimpleEntry<>(symbols[i], probabilities[i]));
        }
        pairs.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        for (int i = 0; i < probabilities.length; i++) {
            symbols[i] = pairs.get(i).getKey();
            probabilities[i] = pairs.get(i).getValue();
        }
    }

    private static Map<Character, String> generateCodeMap(SIG lastSIG, int[] letterCosts, char[] symbols) {
        Map<Character, String> codeMap = new HashMap<>();
        
        Queue<Character> sortedSymbols = new LinkedList<>();
        for (char symbol : symbols) {
            sortedSymbols.add(symbol);
        }


        LinkedList<SIG> path = new LinkedList<SIG>();
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
            int depth;

            Node(String code, int depth) {
                this.code = code;
                this.depth = depth;
            }
        }

        Queue<Node> nodes = new PriorityQueue<>(Comparator.comparingInt(a -> a.depth));
        for (int k = 0; k < r; k++) {
            nodes.add(new Node(alphabet[k], letterCosts[k]));
        }

        for (int i = 0; i < path.size() - 1; i++) {
            int q = path.get(i + 1).q;
            int lastLevel = path.get(i).getLevels()[0];

            for (int j = 0; j < q; j++) {
                Node parent = nodes.poll();
                for (int k = 0; k < r; k++) {
                    nodes.add(new Node(parent.code + alphabet[k], parent.depth + letterCosts[k]));
                }
            }

            for (int j = q; j < lastLevel; j++) {
                Node leaf = nodes.poll();
                if (!sortedSymbols.isEmpty()) {
                    codeMap.put(sortedSymbols.poll(), leaf.code);
                }
            }
        }

        return codeMap;
    }

    public static Map<Character, String> findOptimalPrefixFreeCode(char[] message, int[] costs) {
        int[] probabilities = calculateFrequencies(message);
        char[] symbols = extractAlphabet(message);
        sortSymbolsByProbability(probabilities, symbols);

        int n = probabilities.length;
        int[] depths = calculateDepths(costs);

        Map<SIG, Integer> optimalCosts = new HashMap<>();
        SIG initialSIG = new SIG(0, Arrays.copyOf(depths, depths.length), null, 1, 0);
        adjustSIG(initialSIG, n);

        optimalCosts.put(initialSIG, (int) 0);
        PriorityQueue<SIG> queue = new PriorityQueue<>(new SignatureComparator());
        queue.add(initialSIG);

        List<SIG> perfectSIGs = new ArrayList<>();

        while (!queue.isEmpty()) {
            SIG currentSIG = queue.poll();
            int currentM = currentSIG.getM();
            int firstLevel = currentSIG.getLevels()[0];
            
            int cost = calculateCost(probabilities, currentM, firstLevel, n);
            int newCost = currentSIG.cost + cost;

            for (int q = 0; q <= firstLevel; q++) {

                if (calculateSum(currentM, currentSIG.getLevels()) == n && q > 0){
                    break;
                }

                int newM = currentSIG.getM() + firstLevel - q;
                int[] newLevels = Arrays.copyOfRange(currentSIG.getLevels(), 1, currentSIG.getLevels().length);
                newLevels = Arrays.copyOf(newLevels, newLevels.length + 1);
                newLevels[newLevels.length - 1] = 0;

                for (int i = 0; i < depths.length; i++) {
                    newLevels[i] += q * depths[i];
                }

                SIG newSIG = new SIG(newM, newLevels, currentSIG, q, newCost);
                while (adjustSIG(newSIG, n));

                if (newCost < optimalCosts.getOrDefault(newSIG, Integer.MAX_VALUE) && calculateSum(newSIG.getM(), newSIG.getLevels()) <= n && isValidExpansion(q, newSIG.getLevels(), currentSIG.getLevels())) {
                    optimalCosts.put(newSIG, newCost);
                    queue.add(newSIG);
                    currentSIG.addChild(newSIG);
                }

                if (calculateSum(newSIG.getM(), newSIG.getLevels()) == n && newSIG.getM() == n) {
                    perfectSIGs.add(newSIG);
                }
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (SIG sig : perfectSIGs) {
            int cost =  calculateMessageCost(message, costs, symbols, sig, minCost);
            if (cost < minCost){
                minCost = cost;
            }
        }

        return encodeMap;
    }

    private static int calculateSum(int m, int[] levels) {
        return m + Arrays.stream(levels).sum();
    }

    private static boolean isValidExpansion(int q, int[] newLevels, int[] oldLevels ) {
        int addedLeaves = Arrays.stream(newLevels).sum() - Arrays.stream(oldLevels, 1, oldLevels.length).sum();
        return q == 0 || (q * 2 <= addedLeaves);
    }

    private static boolean adjustSIG(SIG sig, int n) {
        int oldm = sig.getM();
        int m = oldm;
        int[] levels = sig.getLevels();

        if (m > n) {
            m = n;
        }

        int sum = m;
        for (int i = 0; i < levels.length; i++) {
            int allowed = n - sum;
            if (levels[i] > allowed) {
                levels[i] = allowed;
            }
            sum += levels[i];
        }

        sig.m = m;
        sig.levels = levels;
        if (m != oldm){
            return true;
        } else{
            return false;
        }
    }

    private static int calculateCost(int[] probabilities, int m, int firstLevel, int n) {
        int cost = 0;
        for (int t = m+1; t <= n; t++) {
            cost += probabilities[t-1];
        }
        return cost;
    }

    private static int calculateMessageCost(char[] message, int[] costs, char[] symbols, SIG sig, int minCost) {
        Map<Character, String> codeMap = generateCodeMap(sig, costs, symbols);
        Map<Integer, Integer> costMap = new HashMap<>();

        for (int i = 0; i < costs.length; i++) {
            costMap.put(i, costs[i]);
        }

        StringBuilder encodedMessage = new StringBuilder();
        for (char c : message) {
            encodedMessage.append(codeMap.get(c));
        }

        int totalCost = 0;
        for (char c : encodedMessage.toString().toCharArray()) {
            totalCost += costMap.get(Character.getNumericValue(c));
        }

        if (minCost > totalCost) {
            encodeMap = codeMap;
        }

        return totalCost;
    }
}