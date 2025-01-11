import java.util.*;

class Node implements Comparable<Node> {
    char symbol;
    int freq;
    Node left, right;

    Node(char symbol, int freq, Node left, Node right) {
        this.symbol = symbol;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(Node other) {
        return this.freq - other.freq;
    }
}

public class HuffmanCoding {

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

    private static void buildEncodingTable(Node node, String prefix, Map<Character, String> encodingTable) {
        if (node == null) return;

        if (node.left == null && node.right == null) {
            encodingTable.put(node.symbol, prefix);
            return;
        }

        buildEncodingTable(node.left, prefix + "1", encodingTable);
        buildEncodingTable(node.right, prefix + "0", encodingTable);
    }

    private static String compress(String input, Map<Character, String> encodingTable) {
        StringBuilder compressed = new StringBuilder();
        for (char c : input.toCharArray()) {
            compressed.append(encodingTable.get(c));
        }
        return compressed.toString();
    }

    public static void huffmanCoding(String input) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        Node root = buildTree(freqMap);

        Map<Character, String> encodingTable = new HashMap<>();
        buildEncodingTable(root, "", encodingTable);

        String compressed = compress(input, encodingTable);

        System.out.println("Encoding Table:");
        for (Map.Entry<Character, String> entry : encodingTable.entrySet()) {
            System.out.println("'" + entry.getKey() + "': " + entry.getValue());
        }
        System.out.println("\nOriginal Version: " + input);
        System.out.println("\nCompressed Version: " + compressed);
    }

    public static void main(String[] args) {
        String input = "KHOAHOCTUNHIEN";
        huffmanCoding(input);
    }
}
