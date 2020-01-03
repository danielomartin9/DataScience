package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a reflector in the enigma.
 *
 *  @author Daniel Martin
 */
class Reflector extends FixedRotor {

    /** A non-moving rotor named NAME whose permutation at the 0 setting
     * is PERM. */
    Reflector(String name, Permutation perm) {
        super(name, perm);
    }

    /** Cannot call convertBackward on Reflector. */
    @Override
    int convertBackward(int e) {
        throw error("Reflectors do not invert!");
    }

    @Override
    void set(int posn) {
        if (posn != 0) {
            throw error("reflector has only one position");
        }
    }

}
