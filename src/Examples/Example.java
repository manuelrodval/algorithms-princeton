public class Example {
    
}

// public class Example {
//     public static void sort(Comparable[] a){
//         // Sort Algorithm
//     }

//     private static boolean less(Comparable v, Comparable w) {
//         return v.compareTo(w) < 0;
//     }

//     private static void exch(Comparable[] a, int i, int j){
//         Comparable t = a[i]; a[i] = a[j]; a[j] = t;
//     }

//     public static void show(Comparable[] a) {
//         // Print the array, on a single line
//         for (int i =0; i < a.length; i++) StdOut.print(a[i] + " ");
//         StdOut.println();
//     }

//     public static boolean isSorted(Comparable[] a) {
//         // Test whether the array entries are in order
//         for (int i = 1; i < a.length; i++) {
//             if (less(a[i], a[i-1])) return false;
//         }
//         return true;
//     }

//     public static void main(String[] args) {
//         // Read strings from StdIn, sort them and print.
//         String[] a = In.readStrings();
//         sort(a);
//         assert isSorted(a);
//         show(a);
//     }
// }

// public class Selection {
//     public static void sort(Comparable[] a) {
//         // Sort a[] into increasing order. 
//         int N = a.length;
//         for (int i = 0; i < N; i++) {
//             // Exchange a[i] with smallest entry in a[i+1...N]
//             int min = i;
//             for (int j = i+1; j < N; j++) 
//                 if (less(a[j], a[min])) min = j;
//             exch(a, i, min);
//         }
//     }
// }

// public class Insertion {
//     public static void sort(Comparable[] a) { 
//         // Sort a[] into increasing order.
//         int N = a.length;
//         for (int i = 1; i < N; i++) { 
//             // Insert a[i] among a[i-1], a[i-2], a[i-3]... ..
//             for (int j = i; j > 0 && less(a[j], a[j-1]); j--)
//             exch(a, j, j-1);
//         }
//     }
// }

// public class Shell {
//     public static void sort(Comparable[] a) {
//         // Sort a[] into increasing order
//         int N = a.length;
//         int h = 1;
//         while (h < N/3) h = 3*h + 1;
//         while (h >= 1) {
//             for (int i = h; i < N; i++) {
//                 // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]
//                 for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
//                     exch(a, j, j-h);
//                 }
//             }
//             h = h/3;
//         }
//     }
// }

// public class StdRandom {
//     public static void shuffle(Object[] a) {
//         int N = a.length;
//         for (int i = 0; i < N; i++) {
//             int r = StdRandom.uniform(i + 1);
//             exch(a, i, r);
//         }
//     }
// }


// public static void merge(Comparable[] a, int lo, int mid, int hi) {
//     // Merge a[lo...mid] with a[mid+1...hi].
//     int i = lo, j = mid+1;
//     for (int k = lo; k <= hi; k++) aux[k] = a[k]; // Copy a[lo...hi] to aux[lo...hi].
//     for (int k = lo; k <= hi; k++)
//         if (i > mid) a[k] = aux[j++];
//         else if (j > hi) a[k] = aux[i++];
//         else if (less(aux[j], aux[i])) a[k] = aux[j++];
//         else a[k] = aux[i++];
// }

// public class Merge {
//     private static Comparable[] aux; // Auxiliary array for merges.
//     public static void sort(Comparable[] a) {
//         aux = new Comparable[a.length]; // Allocate space just once.
//         sort(a, 0, a.length - 1);
//     }
//     private static void sort(Comparable[] a, int lo, int hi) {
//         // Sort a[lo...hi].
//         if (hi <= lo) return;
//         int mid = lo + (hi - lo)/2;
//         sort(a, lo, mid); // Sort left half.
//         sort(a, mid+1, hi); // Sort right half.
//         merge(a, lo, mid, hi); // Merge results.
//     }
// }

// public class MergeBU {
//     private static Comparable[] aux; // Axiliary array for merges.
//     public static void sort(Comparable[] a) {
//         // Do lgN passes of pairwise merges.
//         int N = a.length;
//         aux = new Comparable[N];
//         for (int sz = 1; sz < N; sz = sz+sz) // sz: Subarray Size
//             for (int lo = 0; lo < N-sz; lo += sz+sz) // lo: Subarray index
//                 merge(a, lo , lo+sz-1, Math.min(lo+sz+sz-1, N-1));
//     }
// }

// public class Quick {
//     public static void sort(Comparable[] a) {
//         StdRandom.shuffle(a); // Eliminate dependence on input.
//         sort(a, 0, a.length - 1);
//     }

//     private static void sort(Comparable[] a, int lo, int hi) {
//         if (hi<=lo) return;
//         int j = partition(a, lo, hi);
//         sort(a, lo, j-1); // Sort left part a[lo...j-1].
//         sort(a, j+1, hi); // Sort right part a[j+1...hi].
//     }

//     private static int partition(Comparable[] a, int lo, int hi) {
//         // Partition into a[lo...i-1], a[i], a[i+1...hi]
//         int i = lo, j = hi + 1;
//         Comparable v = a[lo];
//         while (true) {
//             // Scan right to left, check for scan complete, and exchange.
//             while (less(a[++i], v)) if (i == hi) break;
//             while (less(v, a[--j])) if (j == lo) break;
//             if (i >= j) break;
//             exch(a, i, j); 
//         }
//         exch(a, lo, j);
//         return j;
//     }
// }

// public class Quick3way {
//     private static void sort(Comparable[] a, int lo, int hi} {
//         if (hi <= lo) return;
//         int lt = lo, i = lo + 1, gt = hi;
//         Comparable v = a[lo];
//         while (i <= gt) {
//             itn cmp = a[i].compareTo(v);
//             if (cmp < 0) exch(a, lt++, i++);
//             else if (cmp > 0) exch(a, i, gt--);
//             else i++;
//         }
//         sort(a, lo, lt - 1);
//         sort(a, gt + 1, hi);
//     }
// }

