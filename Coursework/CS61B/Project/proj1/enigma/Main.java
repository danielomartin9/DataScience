package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Daniel Martin
 */
public final class Main {

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;

    /** Int variable to store the number of rotors. */
    private int numRotors;

    /** Int variable to store the number of pawls. */
    private int numPawls;

    /** Hashmap of available rotors. */
    private HashSet<Rotor> allRotors = new HashSet<>();


    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine myMachine = readConfig();
        String lineOne = _input.nextLine();

        if (lineOne.startsWith("*")) {
            setUp(myMachine, lineOne.substring(1).trim());
        } else {
            throw new EnigmaException("Invalid setting");
        }

        while (_input.hasNextLine()) {
            String lineNext = _input.nextLine();
            if (lineNext.startsWith("*")) {
                setUp(myMachine, lineNext.substring(1).trim());
            } else if (lineNext.equals("")) {
                _output.println();
            } else {
                printMessageLine(myMachine.convert(lineNext));
            }
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            _alphabet = new CharacterRange('A', 'Z');
            int counter = 0;
            while (_config.hasNext()) {
                if (counter == 0) {
                    String line = _config.nextLine().trim();
                    line = line.replaceAll("[-]", " ");
                    String[] alphaLine = line.split("\\s+");
                    if (alphaLine.length != 2) {
                        throw new EnigmaException("Invalid Alphabet, "
                                + "incorrect length of alphabet");
                    }

                    if (!_alphabet.contains(alphaLine[0].charAt(0))
                            || !_alphabet.contains(alphaLine[1].charAt(0))) {
                        throw new EnigmaException("Invalid Alphabet,"
                                + " characters not contained in alphabet");
                    }

                    if (alphaLine[0].charAt(0) > alphaLine[1].charAt(0)) {
                        throw new EnigmaException("Invalid Alphabet, first"
                                + "character must be less than last character");
                    }

                    char first = alphaLine[0].charAt(0);
                    char last = alphaLine[1].charAt(0);
                    _alphabet = new CharacterRange(first, last);

                } else if (counter == 1) {
                    String number = _config.nextLine().trim();
                    String[] numberLine = number.split("\\s+");
                    int len = numberLine.length;
                    if (len != 2) {
                        throw new EnigmaException("Invalid Rotors, incorrect"
                                + " inputs for number of Rotors");
                    }

                    if (numberLine[0].charAt(0)
                            < numberLine[1].charAt(0)) {
                        throw new EnigmaException("Invalid Rotors, cannot have"
                                + " greater number of Pawls than total Rotors");
                    }

                    numRotors = Integer.parseInt(numberLine[0]);
                    numPawls = Integer.parseInt(numberLine[1]);
                } else if (counter >= 2) {
                    if (_config.hasNext()) {
                        allRotors.add(readRotor());
                    }
                }
                counter += 1;
            }
            return new Machine(_alphabet, numRotors, numPawls, allRotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            String cycle = "";
            String rotorName = _config.next().toUpperCase();
            String rotorSpec = _config.next().toUpperCase();
            char rotorType = rotorSpec.charAt(0);

            while (_config.hasNext(Pattern.compile("\\([A-Z]+"
                    + "\\)*\\(*[A-Z]*\\)"))) {
                cycle += _config.next();
            }

            Permutation perma = new Permutation(cycle, _alphabet);

            if (rotorType == 'M') {
                String notches = rotorSpec.substring(1);
                return new MovingRotor(rotorName, perma, notches);
            } else if (rotorType == 'N') {
                return new FixedRotor(rotorName, perma);
            } else if (rotorType == 'R') {
                return new Reflector(rotorName, perma);
            } else {
                throw new EnigmaException("Invalid Rotor must be M,N, or R");
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        String[] line  = settings.split("\\s+");
        String[] rotors = new String[numRotors];

        for (int i = 0; i < numRotors; i++) {
            rotors[i] = line[i];
        }

        M.insertRotors(rotors);
        String set = line[numRotors];
        if (set.length() != M.numRotors() - 1) {
            throw new EnigmaException("Invalid amount of setting");
        } else {
            M.setRotors(set);
        }

        if (line.length > numRotors + 1) {
            String cycle = "";

            int counter = numRotors + 1;

            while (counter < line.length) {
                cycle += line[counter];
                counter += 1;
            }

            Permutation perm = new Permutation(cycle, _alphabet);
            M.setPlugboard(perm);
        }
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        String[] splitMsg = msg.split("(?<=\\G.....)");
        String result = "";
        for (int i = 0; i < splitMsg.length; i++) {
            result += " " + splitMsg[i];
        }
        result = result.trim();
        _output.println(result);
    }
}
