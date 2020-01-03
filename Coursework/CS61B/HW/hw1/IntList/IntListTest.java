import static org.junit.Assert.*;

import afu.org.checkerframework.checker.igj.qual.I;
import org.junit.Test;

import javax.print.DocFlavor;

public class IntListTest {

    /** Sample test that verifies correctness of the IntList.list static
     *  method. The main point of this is to convince you that
     *  assertEquals knows how to handle IntLists just fine.
     */
    @Test
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    /** Do not use the new keyword in your tests. You can create
     *  lists using the handy IntList.list method.
     *
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with
     *  IntList empty = IntList.list().
     *
     *  Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     *  Anything can happen to A.
     */
    @Test
    public void testDcatenate() {
        // Basic test case
        IntList A1 = IntList.list(1,2,3);
        IntList B1 = IntList.list(4,5,6);
        IntList result = IntList.list(1,2,3,4,5,6);
        assertEquals(result, IntList.dcatenate(A1, B1));

        // B is a null IntList
        IntList A2 = IntList.list(1);
        IntList empty1 = IntList.list();
        IntList result1 = IntList.list(1);
        assertEquals(result1, IntList.dcatenate(A2, empty1));

        // A is a null IntList
        IntList empty2 = IntList.list();
        IntList B2 = IntList.list(1);
        IntList result2 = IntList.list(1);
        assertEquals(result2, IntList.dcatenate(empty2, B2));

        // A and B .tail is null
        IntList A3 = IntList.list(1);
        IntList B3 = IntList.list(2);
        IntList result3 = IntList.list(1,2);
        assertEquals(result3, IntList.dcatenate(A3, B3));
    }

    /** Tests that subtail works properly. Again, don't use new.
     *
     *  Make sure to test that subtail does not modify the list.
     */
    @Test
    public void testSubtail() {
        // Basic test case
        IntList A = IntList.list(10,11,12);
        IntList result = IntList.list(11, 12);
        assertEquals(result, IntList.subTail(A, 1));
        assertEquals(A, A);

        // A is an empty IntList, expect to return null
        IntList empty = IntList.list();
        IntList result1 = IntList.list();
        assertEquals(result1, IntList.subTail(empty, 3));
        assertEquals(empty, empty);

        // Start is 0, expect to return A2
        IntList A2 = IntList.list(1,2,3);
        IntList result2 = IntList.list(1,2,3);
        assertEquals(result2, IntList.subTail(A2, 0));
        assertEquals(A2, A2);

    }

    /** Tests that sublist works properly. Again, don't use new.
     *
     *  Make sure to test that sublist does not modify the list.
     */
    @Test
    public void testSublist() {
        //Basic test case
        IntList A = IntList.list(1,2,3,4,5);
        IntList result = IntList.list(2,3,4);
        assertEquals(result, IntList.sublist(A, 1, 3));

        //A is an empty list
        IntList empty = IntList.list();
        IntList result1 = IntList.list();
        assertEquals(result1, IntList.sublist(empty, 2, 4));

        //Start is 0
        IntList A1 = IntList.list(1,2,3,4,5);
        IntList result2 = IntList.list(1,2,3,4,5);
        assertEquals(result2, IntList.sublist(A1, 0, 5));

        //Len is 0
        IntList A2 = IntList.list(1,2,3,4,5);
        IntList result3 = IntList.list();
        assertEquals(result3, IntList.sublist(A2, 1, 0));
    }

    /** Tests that dSublist works properly. Again, don't use new.
     *
     *  As with testDcatenate, it is not safe to assume that list passed
     *  to dSublist is the same after any call to dSublist
     */
    @Test
    public void testDsublist() {
        // Basic test case
        IntList A = IntList.list(1,2,3,4,5);
        IntList result = IntList.list(2,3,4);
        assertEquals(result, IntList.dsublist(A, 1, 3));
        assertFalse(A == IntList.dsublist(A, 1, 3));

        // A is an empty IntList
        IntList empty = IntList.list();
        IntList result1 = IntList.list();
        assertEquals(result1, IntList.dsublist(empty, 3, 2));

        // Start is 0
        IntList A1 = IntList.list(1,2,2,3);
        IntList result2 = IntList.list(1,2);
        assertEquals(result2, IntList.dsublist(A1, 0, 2));

        // Len is 0
        IntList A2 = IntList.list(1,2,3,4);
        IntList result3 = IntList.list();
        assertEquals(result3, IntList.dsublist(A2, 3, 0));

        // Len is greater than remaining elements in IntList
        IntList A3 = IntList.list(1,2,3);
        IntList result4 = IntList.list(3);
        assertEquals(result4, IntList.dsublist(A3, 2, 4));
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(IntListTest.class));
    }
}
