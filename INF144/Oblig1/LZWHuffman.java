import java.util.*;
import java.io.*;

class LZWHuffman {

    private static final int set = 30; // 29 norwegian letters + <spacae>
    private static HuffmanTree tree;
    private static Huffman huff = new Huffman();
    
    public static void main (String[] args) {
        File compressed;
        MarkovOblig model = new MarkovOblig();
        ArrayList<Double> rate = new ArrayList<Double>();
        double average = 0.0;

        // Generates 100 new texts that are compressed, and the compression
        // rate is measured. To remove Huffman compression, out needs to be
        // multiplied by 3 to get bit requirement. Also, output.length() as the
        // return value for compress() must be changed to sb.toString().length()
        for(int i = 0; i < 100; i++) {
            int out = compress(model.getOutput(3, "ask", 8000).toLowerCase());
            rate.add((double)(8000*4)/(out));
        }

        // Finds average compression rate
        for(int i = 0; i < rate.size(); i++){
            average += rate.get(i);
        }
        average = average/100;

        System.out.print("The average compression rate with huffman, is: ");
        System.out.printf("%.4f\n", average);

        // Decomressing the last file
        compressed = new File("coded.lzw");
        decompress(compressed);
        
    } // End of main

    // Compresses files with norwegian text
    public static int compress(String input) {
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

        // Creating the output string to be further compressed
        StringBuilder sb = new StringBuilder();
        String space = "";
        for(int i : compressed) {
            sb.append(space);
            space = " ";
            sb.append(i);
        }
        
        writeFile(sb.toString(), "coded.lzw"); // Need to write huffman compression on top of this

        // Huffman compression on the newly written file is done here
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

        writeFile(output, "code.huff");

        return output.length();
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

        // Writes the decompressed file as "Output.txt"
        writeFile(sb.toString(), "Output.txt");
    }

    
    // Reads file, builds an ArrayList to use in the expand() method
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
    // Duplicate method because keys and values had to be switched
    public static HashMap<Integer, String>  buildExpansionDictionary() {
        HashMap<Integer, String> dictionary = new HashMap<Integer, String>();

        for(int i = 97, j = 0; j < 26; i++, j++) {
            dictionary.put(j, "" + (char)i);
        }

        // Putting in special characters
        dictionary.put(26, "æ");
        dictionary.put(27, "ø");
        dictionary.put(28, "å");
        dictionary.put(29, " ");
        
        return dictionary;
    }

    
    // writes to a file from a single string
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
}
