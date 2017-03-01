import java.util.Random;
import java.util.HashMap;

// A bag to hold third approximation markov chain strings
class MarkovBagCubed {
    private HashMap<String, MarkovBagSquared> map;
    private Random rand = new Random();

    // char array of the alphabet for delivering the output character
    private static String[] alphabet = new String[] {"a","b","c","d","e","f",
                                                     "g","h","i","j","k","l",
                                                     "m","n","o","p","q","r",
                                                     "s","t","u","v","w","x",
                                                     "y","z","æ","ø","å"," "};

    public MarkovBagCubed() {
        this.map = new HashMap<String, MarkovBagSquared>();
        for(int i = 0; i < alphabet.length; i++) {
            map.put(alphabet[i], new MarkovBagSquared());
        }
    }

    public void add(String key) {
        MarkovBagSquared bag = map.get(key);
        bag.add(key);
        bag.incInt();
        this.map.put(key, bag);
    }

    public MarkovBagSquared get(String key) {
        return this.map.get(key);
    }

    public String getRandNext() {
        int[] stats = new int[30];
        MarkovBag bag = null;
        int num = 0;
        String out = "";
        
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
                    out = map.get(alphabet[i]).getRandNext();
                }
            }
            else {
                if(num < stats[i] && num > stats[i-1]) {
                    return map.get(alphabet[i]).getRandNext();
                }
            }
        }
        System.out.println("OUT: " + out);
        return out;
    }
}
