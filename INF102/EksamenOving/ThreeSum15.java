import java.util.Arrays;

class ThreeSum15 {
  public static void main(String[] args) {
    int[] arr = new int[] {12,12,-123,4,-32,3,45,563,25,45,346,534,345,
                            12,4,3,532,3,-53,24,4,2,35,534,-263};
    System.out.println("Total triples, where sum > 0: " + threeSumPos(arr));
    System.out.println("Total triples full score, where sum > 0: " + threeSumPosFull(arr));

  }

  public static int threeSumPos(int[] a) {
    int N = a.length;
    int triples = 0;

    for(int i = 0; i < N; i++) {
      for(int j = i+1; j < N; j++) {
        for(int k = j+1; k < N; k++) {
          if(a[i] + a[j] + a[k] > 0) triples ++;
        }
      }
    }

    return triples;
  }

  public static int threeSumPosFull(int[] a) {
    int N = a.length;
    int triples = 0;
    Arrays.sort(a);

    for(int i = 0; i < N; i++) {
      for(int j = i+1; j < N-1; j++) {
        int maybe = myBinarySearch(-(a[i] + a[j]), a, j+1, N-1);
        if(j <= maybe && maybe < N-1) triples += N-1-maybe;
      }
    }

    return triples;
  }

  public static int myBinarySearch(int key, int[] a, int low, int high) {
    int mid = -1;

    while(low <= high) {
      mid = (low + high)/2;
      if(key == a[mid]) return mid;
      if(key < a[mid]) high = mid-1;
      if(key > a[mid]) low = mid+1;
    }

    return (key > a[mid]) ? low : high;
  }
}
