package enigma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.HashSet;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Daniel Martin
 */
class Machine {

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Int variable to store the number of rotors. */
    private  int _numRotors;

    /** Int variable to store the number of pawls. */
    private int _numPawls;

    /** Hashmap of available rotors. */
    private HashMap<String, Rotor> _allRotors = new HashMap<>();

    /** ArrayList of currently used rotors. */
    private ArrayList<Rotor> machineRotor;

    /** Permutation variable representing the plugboard. */
    private Permutation plug;

    /** ArrayList to store rotors to advance. */
    private HashSet<Rotor> advanceRotors =  new HashSet<>();

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _numPawls = pawls;
        plug = new Permutation("", alpha);

        for (Rotor r: allRotors) {
            _allRotors.put(r.name().toUpperCase(), r);
        }
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _numPawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        HashSet<String> uniqueRotors = new HashSet<>();

        if (rotors.length != _numRotors) {
            throw new EnigmaException("Incorrect non-matching"
                    + " amount of Rotors.");
        }


        if (!_allRotors.get(rotors[0]).getClass()
                .equals(Reflector.class)) {
            throw new EnigmaException("Invalid first Rotor, "
                    + "must be a Reflector");
        }

        if (!_allRotors.get(rotors[0]).permutation().derangement()) {
            throw new EnigmaException("Invalid Reflector, "
                    + "Reflectors cannot map to itsself");
        }

        for (int i = 0; i < rotors.length; i++) {
            uniqueRotors.add(rotors[i].toUpperCase());
        }
        if (uniqueRotors.size() != rotors.length) {
            throw new EnigmaException("Invalid Rotors, "
                    + "cannot contain duplicate rotors");
        }

        for (int i = 0; i < rotors.length; i++) {
            if (!_allRotors.containsKey(rotors[i])) {
                throw new EnigmaException("Invalid Rotors, "
                        + "Rotors are misnamed");
            }
        }

        machineRotor = new ArrayList<Rotor>();
        for (int i = 0; i < rotors.length; i++) {
            machineRotor.add(_allRotors.get(rotors[i]));
        }

        int movRotors = 0;
        for (int i = machineRotor.size() - 1; i > 0; i--) {
            if (machineRotor.get(i).getClass().equals(MovingRotor.class)) {
                movRotors += 1;
            }
        }
        if (movRotors != _numPawls) {
            throw new EnigmaException("Invalid amount of MovingRotors");
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 upper-case letters. The first letter refers to the
     *  leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {

        if (setting.length() != numRotors() - 1) {
            throw new EnigmaException("Invalid length of setting");
        }

        for (int i = 0; i < setting.length(); i++) {
            if (!_alphabet.contains(setting.toUpperCase().charAt(i))) {
                throw new EnigmaException("Invalid setting, "
                        + "setting characters not in alphabet");
            } else {
                machineRotor.get(i + 1).set(setting.toUpperCase().charAt(i));
            }
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        for (int i = 0; i < plugboard.getCleanCycle().size(); i++) {
            if (plugboard.getCleanCycle().get(i).length() != 2) {
                throw new EnigmaException("Invalid plugboard, "
                        + "plugboard can only contain pairs");
            }
        }

        plug = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        int p = plug.permute(c);
        advanceRotors = new HashSet<>();

        advanceRotors.add(machineRotor.get(_numRotors - 1));

        for (int i = _numRotors - _numPawls; i < _numRotors - 1; i++) {
            if (machineRotor.get(i + 1).atNotch()) {
                advanceRotors.add(machineRotor.get(i));
                advanceRotors.add((machineRotor.get(i + 1)));
            }
        }

        for (Rotor r: advanceRotors) {
            r.advance();
        }

        for (int i = _numRotors - 1; i > -1; i--) {
            p = machineRotor.get(i).convertForward(p);
        }

        for (int i = 1; i < _numRotors; i++) {
            p = machineRotor.get(i).convertBackward(p);
        }

        p = plug.permute(p);
        return p;
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        Character[] result = new Character[msg.length()];
        String fin = "";
        String cleanMsg = msg.replaceAll("[ ]", "");

        for (int i = 0; i < cleanMsg.length(); i++) {
            result[i] = _alphabet.toChar(convertChar(cleanMsg.charAt(i)));
            fin = fin + result[i];
        }
        return fin;
    }

    /** Method to account for input of a char.
     * @param x x
     * @return result
     * */
    int convertChar(char x) {
        char upper = Character.toUpperCase(x);
        int intResult = _alphabet.toInt(upper);
        int result = convert(intResult);
        return result;
    }
}
