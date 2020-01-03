package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  Daniel Martin
 */
class Arrays {
    public static void main(String[] args){
        int[] A = {1,3,7,5,4,6,9,10};
        System.out.println(naturalRuns(A));
    }

    /* C. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        if(A == null){
            return B;
        } else if (B == null) {
            return A;
        } else {
            int[] result = new int[A.length + B.length];
            System.arraycopy(A, 0, result, 0, A.length);
            System.arraycopy(B, 0, result, A.length, B.length);
            return result;
        }
    }

    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        assert(start >= 0);
        assert(len >= 0);
        if(len == 0){
            return A;
        } else {
            int[] rightHalf = Utils.subarray(A, 0, start);
            int[] leftHalf = Utils.subarray(A, start + len, A.length-start-len);
            return catenate(rightHalf, leftHalf);
        }
    }

    /* E. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        if(A.length == 0){
          return new int[0][];
        }

        int count = 1;
        int[][] result;

        for(int j = 1; j < A.length; j++){
            if(A[j] <= A[j-1]){
                count +=1 ;
            }
        }

        result = new int[count][];
        int index = 0;
        int place = 0;

        for(int j = 1; j < A.length; j++){
            if(A[j] <= A[j-1]){
                result[index] = Utils.subarray(A, place, j - place);
                place = j;
                index += 1;
            }
        }

        if (index != result.length) {
            result[index] = Utils.subarray(A, place, A.length - place);
        }
        return result;
    }
}
