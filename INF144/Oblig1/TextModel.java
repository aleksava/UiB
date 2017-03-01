import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class TextModel {
    private HashMap<String, Bag> map;

    public TextModel() {
        map = new HashMap<String, Bag>();
    }

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

    public void addAllNgramsIn(String inputText) {
        for(int i = 0; i < inputText.length() - 3; i++) {
            String ngram, nextLetter;

            ngram = inputText.substring(i, i + 3);
            nextLetter = inputText.substring(i + 3, i + 4);
            //System.out.println("Adding: " + ngram + " next is: " + nextLetter);
            add(ngram, nextLetter);
        }
    }

    public String getFileAsString(String path) {
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            while(line != null) {
                sb.append(line.toLowerCase());
                line = br.readLine();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public void loadData(String str) {
        String text = getFileAsString(str);
        addAllNgramsIn(text);
    }

    public String getNextLetter(String state) {
        if(map.containsKey(state)) {
            Bag bag = map.get(state);
            return bag.getRandomLetterByFrequency();
        }
        else {
            System.out.println("NEVER SEEN BEFORE: " + state);
            return "";
        }
    }
}
