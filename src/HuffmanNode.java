class HuffmanNode {
    // Character data
  char data;           
    
    // Frequency of the character
  int frequency;    
  
    // The Huffman Code
  String code;

    // Left and right child nodes
  HuffmanNode left, right; 

  // Constructor to initialize the node
  HuffmanNode(char data, int frequency) {
      this.data = data;
      this.frequency = frequency;
      left = right = null;
      code = "";
  }
}

