import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Triplicates {

	public static void main(String[] args) {
		
		String[] arr1 = {"aa", "bb", "cc", "dd", "ee"};
		String[] arr2 = {"a", "bc", "cc", "d", "e"};
		String[] arr3 = {"aa", "b", "cd", "du", "e"};
		String[] arr4 = {"aa", "bb", "cc", "dd", "ee"};
		
		List<String> merge = new ArrayList<>();
		
		find(arr1, arr2, arr3, arr4, merge);
		System.out.println(merge);
	}
	
	public static void find(String[] arr1, String[] arr2, String[] arr3, String[] arr4, List<String> merge) {
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		Arrays.sort(arr3);
		Arrays.sort(arr4);
		
		for(String s : arr1) {
			int counter = 1;
			
			if(Arrays.binarySearch(arr2, s) >= 0) 
				counter ++;
			
			if(Arrays.binarySearch(arr3, s) >= 0)
				counter ++;
			
			if(Arrays.binarySearch(arr4, s) >= 0)
				counter ++;
			
			if(counter >= 3 && Collections.binarySearch(merge, s) < 0) {
				merge.add(s);
			}
		}
		
		for(String s: arr2) {
			int counter = 1;
			
			if(Arrays.binarySearch(arr1, s) >= 0) 
				counter ++;
			
			if(Arrays.binarySearch(arr3, s) >= 0)
				counter ++;
			
			if(Arrays.binarySearch(arr4, s) >= 0)
				counter ++;
			
			if(counter >= 3 && Collections.binarySearch(merge, s) < 0) {
				merge.add(s);
			}
		}
	}
}
