import java.io.*;
import java.util.HashMap;
import java.util.Random;

class MarkovZeroth {

    private static HashMap<Character, Integer> mChain = new HashMap<Character, Integer>();
    
    // char array of the alphabet
    private static char[] alphabet = new char[] {'a','b','c','d','e','f',
                                                'g','h','i','j','k','l',
                                                'm','n','o','p','q','r',
                                                's','t','u','v','w','x',
                                                'y','z','æ','ø','å',' '};

    public static void main (String[] args) {
        char start = 'a';

        // fills the hashmap with all the values it should have to avoid
        // nullpointerexceptions (in theory)
        for(int i = 0; i < alphabet.length; i++) {
            mChain.put(alphabet[i], new Integer(0));
        }

        // initiates the analysis of the text
        readFile("Folktale.txt");

        // prints out the generated text
        System.out.println(generateText(100));
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
                arr = str.split(" ");

                
                for(int i = 0; i < arr.length; i++) {
                    int val = mChain.get(' ').intValue();
                    mChain.put(' ', new Integer(val + 1));
                    addChar(arr[i]);
                }
                arr = null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addChar(String text) {
        char[] letters = text.toCharArray();
        int value = 0;
        
        for(int i = 0; i < letters.length; i++) {
            value = mChain.get(letters[i]).intValue();
            mChain.put(letters[i], new Integer(value + 1));
        }
    }

    public static String generateText(int length) {
        int[] prob = new int[30];
        String out = "";

        // Gathering the probabilities in one place for each char
        for(int i = 0; i < prob.length; i++) {
            if(i == 0) 
                prob[i] = mChain.get(alphabet[i]).intValue();
            else
                prob[i] = (prob[i-1] + mChain.get(alphabet[i]).intValue());
        }

        // Generating the output String
        for(int i = 0; i < length; i++) {
            int num = 0;
            Random rnd = new Random();
            num = rnd.nextInt(prob[29] + 1);
            
            for(int j = 0; j < prob.length; j++) {
                if(j == 0) {
                    if(num < prob[j]) {
                        out += alphabet[j];
                        break;
                    }
                }
                else {
                    if(num < prob[j] && num > prob[j-1]) {
                        out += alphabet[j];
                        break;
                    }
                }
            }
        }

        // Marking the end of the built String with a period
        out += ".";
        return out;
    }
}
