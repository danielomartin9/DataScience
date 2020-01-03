package enigma;

import java.util.ArrayList;
import java.util.HashMap;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *
 *  @author Daniel Martin
 */
class Permutation {

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;
    /** Hashmap to store  associated values. */
    private HashMap<Integer, Integer> alpha =  new HashMap<>();
    /** Hashmap to store the inverse associated values. */
    private HashMap<Integer, Integer> alphaInverse = new HashMap<>();
    /** ArrayList to store permutation. */
    private ArrayList<String> cleanCycle = new ArrayList<>();

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;

        for (int i = 0; i < _alphabet.size(); i++) {
            alpha.put(i, i);
            alphaInverse.put(i, i);
        }

        if (cycles.length() != 0) {
            cycles = cycles.replaceAll("[(]", "");
            cycles = cycles.replaceAll("[)]", " ");
            String[] finalCycle = cycles.split("\\s+");

            for (int i = 0; i < finalCycle.length; i++) {
                cleanCycle.add(i, finalCycle[i]);
            }

            for (String val : cleanCycle) {
                for (int i = 0; i < val.length() - 1; i++) {
                    alpha.put(_alphabet.toInt(val.charAt(i)),
                            _alphabet.toInt(val.charAt(i + 1)));
                }
                alpha.put(_alphabet.toInt(val.charAt(val.length() - 1)),
                        _alphabet.toInt(val.charAt(0)));
            }

            for (String val : cleanCycle) {
                for (int i = val.length() - 1; i > 0; i--) {
                    alphaInverse.put(_alphabet.toInt(val.charAt(i)),
                            _alphabet.toInt(val.charAt(i - 1)));
                }
                alphaInverse.put(_alphabet.toInt(val.charAt(0)),
                        _alphabet.toInt(val.charAt(val.length() - 1)));
            }
        }
    }

    /** Get Alpha Hashmap, used for testing.
     * @return alpha
     * */
    public HashMap<Integer, Integer> getAlphaHash() {
        return alpha;
    }

    /** Get Inverse Alpha Hashmap, used for testing.
     * @return alphaInverse
     * */
    public HashMap<Integer, Integer> getAlphaInverseHash() {
        return alphaInverse;
    }

    /** Get the contents of the Permutation, used for testing.
     * @return cleanCycle
     * */
    public ArrayList<String> getCleanCycle() {
        return cleanCycle;
    }

    /** Set Alphabet, used for testing.
     * @param first first
     * @param last last
     * @return test
     * */
    public Alphabet setAlphabet(char first, char last) {
        Alphabet test = new CharacterRange(first, last);
        return test;
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        cycle = cycle.replaceAll("[(]", "");
        String[] cleanCycles  = cycle.replaceAll("[)]", " ").split(" ");
        for (int i = 0; i < cleanCycles.length; i++) {
            cleanCycle.add(i, cleanCycles[i]);
        }
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        int x = wrap(p);
        return alpha.get(x);
    }

    /** Return the result of applying the inverse of this permutation
     *  to C modulo the alphabet size. */
    int invert(int c) {
        int x = wrap(c);
        return alphaInverse.get(x);
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        int x = _alphabet.toInt(p);
        int val = permute(x);
        return _alphabet.toChar(val);
    }

    /** Return the result of applying the inverse of this permutation to C. */
    int invert(char c) {
        int x  = _alphabet.toInt(c);
        int val = alphaInverse.get(x);
        return _alphabet.toChar(val);
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (int key : alpha.keySet()) {
            if (alpha.get(key) == key) {
                return false;
            }
        }
        return true;
    }
}
