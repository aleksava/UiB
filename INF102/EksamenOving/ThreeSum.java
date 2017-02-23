import java.util.Arrays;

class ThreeSum {
  public static void main(String[] args) {
    int[] arr = new int[] {12,12,-123,4,-32,3,45,563,25,45,346,534,345,
                            12,4,3,532,35,-53,24,4,2,35,534,-263};
    System.out.println("Total triples, where sum < 0: " + count(arr));
  }

  // This runs in coubic time O(N^3), and will not give full score on the exam
  // Will need to modify the code commented out below, in order to score max
  // Below: for(for(binarySearch)) N*N*logN
  public static int count(int[] a) {
    int N = a.length;
    int cunt = 0;
    for(int i = 0; i < N; i ++) {
      for(int j = i+1; j < N; j++) {
        for(int k = j+1; k < N; k++) {
          if(a[i] + a[j] + a[k] < 0) cunt++;
        }
      }
    }
    return cunt;
  }
  /**
  *This just was a fuckup
  public static void main(String[] args) {
    int[] arr = new int[] {12,12,123,4,32,3,45,563,25,45,346,534,345,
                            12,4,3,532,35,53,24,4,2,35,534,623263};
    System.out.println("There are " + countTriples(arr) + " triples in the array");
  }

  public static int countTriples(int[] a ) {
    int N = a.length;
    Arrays.sort(a);
    int triples = 0;
    for(int i = 0; i < N - 2; i++ ) {
      for(int j = i+1; j < N - 1; j++) {
        int maybe = myBinarySearch(-(a[i] + a[j]), a, j+1, N-1);
        if(j <= maybe && maybe < N-1) triples += N-1-maybe;
      }
    }
    return triples;
  }

  public static int myBinarySearch(int key, int[] a, int low, int high) {
    // pre 0 <= low <= high
    // binary search for position where key should occur in a[low.. high] (if at all)
    int mid = -1;
    while(low <= high) {
      mid = (low + high)/2;
      if(key == a[mid]) return mid;
      if(key < a[mid]) high = mid-1;
      if(key > a[mid]) low = mid+1;
    }
    return (key > a[mid]) ? low : high;
  }**/
}
