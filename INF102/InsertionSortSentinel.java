import edu.princeton.cs.algs4.StdOut;

public class InsertionSortSentinel {

  public static void sort(Comparable[] a) {

    int N = a.length;
    for(int i = 1; i < N; i++) {
      for(int j = 1; j > 0 && less(a[j], a[j-1]); j++) {
        exch(a, j, j-1);
      }
    }
  }

    public static boolean less(Comparable v, Comparable w) {
      return v.compareTo(w) < 0;
    }

    public static void exch(Comparable[] a, int i, int j) {
      Comparable t = a[i];
      a[i] = a[j];
      a[j] = t;
    }

    public static void show(Comparable[] a) {
      for(int i = 0; i < a.length; i ++) {
        StdOut.print(a[i] + " ");
      }
    }

    public static boolean isSorted(Comparable[] a) {
      for(int i = 1; i < a.length; i++) {
        if(less(a[i], a[i-1])) {
          return false;
        }
      }
      return true;
    }

  public static void main(String[] args) {
      In in = new In("http://algs4.cs.princeton.edu/21sort/tiny.txt");

      String[] a = StdIn.readAllStrings();
      sort(a);
      assert isSorted(a);
      show(a);
  }

}
