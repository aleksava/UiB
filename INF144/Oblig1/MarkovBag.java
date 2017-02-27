import java.util.HashMap;
import java.util.Random;

class MarkovBag {

    private HashMap<String, Integer> map;
    private Random rand = new Random();
    
    // char array of the alphabet
    private static String[] alphabet = new String[] {"a","b","c","d","e","f",
                                                     "g","h","i","j","k","l",
                                                     "m","n","o","p","q","r",
                                                     "s","t","u","v","w","x",
                                                     "y","z","æ","ø","å"," "};
    
    public MarkovBag() {
        this.map = new HashMap<String, Integer>();
        
        for(int i = 0; i < alphabet.length; i++) {
            map.put(alphabet[i], new Integer(0));
        }
    }
    
    public void add(String key) {
        int val = this.map.get(key).intValue();
        this.map.put(key, ++val);
    }

    public int get(String key) {
        return this.map.get(key).intValue();
    }
    
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
}
