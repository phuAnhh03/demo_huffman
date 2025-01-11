import java.util.*;

// Node class for the Huffman Tree
class Node implements Comparable<Node> {
    char symbol;
    int freq;
    Node left, right;

    // Constructor
    Node(char symbol, int freq, Node left, Node right) {
        this.symbol = symbol;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    // Comparison by frequency
    @Override
    public int compareTo(Node other) {
        return this.freq - other.freq;
    }
}

public class HuffmanCoding {

    // Function to build the Huffman Tree
    private static Node buildTree(Map<Character, Integer> freqMap) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue(), null, null));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            pq.add(new Node('\0', left.freq + right.freq, left, right));
        }

        return pq.poll();
    }

    // Function to build the encoding table
    private static void buildEncodingTable(Node node, String prefix, Map<Character, String> encodingTable) {
        if (node == null) return;

        // Leaf node
        if (node.left == null && node.right == null) {
            encodingTable.put(node.symbol, prefix);
            return;
        }

        // Recursively build the table
        buildEncodingTable(node.left, prefix + "1", encodingTable);
        buildEncodingTable(node.right, prefix + "0", encodingTable);
    }

    // Function to compress a string using the encoding table
    private static String compress(String input, Map<Character, String> encodingTable) {
        StringBuilder compressed = new StringBuilder();
        for (char c : input.toCharArray()) {
            compressed.append(encodingTable.get(c));
        }
        return compressed.toString();
    }

    // Main function for Huffman Coding
    public static void huffmanCoding(String input) {
        // Step 1: Build frequency map
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // Step 2: Build the Huffman Tree
        Node root = buildTree(freqMap);

        // Step 3: Build the encoding table
        Map<Character, String> encodingTable = new HashMap<>();
        buildEncodingTable(root, "", encodingTable);

        // Step 4: Compress the input
        String compressed = compress(input, encodingTable);

        // Step 5: Print results
        System.out.println("Encoding Table:");
        for (Map.Entry<Character, String> entry : encodingTable.entrySet()) {
            System.out.println("'" + entry.getKey() + "': " + entry.getValue());
        }
        System.out.println("\nOriginal Version: " + input);
        System.out.println("\nCompressed Version: " + compressed);
    }

    // Main driver
    public static void main(String[] args) {
        String input = "KHOAHOCTUNHIEN";
        huffmanCoding(input);
    }
}
