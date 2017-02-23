class MergeSort {
  public static void main(String[] args) {
    //lalala
  }

  // precondition: 0 <= lo <= mid <= hi <= a.length, and a[mid+1...i] are sorted
  public static void merge(Comparable[] a, int lo, int mid, int hi) {
    //lalala
    // postcondition: a[lo..hi] is sorted
  }

  public static void sort(Comparable[] a, int lo, int hi) {
    if(hi <= lo) return;
    int mid = (hi + lo)/2;
    sort(a, lo, mid);
    sort(a, mid+1, hi);
    merge(a, lo, mid, hi);
  }
}
