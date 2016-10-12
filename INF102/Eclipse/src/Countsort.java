import java.util.Random;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Countsort {

	public static void main(String[] args) {

		//Initializing and filling in the array that's to be sorted
		int N = 160000000;
		Integer[] arr = new Integer[N];
		
		for(int i = 0; i < N; i++) {
			Random rnd = new Random();
			int num = rnd.nextInt(100);
			arr[i] = num;
		}
		
		//Starting the CPU stopwatch
		Stopwatch timer = new Stopwatch();
		sort(arr);

		double time = timer.elapsedTime();
		StdOut.printf("(%.4f seconds)\n", time);
	}
	
	//The first part of the sorting, finds the min- and max value of the numbers in the array
	public static void sort(Integer[] array) {
		
        if (array.length == 0) {
            return;
        }

        Integer minValue = array[0];
        Integer maxValue = array[0];
        
        for(int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            } 
            else if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }

        sort(array, minValue, maxValue);
    }

    public static void sort(Integer[] array, int minValue, int maxValue) {
    	
    	//An array that equals the span of the numbers in length
        int[] buckets = new int[maxValue - minValue + 1];

        //Filling up the correct bucket when the corresponding number arises
        for(int i = 0; i < array.length; i++) {
            buckets[array[i] - minValue]++;
        }

        //Putting the elements back into the array, in sorted order
        int sortedIndex = 0;
        for(int i = 0; i < buckets.length; i++) {
            while (buckets[i] > 0) {
                array[sortedIndex++] = i + minValue;
                buckets[i]--;
            }
        }
    }
}