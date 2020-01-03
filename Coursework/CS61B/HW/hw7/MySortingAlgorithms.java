import java.util.Arrays;

/**
 * Class containing all the sorting algorithms from 61B to date.
 *
 * You may add any number instance variables and instance methods
 * to your Sorting Algorithm classes.
 *
 * You may also override the empty no-argument constructor, but please
 * only use the no-argument constructor for each of the Sorting
 * Algorithms, as that is what will be used for testing.
 *
 * Feel free to use any resources out there to write each sort,
 * including existing implementations on the web or from DSIJ.
 *
 * All implementations except Distribution Sort adopted from Algorithms,
 * a textbook by Kevin Wayne and Bob Sedgewick. Their code does not
 * obey our style conventions.
 */
public class MySortingAlgorithms {

    /**
     * Java's Sorting Algorithm. Java uses Quicksort for ints.
     */
    public static class JavaSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            Arrays.sort(array, 0, k);
        }

        @Override
        public String toString() {
            return "Built-In Sort (uses quicksort for ints)";
        }
    }

    /** Insertion sorts the provided data. */
    public static class InsertionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int val;
            int revIndex;

            for (int index = 0; index < k; index += 1) {
                val = array[index];
                revIndex = index - 1;

                while (revIndex >= 0 && array[revIndex] > val) {
                    array[revIndex + 1] = array[revIndex];
                    revIndex -= 1;
                }
                array[revIndex + 1] = val;
            }   // FIXME
        }

        @Override
        public String toString() {
            return "Insertion Sort";
        }
    }

    /**
     * Selection Sort for small K should be more efficient
     * than for larger K. You do not need to use a heap,
     * though if you want an extra challenge, feel free to
     * implement a heap based selection sort (i.e. heapsort).
     */
    public static class SelectionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            for (int index = 0; index < k - 1; index += 1) {
                int minVal = array[index];
                int n = 0;

                for (int nextIndex = index + 1; nextIndex < k; nextIndex += 1) {
                    if (array[nextIndex] < minVal) {
                        int temp = array[nextIndex];
                        minVal = temp;
                        n = nextIndex;
                    }
                }
                if (minVal != array[index]) {
                    int temp = array[index];
                    array[index] = minVal;
                    array[n] = temp;
                }
            }// FIXME
        }

        @Override
        public String toString() {
            return "Selection Sort";
        }
    }

    /** Your mergesort implementation. An iterative merge
      * method is easier to write than a recursive merge method.
      * Note: I'm only talking about the merge operation here,
      * not the entire algorithm, which is easier to do recursively.
      */
    public static class MergeSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            merge(array, 0,  k - 1);
            // FIXME
        }

        public void merge(int[] array, int ls, int re) {
            if (ls >= re) {
                return;
            }
            int m = (ls + re) / 2;
            merge(array, ls, m);
            merge(array, m + 1, re);
            halves(array, ls, re);
        }

        public void halves(int[] array, int ls, int re) {
            int[] result = new int[array.length];
            int le = (ls + re) / 2;
            int rs = le + 1;
            int size = re - ls + 1;

            int l = ls;
            int r = rs;
            int index = ls;

            while (l <= le && r <= re) {
                if (array[l] <= array[r]) {
                    result[index] = array[l];
                    index += 1;
                    l += 1;
                } else {
                    result[index] = array[r];
                    index += 1;
                    r += 1;
                }
            }
            System.arraycopy(array, l, result, index, le - l + 1);
            System.arraycopy(array, r, result, index, re - r + 1);
            System.arraycopy(result, ls, array, ls, size);
        }

        // may want to add additional methods

        @Override
        public String toString() {
            return "Merge Sort";
        }
    }

    /**
     * Your Distribution Sort implementation.
     * You should create a count array that is the
     * same size as the value of the max digit in the array.
     */
    public static class DistributionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME: to be implemented
        }

        // may want to add additional methods

        @Override
        public String toString() {
            return "Distribution Sort";
        }
    }

    /** Your Heapsort implementation.
     */
    public static class HeapSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "Heap Sort";
        }
    }

    /** Your Quicksort implementation.
     */
    public static class QuickSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "Quicksort";
        }
    }

    /* For radix sorts, treat the integers as strings of x-bit numbers.  For
     * example, if you take x to be 2, then the least significant digit of
     * 25 (= 11001 in binary) would be 1 (01), the next least would be 2 (10)
     * and the third least would be 1.  The rest would be 0.  You can even take
     * x to be 1 and sort one bit at a time.  It might be interesting to see
     * how the times compare for various values of x. */

    /**
     * LSD Sort implementation.
     */
    public static class LSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "LSD Sort";
        }
    }

    /**
     * MSD Sort implementation.
     */
    public static class MSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "MSD Sort";
        }
    }

    /** Exchange A[I] and A[J]. */
    private static void swap(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
