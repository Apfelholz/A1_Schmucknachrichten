import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanKodierung{
    private HashMap<Character,String> codeMap = new HashMap<>();

    private PriorityQueue<HuffmanNode> initaiseQueueWithRelativefrequence(char[] message){
        PriorityQueue<HuffmanNode> X = new PriorityQueue<HuffmanNode>((a,b) -> a.frequency - b.frequency);

        HashMap<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : message){
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        for (char c : frequencyMap.keySet()) {
            X.add(new HuffmanNode(c, frequencyMap.get(c)));
        }

        return X;
    }

    public HashMap<Character,String> huffmanKodierung(char[] message){
        PriorityQueue<HuffmanNode> X = this.initaiseQueueWithRelativefrequence(message);

        while(X.size() > 1){
            HuffmanNode first = X.poll();
            HuffmanNode second = X.poll();
            HuffmanNode neu = new HuffmanNode('$',first.frequency + second.frequency);
            neu.left = first;
            neu.right = second;
            X.add(neu);
        }

        HuffmanNode root = X.poll();
        root.data = '*';
        this.getcodes(root);

        return codeMap;
    }

    private void getcodes(HuffmanNode node){
        if(node.data != '$' && node.data != '*'){
            codeMap.put(node.data, node.code);
        } else if(node.data == '*'){
            if(node.left != null){
                node.left.code = "0";
                this.getcodes(node.left);
            }
            if(node.right != null){
                node.right.code = "1";
                this.getcodes(node.right);
            }
        } else if(node.data == '$'){
            node.left.code = node.code + "0";
            node.right.code = node.code + "1";
            this.getcodes(node.left);
            this.getcodes(node.right);
        }
    }
}