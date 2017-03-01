import java.util.HashMap;
import java.util.Set;

public class Bag {
    private HashMap<String, Integer> data;
    private String mostFrequent;
    private int size = 0;

    public Bag() {
        data = new HashMap<String, Integer>();
    }

    public int getFrequency(String letter) {
        if(data.containsKey(letter)) return data.get(letter).intValue();
        return 0;
    }

    public int getSize() {
        return size;
    }
    
    public void add(String letter) {
        size ++;
        if(data.containsKey(letter)) {
            int num = data.get(letter);
            data.put(letter, ++num);
        }
        else {
            data.put(letter, 1);
        }

        if(getFrequency(letter) > getFrequency(mostFrequent)) {
            mostFrequent = letter;
        }
    }

    public String getMostFrequent() {
        return mostFrequent;
    }

    public String getRandomLetter() {
        Set<String> set = data.keySet();
        String[] letters = set.toArray(new String[0]);
        return letters[(int)(Math.random()*letters.length)];
    }

    public String getRandomLetterByFrequency() {
        int num = (int)(Math.random()*getSize());

        int sum = 0;
        
        for(String key : data.keySet()) {
            sum += data.get(key);
            if(num < sum) return key;
        }

        return "ERROR!";
    }

    public String toString() {
        return data.toString();
    }
}
