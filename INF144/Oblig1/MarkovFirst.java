import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Random;

public class MarkovFirst {

    // Random generator and HashMap
    private static Random rnd = new Random();
    private static HashMap<String, MarkovBag> mChain = new HashMap<String, MarkovBag>();
    
    // String array of the alphabet
    private static String[] alphabet = new String[] {"a","b","c","d","e","f",
                                                     "g","h","i","j","k","l",
                                                     "m","n","o","p","q","r",
                                                     "s","t","u","v","w","x",
                                                     "y","z","æ","ø","å"," "};
    
    public static void main(String[] args) throws IOException {

        // Analyses the given input text to a markov chain.
        readFile("Folktale.txt");

        // Generates text where length = input parameter
        generateText(50);
		
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

                addText(arr);
                
                arr = null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	
    // Add text to the markov chain
    public static void addText(String[] phrase) {

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
    }
    
    
    // Generate random Norwegian text based on a markov model
    public static void generateText(int max) {
        int length = 0;
        String out = "a";
        String next = "";

        // Builds output string within given parameters
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
