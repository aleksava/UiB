import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.HashMap;

class MarkovModel {
    private HashMap<String, Bag> map;
    private int size = 1;

    public MarkovModel(int size) {
        map = new HashMap<String, Bag>();
        this.size = size;
    }

    // Adds the letter as a successor to the ngram
    // increments the amount of appearences if already existing
    public void add(String ngram, String nextLetter) {
        if(map.containsKey(ngram)) {
            Bag bag = map.get(ngram);
            bag.add(nextLetter);
            map.put(ngram, bag);
        }
        else {
            Bag bag = new Bag();
            bag.add(nextLetter);
            map.put(ngram, bag);
        }
    }

    // Adds all the possible ngrams from the inputText to the HashMap
    public void addAllNgrams(String inputText) {
        String ngram, nextLetter;

        for(int i = 0; i < inputText.length() - size; i++) {
            ngram = inputText.substring(i, i + size);
            nextLetter = inputText.substring(i + size, i + size + 1);

            add(ngram, nextLetter);
        }
    }

    // Reads text from a file to a String
    public String readFile(String path) {
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

    // Reads the given file, and adds it's ngrams
    public void loadData(String str) {
        String text = readFile(str);
        addAllNgrams(text);
    }

    // Returns a letter based on the given state
    public String getNextLetter(String state) {
        if(map.containsKey(state)) {
            Bag bag = map.get(state);
            return bag.getProbableLetter();
        }
        else {
            System.out.println("NEVER SEEN BEFORE: " + state);
            return "";
        }
    }
}
