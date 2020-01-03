package enigma;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MachineTest {

    @Test
    public void testDoubleStep() {
        Alphabet ac = new CharacterRange('A', 'Z');
        Rotor b = new Reflector("B",
                new Permutation("(AE) (BN) (CK) (DQ) (FU) (GY)"
                        + " (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", ac));
        Rotor beta = new FixedRotor("Beta",
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", ac));
        Rotor aIII = new MovingRotor("III",
                new Permutation("ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", ac), "V");
        Rotor aIV = new MovingRotor("IV",
                new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)", ac), "J");
        Rotor aI = new MovingRotor("I",
                new Permutation("(AELTPHQXRU) (BKNW) (CMOY)"
                        + " (DFG) (IV) (JZ) (S)", ac), "Q");
        String setting = "AAAA";
        Rotor[] machineRotors = {b, beta, aIII, aIV, aI};
        String[] rotors = {"B", "Beta", "III", "IV", "I"};
        Machine mach = new Machine(ac, 5, 3,
                new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors);
        mach.setRotors(setting);
        Permutation plugboard = new Permutation("(YF) (ZH)", ac);
        mach.setPlugboard(plugboard);

        assertEquals("AAAAA", getSetting(ac, machineRotors));
        mach.convert('a');
        assertEquals("AAAAB", getSetting(ac, machineRotors));
        mach.setRotors("AXLE");
        assertEquals("AAXLE", getSetting(ac, machineRotors));

        assertTrue(mach.convertChar('Y') == 25);
        assertEquals("AAXLF", getSetting(ac, machineRotors));
        assertTrue(mach.convertChar('A') == 14);
        assertEquals("AAXLG", getSetting(ac, machineRotors));

        mach.setRotors("AXLE");
        assertEquals("AAXLE", getSetting(ac, machineRotors));
        for (int i = 0; i < 12; i++) {
            mach.convertChar('A');
        }
        assertEquals("AAXLQ", getSetting(ac, machineRotors));
        mach.convertChar('H');
        assertEquals(("AAXMR"), getSetting(ac, machineRotors));
    }

    @Test
    public void convertStringTests() {
        Alphabet ac = new CharacterRange('A', 'Z');
        Rotor b = new Reflector("B",
                new Permutation("(AE) (BN) (CK) (DQ) (FU) (GY)"
                        + " (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", ac));
        Rotor beta = new FixedRotor("Beta",
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", ac));
        Rotor aIII = new MovingRotor("III",
                new Permutation("ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", ac), "V");
        Rotor aII = new MovingRotor("II",
                new Permutation("(FIXVYOMW) (CDKLHUP) (ESZ) (BJ)"
                        + " (GR) (NT) (A) (Q)", ac), "E");
        Rotor aI = new MovingRotor("I",
                new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG)"
                        + " (IV) (JZ) (S)", ac), "Q");
        String setting = "AAAA";
        Rotor[] machineRotors = {b, beta, aI, aII, aIII};
        String[] rotors = {"B", "Beta", "I", "II", "III"};
        Machine mach = new Machine(ac, 5, 3,
                new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors);
        mach.setRotors(setting);
        assertEquals(mach.convert("Hello World"),  "ILBDAAMTAZ");
    }

    @Test
    public void inputTests1() {
        Alphabet ac = new CharacterRange('A', 'Z');
        Rotor b = new Reflector("B",
                new Permutation("(AE) (BN) (CK) (DQ) (FU) (GY) (HW)"
                        + " (IJ) (LO) (MP) (RX) (SZ) (TV)", ac));
        Rotor beta = new FixedRotor("Beta",
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", ac));
        Rotor aIII = new MovingRotor("III",
                new Permutation("ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", ac), "V");
        Rotor aIV = new MovingRotor("IV",
                new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)", ac), "J");
        Rotor aI = new MovingRotor("I",
                new Permutation("(AELTPHQXRU) (BKNW) (CMOY)"
                        + " (DFG) (IV) (JZ) (S)", ac), "Q");
        String setting = "AXLE";
        Rotor[] machineRotors = {b, beta, aIII, aIV, aI};
        String[] rotors = {"B", "Beta", "III", "IV", "I"};
        Machine mach = new Machine(ac, 5, 3,
                new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors);
        mach.setRotors(setting);
        Permutation plugboard = new Permutation("(HQ) (EX) (IP) (TR) (BY)", ac);
        mach.setPlugboard(plugboard);

        assertEquals(mach.convert("FROM his shoulder Hiawatha "),
                "QVPQSOKOILPUBKJZPISFXDW");
        assertEquals(mach.convert("Took the camera of rosewood   "),
                "BHCNSCXNUOAATZXSRCFYDGU");
        assertEquals(mach.convert("Made of sliding folding rosewood"),
                "FLPNXGXIXTYJUJRCAUGEUNCFMKUF");
        assertEquals(mach.convert("Neatly put it all together  "),
                "WJFGKCIIRGXODJGVCGPQOH");
        assertEquals(mach.convert("In its case it lay compactly "),
                "ALWEBUHTZMOXIIVXUEFPRPR");
        assertEquals(mach.convert("Folded into nearly nothing"),
                "KCGVPFPYKIKITLBURVGTSFU");
        assertEquals(mach.convert("But he opened out the hinges"),
                "SMBNKFRIIMPDOFJVTTUGRZM");
        assertEquals(mach.convert("Pushed and pulled the joints"),
                "UVCYLFDZPGIBXREWXUEBZQJO");
        assertEquals(mach.convert("  and hinges"), "YMHIPGRRE");
        assertEquals(mach.convert("Till it looked all squares"),
                "GOHETUXDTWLCMMWAVNVJVH");
        assertEquals(mach.convert("   and oblongs"), "OUFANTQACK");
        assertEquals(mach.convert("Like a complicated figure"),
                "KTOZZRDABQNNVPOIEFQAFS");
        assertEquals(mach.convert("in the Second Book of Euclid "),
                "VVICVUDUEREYNPFFMNBJVGQ");
    }

    @Test
    public void inputTests2() {
        Alphabet ac = new CharacterRange('A', 'Z');
        Rotor b = new Reflector("B",
                new Permutation("(AE) (BN) (CK) (DQ) (FU) (GY) (HW)"
                        + " (IJ) (LO) (MP) (RX) (SZ) (TV)", ac));
        Rotor beta = new FixedRotor("Beta",
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", ac));
        Rotor aIII = new MovingRotor("III",
                new Permutation("ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", ac), "V");
        Rotor aIV = new MovingRotor("IV",
                new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)", ac), "J");
        Rotor aI = new MovingRotor("I",
                new Permutation("(AELTPHQXRU) (BKNW) (CMOY)"
                        + " (DFG) (IV) (JZ) (S)", ac), "Q");
        String setting = "AXLE";
        Rotor[] machineRotors = {b, beta, aIII, aIV, aI};
        String[] rotors = {"B", "Beta", "III", "IV", "I"};
        Machine mach = new Machine(ac, 5, 3,
                new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors);
        mach.setRotors(setting);
        Permutation plugboard = new Permutation("(HQ) (EX) (IP) (TR) (BY)", ac);
        mach.setPlugboard(plugboard);

        assertEquals(mach.convert("QVPQS OKOIL PUBKJ ZPISF XDW"),
                "FROMHISSHOULDERHIAWATHA");
        assertEquals(mach.convert("BHCNS CXNUO AATZX SRCFY DGU "),
                "TOOKTHECAMERAOFROSEWOOD");
        assertEquals(mach.convert("FLPNX GXIXT YJUJR CAUGE UNCFM KUF"),
                "MADEOFSLIDINGFOLDINGROSEWOOD");
        assertEquals(mach.convert("WJFGK CIIRG XODJG VCGPQ OH"),
                "NEATLYPUTITALLTOGETHER");
        assertEquals(mach.convert("ALWEB UHTZM OXIIV XUEFP RPR"),
                "INITSCASEITLAYCOMPACTLY");
        assertEquals(mach.convert("KCGVP FPYKI KITLB URVGT SFU"),
                "FOLDEDINTONEARLYNOTHING");
        assertEquals(mach.convert("SMBNK FRIIM PDOFJ VTTUG RZM"),
                "BUTHEOPENEDOUTTHEHINGES");
        assertEquals(mach.convert("UVCYL FDZPG IBXRE WXUEB ZQJO"),
                "PUSHEDANDPULLEDTHEJOINTS");
        assertEquals(mach.convert("YMHIP GRRE"), "ANDHINGES");
        assertEquals(mach.convert("GOHET UXDTW LCMMW AVNVJ VH"),
                "TILLITLOOKEDALLSQUARES");
        assertEquals(mach.convert("OUFAN TQACK"), "ANDOBLONGS");
        assertEquals(mach.convert("KTOZZ RDABQ NNVPO IEFQA FS"),
                "LIKEACOMPLICATEDFIGURE");
        assertEquals(mach.convert("VVICV UDUER EYNPF FMNBJ VGQ"),
                "INTHESECONDBOOKOFEUCLID");
    }

    /** Helper method to get the String representation
     * of the current Rotor settings */
    public String getSetting(Alphabet alph, Rotor[] machineRotors) {
        String currSetting = "";
        for (Rotor r : machineRotors) {
            currSetting += alph.toChar(r.setting());
        }
        return currSetting;
    }
}
