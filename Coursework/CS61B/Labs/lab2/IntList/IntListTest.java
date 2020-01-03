import static org.junit.Assert.*;
import org.junit.Test;

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

    @Test
    public void testdSquareList() {
        IntList L = IntList.list(1, 2, 3);
        IntList.dSquareList(L);
        assertEquals(IntList.list(1, 4, 9), L);
    }

    /*  Do not use the "new" keyword in your tests. You can create
     *  lists using the handy IntList.list method.
     *
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with
     *  IntList empty = IntList.list().
     */
    
    //TODO:  Create testSquareListRecursive()
    @Test
    public void testSquareListRecursive(){
        //Tests correctness
        IntList L = IntList.list(1,2,3,4,5);
        IntList new1 = IntList.squareListRecursive(L);
        assertEquals(IntList.list(1,2,3,4,5), L);
        assertEquals(IntList.list(1,4,9,16,25), new1);

        //Tests an empty list
        IntList empty = IntList.list(); //empty linked list
        IntList.squareListRecursive(empty);
        assertEquals(IntList.list(), empty);

        //Tests a single element list
        IntList single = IntList.list(1);
        IntList new2 = IntList.squareListRecursive(single);
        assertEquals(IntList.list(1), single);
        assertEquals(IntList.list(1), new2);
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(IntListTest.class));
    }
}
