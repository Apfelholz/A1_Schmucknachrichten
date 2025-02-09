class HuffmanNodeNonBinary {
    // Character data
  char data;           
    
    // Frequency of the character
  int frequency;    
  
    // The Huffman Code
  String code;

    // Left and right child nodes
  HuffmanNodeNonBinary[] childNodes; 

  // Constructor to initialize the node
  HuffmanNodeNonBinary(char data, int frequency, int numberOfDifferentPearlTypes) {
      this.data = data;
      this.frequency = frequency;
      childNodes = new HuffmanNodeNonBinary[numberOfDifferentPearlTypes];
      code = "";
  }
}

