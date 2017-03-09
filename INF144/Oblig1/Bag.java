import java.util.HashMap;
import java.util.Random;

public class Bag {
    private HashMap<String, Integer> data;
    private Random rand = new Random();
    private String mostFrequent;
    private int size = 0;

    public Bag() {
        data = new HashMap<String, Integer>();
    }

    // Adds the given letter to the data, increments if
    // the letter allready exists
    public void add(String letter) {
        if(data.containsKey(letter)) {
            int num = data.get(letter);
            data.put(letter, ++num);
        }
        else {
            data.put(letter, 1);
        }

        // Incrementing the size of the HashMap
        size ++;
    }

    // Returns a letter from this HashMap, the more often a letter
    // has appeared, the chances of it being chosen is higher
    public String getProbableLetter() {
        int num = rand.nextInt(getSize());
        int sum = 0;
        
        for(String key : data.keySet()) {
            sum += data.get(key).intValue();
            if(num < sum) return key;
        }

        return "ERROR!";
    }

    // Avoids a large try catch and NullPointerException
    // Return how often a letter appears in the HashMap
    public int getFrequency(String letter) {
        if(data.containsKey(letter))
            return data.get(letter).intValue();
        return 0;
    }

    // Returns the amount of different letters in the bag
    public int getSize() {
        return size;
    }

    public String toString() {
        return data.toString();
    }
}
