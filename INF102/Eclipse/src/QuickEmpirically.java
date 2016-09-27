import java.util.Random;

import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdRandom;


public class QuickEmpirically {

	public static void main(String[] args) {
		int N = 20000; 
		Comparable[] arr = new Comparable[N];
		
		Stopwatch timer = new Stopwatch();
		
		for(int i = 0; i < N; i++) {
			Random rdm = new Random();
			int r = rdm.nextInt(N) + 1;
			arr[i] = r;
		}
		
		StdRandom.shuffle(arr);
		Quick.sort(arr);
		//assert isSorted(arr);
		
		double time = timer.elapsedTime();
		StdOut.printf("%.3f second\n", time);
	}

}
