import java.io.*;
import java.util.*;

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

        for(int i = 0; i < freqs.length; i++) {
            trees.offer(new HuffmanLeaf(freqs[i], i));
        }

        // VELDIG SKEPTISK TIL DETTE...................................................
        assert trees.size() > 0;

        while(trees.size() > 1) {
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();

            trees.offer(new HuffmanNode(a, b));
        }

        return trees.poll();
    }



    public static String writeCode(HuffmanTree tree, String input) {
        StringBuilder sb = new StringBuilder();
        
        dfs(tree, new StringBuffer());

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

    public static void dfs(HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;
        
        if(tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;
            codes.put(leaf.value, prefix.toString());
            // System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);
        }
        else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;
            
            // traverse left
            prefix.append('0');
            dfs(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);
            
            // traverse right
            prefix.append('1');
            dfs(node.right, prefix);
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

    public static void writeFile(String str, String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");

            writer.println(str);

            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void decode(String str) {
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        char[] arr = str.toCharArray();
        StringBuilder prefix = new StringBuilder();
        
        for(int i = 0; i < arr.length; i ++ ){
            prefix.append(arr[i]);
            // System.out.println("Prefix: " + prefix.toString());
            for(int j : codes.keySet()) {
                // System.out.println(codes.get(j));
                try {
                    if(codes.get(j).equals(prefix.toString())) {
                        if(j != 10)
                            sb.append(j);
                        else
                            sb.append(" ");
                        //System.out.println("Found one!");
                        prefix = new StringBuilder();
                        //System.out.println("Prefix reset!");
                    }
                }
                catch(NullPointerException e) {
                    // let it run
                }
            }
        }

        System.out.println("Writing decoded file...");
        writeFile(sb.toString(), "unhuffed.txt");
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
        writeFile(output, "Huffed.txt");

        System.out.println("Decoding...");
        decode(output);
        
        for(Integer i: codes.keySet()) {
            System.out.println(i + "\t" + codes.get(i));
        }
    }
}
