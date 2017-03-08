import java.util.*;
import java.io.*;

class LZWHuffman {

    private static final int set = 30; // 29 norwegian letters + <spacae>
    private static HuffmanTree tree;
    private static Huffman huff = new Huffman();
    
    public static void main (String[] args) {
        int filesize = (int)(new File("Folktale.txt").length()/1024);
        int compressedSize = 0;
        File compressed;

        //System.out.println("File size pre-compression: " + filesize + " kB");

        compress(readFile("Folktale.txt").toLowerCase());

        
 
        compressed = new File("coded.lzw");
        compressedSize = (int)(compressed.length()/1024);

        //System.out.println("File size post-compression: " + compressedSize + " kB");

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

        StringBuilder sb = new StringBuilder();
        String space = "";
        for(int i : compressed) {
            sb.append(space);
            space = " ";
            sb.append(i);
        }
        
        writeFile(sb.toString(), "coded.lzw"); // Need to write huffman compression on top of this
        System.out.println("Compression rate with LZW: " + getCompressionRate());

        // Do Huffman compression here
        String lzw = readFile("coded.lzw");

        int[] charFreqs = new int[11];

        for(char c : lzw.toCharArray()) {
            if(c != ' ') {
                charFreqs[Character.getNumericValue(c)]++;
            }
            else
                charFreqs[10]++;
        }

        // Build Huffman tree
        tree = huff.buildTree(charFreqs);
        
        String output = huff.writeCode(tree, lzw);

        huff.writeFile(output, "code.huff");
    }

    // Expands file with norwegian text to original
    public static void expand(ArrayList<Integer> compressed) {
        HashMap<Integer, String> dictionary = buildExpansionDictionary();
        StringBuilder sb = new StringBuilder();
        int dictLength = 30;
        String entry;

        String w = dictionary.get(compressed.remove(0));
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

        writeFile(sb.toString(), "Output.txt");
    }

    public static void decompress(File file) {

        // Converting from binary huffman, to lzw library readable
        String binary = readFile("code.huff");
        
        huff.decode(binary); // writes a text file called "lzw.huff" with lzw readable text
        
        // Starting the LZW decomression        
        ArrayList<Integer> compressed = new ArrayList<Integer>();
        String line = null;
        String[] arr;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("lzw.huff"));

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

    // writes to a file from a single string
    public static void writeFile(String str, String filename) {

        System.out.println("The length of " + filename + " is: " + str.length());
        
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
