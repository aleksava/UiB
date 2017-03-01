import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

class MarkovChain {

    // Hashmap
    public static Hashtable<String, Vector<String>> mChain = new Hashtable<String, Vector<String>>();
    static Random rnd = new Random();

    public static void main (String[] args) throws IOException {

        // Create the first two entries (k: _start, k: _end)
        mChain.put("_start", new Vector<String>());
        mChain.put("_end", new Vector<String>());

        //readFile();

        while(true) {
            System.out.print("Enter your phrase > ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String sInput = in.readLine() + ".";

            // Add the words to the Hashtable
            addWords(sInput);
        }
    }

    public static void addWords(String phrase) {
        String[] words = phrase.split(" ");

        for(int i = 0; i < words.length; i++) {
            if(i == 0) {
                Vector<String> startWords = mChain.get("_start");
                startWords.add(words[i]);

                Vector<String> suffix = mChain.get("_end");
                if(suffix == null) {
                    suffix = new Vector<String>();
                    suffix.add(words[i+1]);
                    mChain.put(words[i], suffix);
                }

            } else if(i == words.length - 1) {
                Vector<String> endWords = mChain.get("_end");
                endWords.add(words[i]);
                
            } else {
                Vector<String> suffix = mChain.get(words[i]);
                if(suffix == null) {
                    suffix = new Vector<String>();
                    suffix.add(words[i+1]);
                    mChain.put(words[i], suffix);
                } else {
                    suffix.add(words[i+1]);
                    mChain.put(words[i], suffix);
                }
            }
        }
        generateSentence();
    }

    public static void generateSentence() {
		
        // Vector to hold the phrase
        Vector<String> newPhrase = new Vector<String>();
		
        // String for the next word
        String nextWord = "";
				
        // Select the first word
        Vector<String> startWords = mChain.get("_start");
        int startWordsLen = startWords.size();
        nextWord = startWords.get(rnd.nextInt(startWordsLen));
        newPhrase.add(nextWord);
		
        // Keep looping through the words until we've reached the end
        while (nextWord.charAt(nextWord.length()-1) != '.') {
            Vector<String> wordSelection = mChain.get(nextWord);
            int wordSelectionLen = wordSelection.size();
            nextWord = wordSelection.get(rnd.nextInt(wordSelectionLen));
            newPhrase.add(nextWord);
        }
		
        System.out.println("New phrase: " + newPhrase.toString());	
    }

    
    
    /*
        private static void readFile(String fileName) {
        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String str;
            String[] arr;

            while((str = br.readLine()) != null) {
                arr = str.split(" ");
                for(int i = 0; i < arr.length; i++) {
                    arrL.add(arr[i]);
                }
                arr = null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
