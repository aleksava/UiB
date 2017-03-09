import java.util.*;
import java.io.*;

abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public final int frequency;

    public HuffmanTree(int frequency) {
        this.frequency = frequency;
    }

    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}

class HuffmanLeaf extends HuffmanTree {
    public final int value;

    public HuffmanLeaf(int frequency, int num) {
        super(frequency);
        value = num;
    }
}

class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right;

    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}

class Huffman {

    static HashMap<Integer, String> codes = new HashMap<Integer, String>();

    // Builds a HuffmanTree based on the input frequencies
    public static HuffmanTree buildTree(int[] freqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();

        // Filling the PriorityQueue with leaves
        for(int i = 0; i < freqs.length; i++) {
            trees.offer(new HuffmanLeaf(freqs[i], i));
        }

        assert trees.size() > 0;

        // Gets and removes the two heads of the queue and gathers them to one
        // Node, as long as there is more than 1 element in the queue
        while(trees.size() > 1) {
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();

            trees.offer(new HuffmanNode(a, b));
        }

        // Sends of the final node as the root of a tree
        return trees.poll();
    }


    // Generates the binary Huffman code for a 
    public static String writeCode(HuffmanTree tree, String input) {
        StringBuilder sb = new StringBuilder();
        
        gatherCodes(tree, new StringBuffer());

        try {
            for(char c : input.toCharArray()) {
                if(c != ' ') {
                    sb.append(codes.get(Character.getNumericValue(c)));
                }
                
                else
                    sb.append(codes.get(10));
            }
        }
        catch(NumberFormatException e) {
            e.printStackTrace();
        }
        
        return sb.toString();
    }


    // Gathers the codes for each character by moving through the entire tree
    public static void gatherCodes(HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;

        // Checks if at a Leaf or a Node, and either moves on building the prefix
        // or puts the codes with the value in a HashMap for simplicity
        if(tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;
            codes.put(leaf.value, prefix.toString());
        }
        else if(tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;
            
            // Traverse left in the tree
            prefix.append('0');
            gatherCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);
            
            // Traverse right in the tree
            prefix.append('1');
            gatherCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }

    
    // Reads text from a file to a String
    public static String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        String line;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            
            while((line = br.readLine()) != null) {
                sb.append(line.toLowerCase());
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        return sb.toString();
    }
    

    // Writes a given String to a new file. 
    public static void writeFile(String str, String filename) {
        //System.out.println("The length of " + filename + " is: " + str.length());

        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");

            writer.println(str);

            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    
    // Decodes a String from Huffman binary to LZW dictionary indexes
    public static void decode(String str) {
        StringBuilder sb = new StringBuilder();
        char[] arr = str.toCharArray();
        StringBuilder prefix = new StringBuilder();
        
        for(int i = 0; i < arr.length; i ++ ){
            prefix.append(arr[i]);
            for(int j : codes.keySet()) {
                try {
                    if(codes.get(j).equals(prefix.toString())) {
                        if(j != 10) 
                            sb.append(j);
                        else 
                            sb.append(" ");
                        prefix = new StringBuilder();
                    }
                }
                catch(NullPointerException e) {
                    // let it run
                }
            }
        }

        writeFile(sb.toString(), "lzw.huff");
    }
    
    public static void main (String[] args) {
        String test = readFile("LZW.txt");

        int[] charFreqs = new int[11];
        
        for (char c : test.toCharArray()){
            if(c != ' ')
                charFreqs[Character.getNumericValue(c)]++;
            else
                charFreqs[10]++;
        }
        
        // build tree
        HuffmanTree tree = buildTree(charFreqs);
        
        // print out results

        System.out.println("Encoding...");
        String output = writeCode(tree, test);

        System.out.println("Writing file...");
        writeFile(output, "code.huff");

        System.out.println("Decoding...");
        decode(output);
        
        for(Integer i: codes.keySet()) {
            System.out.println(i + "\t" + codes.get(i));
        }
    }
}
