import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;


public class Markov {

    // Hashmap
    public static Hashtable<String, Vector<String>> markovChain = new Hashtable<String, Vector<String>>();
    static Random rnd = new Random();

    public static HashMap<String, MarkovBag> mChain = new HashMap<String, MarkovBag>();
    
    // char array of the alphabet
    private static String[] alphabet = new String[] {"a","b","c","d","e","f",
                                                     "g","h","i","j","k","l",
                                                     "m","n","o","p","q","r",
                                                     "s","t","u","v","w","x",
                                                     "y","z","æ","ø","å"," "};
	
    /*
     * Main constructor
     */
    public static void main(String[] args) throws IOException {
		
        // Create the first two entries (k:_start, k:_end)
        // markovChain.put("_start", new Vector<String>());
        // markovChain.put("_end", new Vector<String>());

        readFile("Folktale.txt");
        
        generateText(10);
		
    }

    
    // readFile() analyses the given file, gives stats of each character
    public static void readFile(String fileName) {
        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String str;
            String[] arr;

            while((str = br.readLine()) != null) {
                str = str.toLowerCase();
                arr = str.split("");

                addWords(arr);
                
                arr = null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    /*
     * Add words
     */
    public static void addWords(String[] phrase) {

        for(int i = 0; i < phrase.length; i ++) {

            if(!mChain.containsKey(phrase[i])) {
                mChain.put(phrase[i], new MarkovBag());
            }
            
            else if(i != phrase.length - 1){
                MarkovBag bag = mChain.get(phrase[i]);
                bag.add(phrase[i+1]);
                mChain.put(phrase[i], bag);
            }
        }
				
        // Loop through each word, check if it's already added
        // if its added, then get the suffix vector and add the word
        // if it hasn't been added then add the word to the list
        // if its the first or last word then select the _start / _end key
	
    }
	
	
    /*
     * Generate a markov phrase
     */

    public static void generateText(int max) {
        int length = 0;
        String out = "a";
        String next = "";

        while(length < max) {
            next = mChain.get(out.substring(out.length()-1, out.length())).getRandNext();
            out += next;
            length ++;
        }

        // Marking the end of the built String with a period
        out += ".";
        System.out.println("The new random first order markov generated text: \n" + out);
    }
}
