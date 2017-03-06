import java.util.*;
import java.io.*;

class LZWHuffComp {

    private static final int set = 30; // 29 norwegian letters + <spacae>
    private static HuffmanTree tree;
    private static Huffman huff = new Huffman();
    
    public static void main (String[] args) {
        int filesize = (int)(new File("Folktale.txt").length()/1024);
        int compressedSize = 0;
        File compressed;
        

        System.out.println("File size pre-compression: " + filesize + " kB");

        compress(readFile("Folktale.txt").toLowerCase());

        

        compressed = new File("LZW.txt");
        compressedSize = (int)(compressed.length()/1024);

        System.out.println("File size post-compression: " + compressedSize + " kB");

        decompress(compressed);
    }

    // Compresses files with norwegian text
    public static void compress(String input) {
        HashMap<String, Integer> dictionary = buildCompressionDictionary();
        ArrayList<Integer> compressed = new ArrayList<Integer>();
        int dictLength = 30;
        String a = "";

        // Build the compressed output, and continue on the dictionary
        for(char c: input.toCharArray()) {
            String ac = a + c;
            if(dictionary.containsKey(ac))
                a = ac;
            else {
                compressed.add(dictionary.get(a));
                dictionary.put(ac, dictLength++);
                a = "" + c;
            }
        }

        if(!a.equals("")) {
            compressed.add(dictionary.get(a));
        }     

        writeFile(compressed, "LZW.txt"); // Need to write huffman compression on top of this
        System.out.println("Compression rate with LZW: " + getCompressionRate());

        // Do Huffman compression here
        String lzw = readFile("LZW.txt");

        int[] charFreqs = new int[11];

        for(char c : lzw.toCharArray()) {
            if(c == ' ') {
                charFreqs[10]++;
                continue;
            }
            charFreqs[Character.getNumericValue(c)]++;
        }

        // Build Huffman tree
        tree = huff.buildTree(charFreqs);

        huff.writeFile(huff.writeCode(tree, lzw), "Huffed.txt");
        
    }

    // Expands file with norwegian text to original
    public static void expand(ArrayList<Integer> compressed) {
        // System.out.println("expand(ArrayList<Integer> comressed)");
        HashMap<Integer, String> dictionary = buildExpansionDictionary();
        StringBuilder sb = new StringBuilder();
        int dictLength = 30;
        String entry;

        String w = "" + dictionary.get(compressed.remove(0));
        sb.append(w);

        for(int k: compressed) {
            entry = "";
            if(dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if(k == dictLength)
                entry = w + w.charAt(0);

            sb.append(entry);

            dictionary.put(dictLength++, w + entry.charAt(0));

            w = entry;
        }

        // System.out.println(sb.toString());
        writeFile(sb.toString(), "LZW_Expanded.txt");
    }

    public static void decompress(File file) {

        // Converting from binary huffman, to lzw library readable
        String binary = huff.writeCode(tree, readFile("Huffed.txt"));

        huff.decode(binary); // writes file called "unhuffed.txt" with lzw readable text

        // Starting the LZW decomression        
        ArrayList<Integer> compressed = new ArrayList<Integer>();
        String line = null;
        String[] arr;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("unhuffed.txt"));

            line = br.readLine();

            arr = line.split(" ");

            for(String s: arr) {
                compressed.add(Integer.parseInt(s));
            }
        }
        catch(IOException | NumberFormatException e){
            e.printStackTrace();
        }

        expand(compressed);
    }
    
    // Builds a basic 30 character long dictionary for compression
    public static HashMap<String, Integer>  buildCompressionDictionary() {
        HashMap<String, Integer> dictionary = new HashMap<String, Integer>();

        for(int i = 97, j = 0; j < 26; i++, j++) {
            dictionary.put("" + (char)i, j);
        }

        dictionary.put("æ", 26);
        dictionary.put("ø", 27);
        dictionary.put("å", 28);
        dictionary.put(" ", 29);
        
        return dictionary;
    }

    // Builds a basic 30 character long dictionary for decompression
    public static HashMap<Integer, String>  buildExpansionDictionary() {
        HashMap<Integer, String> dictionary = new HashMap<Integer, String>();

        for(int i = 97, j = 0; j < 26; i++, j++) {
            dictionary.put(j, "" + (char)i);
        }

        dictionary.put(26, "æ");
        dictionary.put(27, "ø");
        dictionary.put(28, "å");
        dictionary.put(29, " ");
        
        return dictionary;
    }

    /*
     * THIS SHOULD BE MERGED WITH THE OTHER WRITEFILE METHOD
     * USING A STRINGBUILDER IN ADVANCE...
     * SPAGHETTI CODE
     *
     */
    // Writes input to file
    public static void writeFile(ArrayList<Integer> list, String filename) {
        //System.out.println("writeFile(ArrayList<Integer>)");
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");

            for(Integer i: list) {
                writer.print((i + "") + " ");
            }
            
            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String str, String filename) {
        //System.out.println("writeFile(String str)");
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");

            writer.println(str);

            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
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
    
        // Returns the compression rate using the LZW compression (and Huffman compression on top)
        public static int getCompressionRate() {
            return 0;
    }
}

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
