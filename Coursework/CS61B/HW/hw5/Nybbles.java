/** Represents an array of integers each in the range -8..7.
 *  Such integers may be represented in 4 bits (called nybbles).
 *  @author Daniel Martin
 */
public class Nybbles {

    /** Maximum positive value of a Nybble. */
    public static final int MAX_VALUE = 7;

    /** Return an array of size N. */
    public Nybbles(int N) {
        // DON'T CHANGE THIS.
        _data = new int[(N + 7) / 8];
        _n = N;
    }

    /** Return the size of THIS. */
    public int size() {
        return _n;
    }

    /** Return the Kth integer in THIS array, numbering from 0.
     *  Assumes 0 <= K < N. */
    public int get(int k) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else {
            int index = k / 8;
            int sIndex = k % 8;
            int element = _data[index] >>> sIndex * 4;

            element &= 0b1111;
            if (element >= 8) {
                return element - 16;
            } else {
                return element;
            }
        }
    }

    /** Set the Kth integer in THIS array to VAL.  Assumes
     *  0 <= K < N and -8 <= VAL < 8. */
    public void set(int k, int val) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else if (val < (-MAX_VALUE - 1) || val > MAX_VALUE) {
            throw new IllegalArgumentException();
        } else {
            int index = k / 8;
            int x = _data[index];
            int clean = (1 + 2 + 4 + 8) << (4 * (k % 8));

            x &= ~clean;

            int bit1 = (val & 1) << (4 * (k % 8));
            int bit2 = (val & 2) << (4 * (k % 8));
            int bit3 = (val & 4) << (4 * (k % 8));
            int bit4 = (1 << (3 + 4 * (k % 8))) & val;

            if (val == -8) {
                bit1 = 0;
                bit2 = 0;
                bit3 = 0;
            } else {
                bit4 = (1 << (3 + 4 * (k % 8)));
            }

            x |= bit1; x|= bit2; x|= bit3; x|= bit4;
            _data[index] = x;
        }
    }

    // DON'T CHANGE OR ADD TO THESE.
    /** Size of current array (in nybbles). */
    private int _n;
    /** The array data, packed 8 nybbles to an int. */
    private int[] _data;
}
