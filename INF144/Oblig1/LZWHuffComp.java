import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;

class LZWHuffComp {

    private static final int set = 30; // 29 norwegian letters + <spacae>
    
    public static void main (String[] args) {
        MarkovModel dictionary = new MarkovModel(3);
        File file = new File("Folktale.txt");
        File compressed;
        int filesize = (int)(file.length()/1024);
        int compressedSize = 42;
        dictionary.loadData("Folktale.txt");
        

        System.out.println("File size pre-compression: " + filesize + " kB");

        compress("toobeornottobeeornot");

        compressed = new File("Output.txt");
        compressedSize = (int)(compressed.length()/1024);

        System.out.println("File size post-compression: " + compressedSize + " kB");
    }

    // Compresses files with norwegian text
    public static void compress(String input) {
        HashMap<String, Integer> dictionary = buildBasicDictionary();
        ArrayList<Integer> compressed = new ArrayList<Integer>();
        int dictLength = 30;
        String a = "";

        // Build the compressed output, and continue on the dictionary
        for(String s: input.split("")) {
            String as = a + s;
            if(dictionary.containsKey(as))
                a = as;
            else {
                compressed.add(dictionary.get(a));
                dictionary.put(as, dictLength++);
                a = s;
            }
        }

        if(!a.equals("")) {
            compressed.add(dictionary.get(a));
        }

        writeFile(compressed); // Need to write huffman compression on top of this
    }

    // Expands file with norwegian text to original
    public static void expand() {
        HashMap<String, Integer> dictionary = buildBasicDictionary();
        int dictLength = 30;

        
    }

    // Builds a basic 30 character long dictionary
    public static HashMap<String, Integer>  buildBasicDictionary() {
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

    // Writes input to file
    public static void writeFile(ArrayList<Integer> list) {

        try {
            PrintWriter writer = new PrintWriter("Output.txt", "UTF-8");

            for(Integer i: list) {
                writer.print((i + "") + " ");
            }
            
            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    // Returns the compression rate using the LZW compression (and Huffman compression on top)
    public static int getCompressionRate() {
        return 0;
    }
}
