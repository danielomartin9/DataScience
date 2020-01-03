package lists;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A couple of tests to verify the correctness of the
 * naturalRuns method.  Tests cover a generic list input,
 * an ascending list, and a descending list.
 *
 * Daniel Martin
 */

public class ListsTest {
    @Test
    public void naturalRunTest(){
        IntList L = IntList.list(1,3,7,5,4,6,9,10,10,11);
        IntListList result = Lists.naturalRuns(L);
        int[][] A = {{1,3,7}, {5}, {4,6,9,10}, {10,11}};
        IntListList expected = IntListList.list(A);
        assertEquals(expected, result);

        IntList L1 = IntList.list(1,2,1,1,4,5,6,4);
        IntListList result1 = Lists.naturalRuns(L1);
        int[][] A1 = {{1,2}, {1}, {1,4,5,6}, {4}};
        IntListList expected1 = IntListList.list(A1);
        assertEquals(expected1, result1);

        IntList L2 = IntList.list(1,2,3,4);
        IntListList result2 = Lists.naturalRuns(L2);
        int[][] A2 = {{1,2,3,4}};
        IntListList expected2 = IntListList.list(A2);
        assertEquals(expected2, result2);

        IntList L3 = IntList.list(5,4,3,2,1);
        IntListList result3 = Lists.naturalRuns(L3);
        int[][] A3 = {{5}, {4}, {3}, {2}, {1}};
        IntListList expected3 = IntListList.list(A3);
        assertEquals(expected3, result3);
    }

    // It might initially seem daunting to try to set up
    // IntListList expected.
    //
    // There is an easy way to get the IntListList that you want in just
    // few lines of code! Make note of the IntListList.list method that
    // takes as input a 2D array.

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
