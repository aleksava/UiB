import java.io.*;
import java.util.Vector;
import java.util.HashMap;
import java.util.Random;

class MarkovZeroth {

    public static HashMap<String, Vector<String>> mChain = new HashMap<String, Vector<String>>();
    
    public static void main (String[] args) {
        

        words = readFile("Folktale.txt", words);

        char[] alphabet = new char[] {'a','b','c','d','e','f',
                                      'g','h','i','j','k','l',
                                      'm','n','o','p','q','r',
                                      's','t','u','v','w','x',
                                      'y','z','æ','ø','å',' '};
        char start = 'a';

        
    }

    public static void analyze(String text) {
        
    }

    public static void readFile(String fileName) {
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
                    addWord(arr[i]);
                }
                arr = null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return arrL;
    }

    public static void  addWord(String text) {
        char[] letters = text.toCharArray();

        for(int i = 0; i < words.length; i++) {

            if(i == 0) {
                Vector<String> startWord = 
            }
        }
    }
}
