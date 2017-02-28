import java.util.Random;
import java.util.HashMap;

// A bag to hold second approximation of a markov chain
class MarkovBagSquared {
    private HashMap<String, MarkovBag> map;
    private Random rand = new Random();
    
    // char array of the alphabet for delivering the output character
    private static String[] alphabet = new String[] {"a","b","c","d","e","f",
                                                     "g","h","i","j","k","l",
                                                     "m","n","o","p","q","r",
                                                     "s","t","u","v","w","x",
                                                     "y","z","æ","ø","å"," "};

    public MarkovBagSquared() {
        this.map = new HashMap<String, MarkovBag>();

        for(int i = 0; i < alphabet.length; i++) {
            map.put(alphabet[i], new MarkovBag());
        }
    }

    public void add(String key) {
        MarkovBag bag = map.get(key);
        bag.add(key);
        bag.incInt();
        this.map.put(key, bag);
    }

    public MarkovBag get(String key) {
        return map.get(key);
    }

    public int get(String key, String key2) {
         
        return 0;
    }

    public String getRandNext() {
        int[] stats = new int[30];
        MarkovBag bag = null;
        int num = 0;

        // Gathering stats to one array
        for(int i = 0; i < stats.length; i++) {
            if(i == 0) 
                stats[i] = map.get(alphabet[i]).getInt();
            else
                stats[i] = (stats[i-1] + map.get(alphabet[i]).getInt());
        }
        
        num = rand.nextInt(stats[29] + 1);

        // Finds the appropriate MarkovBag to search in
        for(int i = 0; i < stats.length; i++) {
            if(i == 0) {
                if(num < stats[i]) {
                    return map.get(alphabet[i]).getRandNext();
                }
            }
            else {
                if(num < stats[i] && num > stats[i-1]) {
                    return map.get(alphabet[i]).getRandNext();
                }
            }
        }
        
        return null;
    }
}
