import java.util.*;

public class UnequalCostPrefixFreeCode {

    public static int[] calculateFrequencies(char[] message) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : message) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        int[] frequencies = new int[frequencyMap.size()];
        int index = 0;

        for (int count : frequencyMap.values()) {
            frequencies[index++] = (int) count;
        }

        return frequencies;
    }

    public static char[] extractAlphabet(char[] message) {
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

    private static void sortSymbolsByFrequence(int[] probabilities, char[] symbols) {
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
        int[] frequence = calculateFrequencies(message);
        char[] symbols = extractAlphabet(message);
        sortSymbolsByFrequence(frequence, symbols);

        int n = frequence.length;
        int[] depths = calculateDepths(costs);

        Map<SIG, Integer> optimalCosts = new HashMap<>();
        PriorityQueue<SIG> queue = new PriorityQueue<>(new SignatureComparator());

        SIG initialSIG = new SIG(0, Arrays.copyOf(depths, depths.length), null, 1, 0);
        reduce(initialSIG, n, 0);

        optimalCosts.put(initialSIG, (int) 0);
        queue.add(initialSIG);

        SIG perfectSIG = null;

        while (!queue.isEmpty()) {
            SIG currentSIG = queue.poll();
            int currentM = currentSIG.getM();
            int firstLevel = currentSIG.getLevels()[0];
            
            int cost = calculateCost(frequence, currentM, firstLevel, n);
            int newCost = currentSIG.cost + cost;

            for (int q = 0; q <= firstLevel; q++) {

                int newM = currentSIG.getM() + firstLevel - q;
                int[] newLevels = Arrays.copyOfRange(currentSIG.getLevels(), 1, currentSIG.getLevels().length);
                newLevels = Arrays.copyOf(newLevels, newLevels.length + 1);
                newLevels[newLevels.length - 1] = 0;

                for (int i = 0; i < depths.length; i++) {
                    newLevels[i] += q * depths[i];
                }

                SIG newSIG = new SIG(newM, newLevels, currentSIG, q, newCost);
                int killtLeafs = 0;
                while (true){
                    int last = killtLeafs;
                    killtLeafs = reduce(newSIG, n, killtLeafs);
                    if (killtLeafs == last){
                        break;
                    }
                };

                if (newCost < optimalCosts.getOrDefault(newSIG, Integer.MAX_VALUE) && calculateSum(newSIG.getM(), newSIG.getLevels()) <= n && isValidExpansion(q, newSIG.getLevels(), currentSIG.getLevels(), killtLeafs)) {
                    optimalCosts.put(newSIG, newCost);
                    queue.add(newSIG);
                    if (calculateSum(newSIG.getM(), newSIG.getLevels()) == n && newSIG.getM() == n) {
                        perfectSIG = newSIG;
                        break;
                    }
                }
            }
        }

        return generateCodeMap(perfectSIG, costs, symbols);
    }

    private static int calculateSum(int m, int[] levels) {
        return m + Arrays.stream(levels).sum();
    }

    private static boolean isValidExpansion(int q, int[] newLevels, int[] oldLevels, int killtLeafs) {
        int addedLeaves = Arrays.stream(newLevels).sum() - (Arrays.stream(oldLevels, 1, oldLevels.length).sum() - killtLeafs);
        return q == 0 || (q * 2 <= addedLeaves);
    }

    private static int reduce(SIG sig, int n, int killtLeafs) {
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
                killtLeafs += levels[i]-allowed;
                levels[i] = allowed;
            }
            sum += levels[i];
        }

        sig.m = m;
        sig.levels = levels;
        return killtLeafs;
    }

    private static int calculateCost(int[] probabilities, int m, int firstLevel, int n) {
        int cost = 0;
        for (int t = m+1; t <= n; t++) {
            cost += probabilities[t-1];
        }
        return cost;
    }
}