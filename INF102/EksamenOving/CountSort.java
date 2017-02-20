class CountSort {
  public static void main(String[] args) {
    int[] arr = new int[] {1,1,1,4,2,4,2,4,5,6,4,67,8,56,4,6,4,67,4,63,6,79,
                            31,99,45,45,23,7,34,7,32,87,6,6,42,54,65,87,23};

    System.out.println("Unsorted Array: ");
    for(int i = 0; i < arr.length; i++)
      System.out.print(arr[i] + " ");

    arr = sort(arr);

    System.out.println("\nSorted Array: ");
    for(int i = 0; i < arr.length; i++)
      System.out.print(arr[i] + " ");
  } // End of main

  public static int[] sort(int[] a) {
    int[] bucket = new int[100];
    int index = 0;
    for(int i = 0; i < a.length; i++) {
      bucket[a[i]] ++;
    }

    for(int j = 0; j < 100; j++) {
      for(int k = 0; k < bucket[j]; k++) {
        a[index ++] = j;
      }
    }

    return a;
  } // End of sort()
} // End of CountSort, v16b.pdf task 2.
