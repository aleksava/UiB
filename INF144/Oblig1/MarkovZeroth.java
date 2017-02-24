import java.io.*;
import java.util.LinkedList;
import java.util.ArrayList;

class MarkovZeroth {
    public static void main (String[] args) {
        BufferedReader br = null;
        FileReader fr = null;
        ArrayList<String> words = new ArrayList<String>();

        try {
            fr = new FileReader("Folktale.txt");
            br = new BufferedReader(fr);

            String str;
            String[] arr;

            while(str = br.readLine() != null) {
                arr = str.split(" ");
                for(int i = 0; i < arr.length; i++) {
                    System.out.println(arr[i]);
                    words.add(arr[i]);
                }
                arr = null;
            }
        }
    } 
}
