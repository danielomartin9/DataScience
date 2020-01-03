package enigma;

import java.util.ArrayList;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *
 *  @author Daniel Martin
 */
class MovingRotor extends Rotor {

    /** An ArrayList to store the notches for MovingRotor. */
    private ArrayList<Character> _notches = new ArrayList<>();

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initially in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        for (int i = 0; i < notches.length(); i++) {
            _notches.add(i, notches.charAt(i));
        }
    }

    @Override
    /** Return true iff I have a ratchet and can move. */
    boolean rotates() {
        return true;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    @Override
    void advance() {
        if (setting() == alphabet().size() - 1) {
            set(0);
        } else {
            set(setting() + 1);
        }
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    @Override
    boolean atNotch() {
        return _notches.contains(alphabet().toChar(setting()));
    }
}
