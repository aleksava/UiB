import java.util.*;
import java.io.*;

class LZWHuffComp {

    private static final int set = 30; // 29 norwegian letters + <spacae>
    
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

        buildTree();
        writeHuff();
        
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
        //System.out.println("decompress(File file)");
        ArrayList<Integer> compressed = new ArrayList<Integer>();
        String line = null;
        String[] arr;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

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
