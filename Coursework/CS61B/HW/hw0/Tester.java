import org.junit.Test;
import static org.junit.Assert.*;

import ucb.junit.textui;

/** Tests for hw0. 
 *  @author Daniel Martin
 */
public class Tester {

    /* Feel free to add your own tests.  For now, you can just follow
     * the pattern you see here.  We'll look into the details of JUnit
     * testing later.
     *
     * To actually run the tests, just use
     *      java Tester 
     * (after first compiling your files).
     *
     * DON'T put your HW0 solutions here!  Put them in a separate
     * class and figure out how to call them from here.  You'll have
     * to modify the calls to max, threeSum, and threeSumDistinct to
     * get them to work, but it's all good practice! */

    @Test
    public void maxTester() {
        // Change call to max to make this call yours.
        assertEquals(14, max.max_for(new int[] { 0, -5, 2, 14, 10 }));
        assertEquals(25, max.max_while(new int[] { 0, -12, 25, 1, 10 }));
        assertEquals(130, max.max_for(new int[] { 30, -1, 25, 16, 130 }));
    }

    @Test
    public void threeSumTester() {
        // Change call to threeSum to make this call yours.
        assertTrue(threeSum.threeSum(new int[] { -6, 3, 10, 200 }));
        assertTrue(threeSum.threeSum(new int[] { 5, 1, 0, 3, 6}));
        assertFalse(threeSum.threeSum(new int[] { -6, 2, 5, 200 }));
    }

    @Test
    public void threeSumDistinctTester() {
        // Change call to threeSumDistinct to make this call yours.
        assertFalse(threeSumDistinct.threeSumDistinct(new int[] { -6, 3, 10, 200 }));
        assertFalse(threeSumDistinct.threeSumDistinct(new int[] { 5, 1, 0, 3, 6 }));
        assertTrue(threeSumDistinct.threeSumDistinct(new int[] { 8, 2, -1, -1, 15 }));
        assertFalse(threeSumDistinct.threeSumDistinct(new int[] { 8, 2, -1, 15 }));
    }

    public static void main(String[] unused) {
        textui.runClasses(Tester.class);
    }

}
