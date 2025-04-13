import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanKodierungNonBinary{
    private static HashMap<Character,String> codeMap = new HashMap<>();

    private static PriorityQueue<HuffmanNodeNonBinary> initaiseQueueWithRelativefrequence(char[] message, int numberOfDifferentPearlTypes){
        PriorityQueue<HuffmanNodeNonBinary> X = new PriorityQueue<HuffmanNodeNonBinary>((a,b) -> a.frequency - b.frequency);

        HashMap<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : message){
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        for (char c : frequencyMap.keySet()) {
            X.add(new HuffmanNodeNonBinary(c, frequencyMap.get(c), numberOfDifferentPearlTypes));
        }

        return X;
    }

    public static HashMap<Character,String> huffmanKodierung(char[] message, int numberOfDifferentPearlTypes){
        PriorityQueue<HuffmanNodeNonBinary> X = initaiseQueueWithRelativefrequence(message, numberOfDifferentPearlTypes);

        while(X.size() > 1){
            HuffmanNodeNonBinary[] childNodes = new HuffmanNodeNonBinary[numberOfDifferentPearlTypes];
            int newFrequency = 0;
            for(int i = 0; i < numberOfDifferentPearlTypes; i++){
                if (X.isEmpty()){
                    break;
                }
                childNodes[i] = X.poll();
                newFrequency += childNodes[i].frequency;
            }
            HuffmanNodeNonBinary neu = new HuffmanNodeNonBinary('$',newFrequency, numberOfDifferentPearlTypes);
            neu.childNodes = childNodes;
            X.add(neu);
        }

        HuffmanNodeNonBinary root = X.poll();
        root.data = '*';
        getcodes(root);

        return codeMap;
    }

    private static void getcodes(HuffmanNodeNonBinary node){
        if(node.data != '$' && node.data != '*'){
            codeMap.put(node.data, node.code);
        } else if(node.data == '*'){
            int i = 0;
            for(HuffmanNodeNonBinary c : node.childNodes){
                if(c != null){
                    c.code = Integer.toString(i);
                    getcodes(c);
                }
                i++;
            }        
        } else if(node.data == '$'){
            int i = 0;
            for(HuffmanNodeNonBinary c : node.childNodes){
                if(c != null){
                    c.code = node.code + Integer.toString(i);
                    getcodes(c);
                }
                i++;
            }
        }
    }
}