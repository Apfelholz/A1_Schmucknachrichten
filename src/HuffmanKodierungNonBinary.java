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
            X.add(new HuffmanNodeNonBinary(c, frequencyMap.get(c), numberOfDifferentPearlTypes, 0));
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
            HuffmanNodeNonBinary neu = new HuffmanNodeNonBinary( '\u0000' ,newFrequency, numberOfDifferentPearlTypes, 1);
            neu.childNodes = childNodes;
            X.add(neu);
        }

        HuffmanNodeNonBinary root = X.poll();
        root.position = 2;
        getcodes(root);

        return codeMap;
    }

    private static void getcodes(HuffmanNodeNonBinary node){
        if(node.position == 0){
            codeMap.put(node.data, node.code);
        } else if(node.position == 2){
            int i = 0;
            for(HuffmanNodeNonBinary c : node.childNodes){
                if(c != null){
                    c.code = Integer.toString(i);
                    getcodes(c);
                }
                i++;
            }        
        } else if(node.position == 1){
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