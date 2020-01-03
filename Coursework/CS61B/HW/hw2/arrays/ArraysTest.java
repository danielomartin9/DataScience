package arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/** Tests that verify that catenate, remove, and
 * naturalRuns function properly.  The catenate tests
 * include concatenating 2 arbitrary lists and one null list.
 * The remove tests include removing lists at arbitrary starts
 * and lengths, including a length of 0.  The naturalRuns test
 * include a generic list, an ascending list, and a descending list.
 *
 *  Daniel Martin
 */

public class ArraysTest {
    @Test
    public void catenateTest(){
        int[] A = {1,2,3};
        int[] B = {4,5,6};
        int[] result = Arrays.catenate(A,B);
        int[] expected = {1,2,3,4,5,6};
        assertArrayEquals(expected, result);

        int[] A1 = {};
        int[] B1 = {4,5,6};
        int[] result1 = Arrays.catenate(A1, B1);
        int[] expected1 = {4,5,6};
        assertArrayEquals(expected1, result1);

        int[] A2 = {1,2,3};
        int[] B2 = {};
        int[] result2 = Arrays.catenate(A2, B2);
        int[] expected2 = {1,2,3};
        assertArrayEquals(expected2, result2);
    }

    @Test
    public void removeTest(){
        int[] A = {1,2,3,4,5};
        int[] result = Arrays.remove(A,2,2);
        int[] expected = {1,2,5};
        assertArrayEquals(expected, result);

        int[] A1 = {1,2,3,4,5};
        int[] result1 = Arrays.remove(A, 2, 0);
        int[] expected1 = {1,2,3,4,5};
        assertArrayEquals(expected1, result1);

        int[] A2 = {1,2,3,4,5,6,7};
        int[] result2 = Arrays.remove(A2, 4, 1);
        int[] expected2 = {1,2,3,4,6,7};
        assertArrayEquals(expected2, result2);
    }

    @Test
    public void naturalRunsTest(){
        int[] A = {1, 3, 7, 5, 4, 6, 9, 10};
        int[][] result = Arrays.naturalRuns(A);
        int[][] expected = {{1,3,7}, {5}, {4,6,9,10}};
        assertArrayEquals(expected, result);

        int[] A1 = {1,2,3};
        int[][] result1 = Arrays.naturalRuns(A1);
        int[][] expected1 = {{1,2,3}};
        assertArrayEquals(expected1, result1);

        int[] A2 = {5,4,3,2,1};
        int[][] result2 = Arrays.naturalRuns(A2);
        int[][] expected2 = {{5}, {4}, {3}, {2}, {1}};
        assertArrayEquals(expected2, result2);

    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
