
import java.util.Random;
import java.util.Arrays;
import edu.princeton.cs.algs4.*;

public class HeapTest {
	public static void main(String[] args) {
		final int N = 10000;
		
		String[] arr = new String[N];
		String[] arr10 = new String[N];
		String[] arr20 = new String[N];
		String[] arr40 = new String[N];
		String[] arr50 = new String[N];
		
		System.out.println("The modified Heap.java: ");
		HeapMod(arr, 2, N);
		HeapMod(arr10, 10, N);
		HeapMod(arr20, 20, N);
		HeapMod(arr40, 40, N);
		HeapMod(arr50, 50, N);
		
		System.out.println("The original Heap.java: ");
		HeapOrg(arr, 2, N);
		HeapOrg(arr10, 10, N);
		HeapOrg(arr20, 20, N);
		HeapOrg(arr40, 40, N);
		HeapOrg(arr50, 50, N);
	}
	
	public static void HeapMod(String[] ar, int k, int tot) {
		for(int i = 0; i < tot; i++) {
			ar[i] = stringRandom(k);
		}
        Arrays.sort(ar);
        Stopwatch timer = new Stopwatch();
        HeapOblig.sort(ar);
        double time = timer.elapsedTime();
 
        StdOut.printf(k + " char: %.3f second\n", time);
	}
	
	public static void HeapOrg(String[] ar, int k, int tot) {
		for(int i = 0; i < tot; i++) {
			ar[i] = stringRandom(k);
		}
        //Arrays.sort(ar);
        Stopwatch timer = new Stopwatch();
        Heap.sort(ar);
        double time = timer.elapsedTime();
 
        StdOut.printf(k + " char: %.3f second\n", time);
	}
	
	public static String stringRandom(int length) {
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		String randString = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = alphabet[random.nextInt(alphabet.length)];
            randString += c;
        }
 
        return randString;
	}
}
