import java.util.*;
import java.io.*;

public class LZW {
    /** Compress a string to a list of output symbols. */
    public static List<Integer> compress(String uncompressed) {
        // Build the dictionary.
        int dictSize = 256;
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < 256; i++) {
            dictionary.put("" + (char)i, i);
            System.out.print((char)i + " ");
        }
        
        String w = "";
        List<Integer> result = new ArrayList<Integer>();
        for (char c : uncompressed.toCharArray()) {
            String wc = w + c;
            if (dictionary.containsKey(wc))
                w = wc;
            else {
                result.add(dictionary.get(w));
                // Add wc to the dictionary.
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
        }
 
        // Output the code for w.
        if (!w.equals(""))
            result.add(dictionary.get(w));

        writeFile(result, "LZW_CHEAT.txt");
        
        return result;
    }
 
    /** Decompress a list of output ks to a string. */
    public static String decompress(List<Integer> compressed) {
        // Build the dictionary.
        int dictSize = 256;
        Map<Integer,String> dictionary = new HashMap<Integer,String>();
        for (int i = 0; i < 256; i++)
            dictionary.put(i, "" + (char)i);
 
        String w = "" + (char)(int)compressed.remove(0);
        StringBuffer result = new StringBuffer(w);
        for (int k : compressed) {
            String entry;
            if (dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if (k == dictSize)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);
 
            result.append(entry);
 
            // Add w+entry[0] to the dictionary.
            dictionary.put(dictSize++, w + entry.charAt(0));
 
            w = entry;
        }

        writeFile(result.toString(), "LZW_FAKE.txt");
        
        return result.toString();
    }

        
    // Writes input to file
    public static void writeFile(List<Integer> list, String filename) {

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
 
    public static void main(String[] args) {
        List<Integer> compressed = compress(readFile("Folktale.txt"));
        //System.out.println(compressed);
        String decompressed = decompress(compressed);
        //System.out.println(decompressed);
    }
}
