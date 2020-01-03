/** HW #7, Two-sum problem.
 * @author Daniel Martin
 */
public class Sum {

    /** Returns true iff A[i]+B[j] = M for some i and j. */
    public static boolean sumsTo(int[] A, int[] B, int m) {

        for (int i = 0; i < A.length; i += 1) {
            for (int j = 0; j < A.length; j += 1) {
                if (A[i] + B[j] == m) {
                    return true;
                }
            }
        }
        return false;  // REPLACE WITH YOUR ANSWER
    }

}
