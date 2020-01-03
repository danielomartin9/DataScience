package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import java.util.HashMap;

import static org.junit.Assert.*;

import static enigma.TestUtils.*;


/** The suite of all JUnit tests for the Permutation class.
 *
 *  @author Daniel Martin
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */
    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }

    @Test
    public void constructorTests() {
        Permutation cycle  = new Permutation("(ABCD)", UPPER);
        HashMap<Integer, Integer> forward = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            forward.put(i, i + 1);
        }
        forward.put(3, 0);
        for (int i = 4; i < 26; i++) {
            forward.put(i, i);
        }
        assertEquals(cycle.getAlphaHash(), forward);

        HashMap<Integer, Integer> backward = new HashMap<>();
        for (int i = 3; i > 0; i--) {
            backward.put(i, i - 1);
        }
        backward.put(0, 3);
        for (int i = 25; i > 3; i--) {
            backward.put(i, i);
        }
        assertEquals(cycle.getAlphaInverseHash(), backward);

        Permutation identity = new Permutation("", UPPER);
        HashMap<Integer, Integer> forward1 = new HashMap<>();
        HashMap<Integer, Integer> backward1 = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            forward1.put(i, i);
            backward1.put(i, i);
        }
        assertEquals(identity.getAlphaHash(), forward1);
        assertEquals(identity.getAlphaInverseHash(), backward1);
    }

    @Test
    public void permuteIntTests() {
        Permutation test1 = new Permutation("", UPPER);
        assertTrue(test1.permute(0) == 0);
        assertTrue(test1.permute(25) == 25);

        Permutation test2 = new Permutation("(ABC)", UPPER);
        assertTrue(test2.permute(0) == 1);
        assertTrue(test2.permute(2) == 0);
        assertTrue(test2.permute(25) == 25);
    }

    @Test
    public void permuteCharTests() {
        Permutation test1 = new Permutation("", UPPER);
        assertTrue(test1.permute('A') == 'A');
        assertTrue(test1.permute('Z') == 'Z');

        Permutation test2 = new Permutation("(ABC)", UPPER);
        assertTrue(test2.permute('A') == 'B');
        assertTrue(test2.permute('C') == 'A');
        assertTrue(test2.permute('Z') == 'Z');
    }

    @Test
    public void invertIntTests() {
        Permutation test1 = new Permutation("", UPPER);
        assertTrue(test1.invert(0) == 0);
        assertTrue(test1.invert(25) == 25);

        Permutation test2 = new Permutation("(ABC)", UPPER);
        assertTrue(test2.invert(2) == 1);
        assertTrue(test2.invert(0) == 2);
        assertTrue(test2.invert(25) == 25);
    }

    @Test
    public void invertCharTests() {
        Permutation test1 = new Permutation("", UPPER);
        assertTrue(test1.invert('A') == 'A');
        assertTrue(test1.invert('Z') == 'Z');

        Permutation test2 = new Permutation("(ABC)", UPPER);
        assertTrue(test2.invert('C') == 'B');
        assertTrue(test2.invert('A') == 'C');
        assertTrue(test2.invert('Z') == 'Z');
    }

    @Test
    public void sizeTests() {
        Permutation test1 = new
                Permutation("ABCDEFGHIJKLMNOPQRSTUVWXYZZ", UPPER);
        Alphabet alphabet1 = test1.setAlphabet('A', 'B');
        assertTrue(alphabet1.size() == 2);

        Permutation test2 = new Permutation("", UPPER);
        Alphabet alphabet2 = test2.setAlphabet('A', 'Z');
        assertTrue(alphabet2.size() == 26);

        Permutation test3 =  new Permutation("", UPPER);
        Alphabet alphabet3 = test3.setAlphabet('A', 'A');
        assertTrue(alphabet3.size() == 1);
    }

    @Test
    public void derangementTests() {
        Permutation identity =  new Permutation("", UPPER);
        assertFalse(identity.derangement());

        Permutation test1 = new
                Permutation("(ABCDEFGHIJKLMNOPQRSTUVWXYZ)", UPPER);
        assertTrue(test1.derangement());

        Permutation test2 = new
                Permutation("(ABC) (DEG) (HVW)", UPPER);
        assertFalse(test2.derangement());

        Permutation test3 = new
                Permutation("(A) (BCDEFGHIJKLMNOPQRSTUVWXYZ)", UPPER);
        assertFalse(test3.derangement());
    }

}
