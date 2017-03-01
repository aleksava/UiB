import java.util.HashMap;
import java.util.Random;

// A bag to hold first approximation of a markov chain
class MarkovBag {

    private HashMap<String, Integer> map;
    private Random rand = new Random();
    private int prob = 0;
    
    // char array of the alphabet for delivering the output character
    private static String[] alphabet = new String[] {"a","b","c","d","e","f",
                                                     "g","h","i","j","k","l",
                                                     "m","n","o","p","q","r",
                                                     "s","t","u","v","w","x",
                                                     "y","z","æ","ø","å"," "};

    // Constructor that fills HashMap with the allowed chars
    // helping to avoid NullPointerException
    public MarkovBag() {
        this.map = new HashMap<String, Integer>();
        
        for(int i = 0; i < alphabet.length; i++) {
            map.put(alphabet[i], new Integer(0));
        }
    }

    // Increments the occurences of a letter after the current
    public void add(String key) {
        int val = this.map.get(key).intValue();
        this.map.put(key, ++val);
    }

    // Fetches the number of times a letter has occurred
    public int get(String key) {
        return this.map.get(key).intValue();
    }

    // Fetches a random next letter based on probability
    public String getRandNext() {
        int[] stats = new int[30];
        int num = 0;

        // Gathering stats to one array
        for(int i = 0; i < stats.length; i++) {
            if(i == 0) 
                stats[i] = map.get(alphabet[i]).intValue();
            else
                stats[i] = (stats[i-1] + map.get(alphabet[i]).intValue());
        }
        
        num = rand.nextInt(stats[29] + 1);

        // Finds the appropriate String in the alphabet array
        for(int i = 0; i < stats.length; i++) {
            if(i == 0) {
                if(num < stats[i]) {
                    return alphabet[i];
                }
            }
            
            else {
                if(num < stats[i] && num > stats[i-1]) {
                    return alphabet[i];
                }
            }
        }
        
        return "";
    }

    public int getInt() {
        return prob;
    }

    public void incInt() {
        prob++;
    }
}
