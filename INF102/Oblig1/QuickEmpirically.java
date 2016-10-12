import java.util.Random;

import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.QuickX;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class QuickEmpirically {

	public static void main(String[] args) {
		
		int N = 320000; 
		Comparable[] arr = new Comparable[N];
		
		Stopwatch timer = new Stopwatch();
		
		for(int i = 0; i < N; i++) {
			Random rdm = new Random();
			int r = rdm.nextInt(N) + 1;
			arr[i] = r;
		}
		
		//Quick.sort(arr);
		QuickX.sort(arr);
		
		double time = timer.elapsedTime();
		StdOut.printf("%.3f second\n", time);
	}

}
