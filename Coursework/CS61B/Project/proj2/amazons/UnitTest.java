package amazons;

import org.junit.Test;

import static amazons.Piece.*;
import static amazons.Piece.WHITE;
import static org.junit.Assert.*;
import ucb.junit.textui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** The suite of all JUnit tests for the enigma package.
 *  @author Daniel Martin
 */
public class UnitTest {


    /**
     * Run the JUnit tests in this package. Add xxxTest.class entries to
     * the arguments of runClasses to run other JUnit tests.
     */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class);
    }

    /**
     * Tests basic correctness of put and get on the initialized board.
     */
    @Test
    public void testBasicPutGet() {
        Board b = new Board();
        b.put(BLACK, Square.sq(3, 5));
        assertEquals(b.get(3, 5), BLACK);
        b.put(WHITE, Square.sq(9, 9));
        assertEquals(b.get(9, 9), WHITE);
        b.put(EMPTY, Square.sq(3, 5));
        assertEquals(b.get(3, 5), EMPTY);
    }

    /**
     * Tests the put method in my Board.
     */
    @Test
    public void testPutGet() {
        Board b = new Board();
        b.put(BLACK, Square.sq(5, 5));
        b.put(WHITE, Square.sq(3, 3));
        b.put(EMPTY, Square.sq(4, 4));
        b.put(SPEAR, Square.sq(2, 2));

        assertTrue(b.get(Square.sq(5, 5)).toString().equals("B"));
        assertTrue(b.get(Square.sq(3, 3)).toString().equals("W"));
        assertTrue(b.get(Square.sq(4, 4)).toString().equals("-"));
        assertTrue(b.get(Square.sq(2, 2)).toString().equals("S"));

        b.put(WHITE, Square.sq(5, 5));
        assertTrue(b.get(Square.sq(5, 5)).toString().equals("W"));
        assertFalse(b.get(Square.sq(5, 5)).toString().equals("B"));
    }

    /**
     * Tests proper identification of legal/illegal queen moves.
     */
    @Test
    public void testIsQueenMove() {
        assertFalse(Square.sq(1, 5).isQueenMove(Square.sq(1, 5)));
        assertFalse(Square.sq(1, 5).isQueenMove(Square.sq(2, 7)));
        assertFalse(Square.sq(0, 0).isQueenMove(Square.sq(5, 1)));
        assertTrue(Square.sq(1, 1).isQueenMove(Square.sq(9, 9)));
        assertTrue(Square.sq(2, 7).isQueenMove(Square.sq(8, 7)));
        assertTrue(Square.sq(3, 0).isQueenMove(Square.sq(3, 4)));
        assertTrue(Square.sq(7, 9).isQueenMove(Square.sq(0, 2)));
    }

    /**
     * Tests toString for initial board state and a smiling board state. :)
     */
    @Test
    public void testToString() {
        Board b = new Board();
        assertEquals(INIT_BOARD_STATE, b.toString());
        makeSmile(b);
        assertEquals(SMILE, b.toString());

        Board b1 = new Board();
        b1.makeMove(Square.sq("d1"), Square.sq("d7"), Square.sq("g7"));
        b1.makeMove(Square.sq("d10"), Square.sq("c9"), Square.sq("h4"));
        assertEquals(AFTER_MOVE, b1.toString());
        b1.undo();
        assertEquals(AFTER_MOVE_UNDO, b1.toString());
    }

    /**
     * Tests to ensure proper direction value from From - To.
     */
    @Test
    public void testDirection() {
        Board b = new Board();
        assertTrue(Square.sq(5, 5).direction(Square.sq(8, 5)) == 2);
        assertTrue(Square.sq(5, 5).direction(Square.sq(2, 5)) == 6);
        assertTrue(Square.sq(5, 5).direction(Square.sq(5, 1)) == 4);
        assertTrue(Square.sq(5, 5).direction(Square.sq(5, 8)) == 0);
        assertTrue(Square.sq(5, 5).direction(Square.sq(7, 7)) == 1);
        assertTrue(Square.sq(5, 5).direction(Square.sq(3, 7)) == 7);
        assertTrue(Square.sq(5, 5).direction(Square.sq(0, 0)) == 5);
        assertTrue(Square.sq(5, 5).direction(Square.sq(9, 1)) == 3);
    }

    /**
     * Tests to ensure correct/valid To square steps away from From.
     */
    @Test
    public void testQueenMove() {
        assertTrue(Square.sq(5, 5).queenMove(2, 3) == Square.sq(8, 5));
        assertTrue(Square.sq(5, 5).queenMove(6, 4) == Square.sq(1, 5));
        assertTrue(Square.sq(5, 5).queenMove(0, 1) == Square.sq(5, 6));
        assertTrue(Square.sq(5, 5).queenMove(4, 5) == Square.sq(5, 0));
        assertTrue(Square.sq(5, 5).queenMove(1, 3) == Square.sq(8, 8));
        assertTrue(Square.sq(5, 5).queenMove(7, 2) == Square.sq(3, 7));
        assertTrue(Square.sq(5, 5).queenMove(5, 4) == Square.sq(1, 1));
        assertTrue(Square.sq(5, 5).queenMove(3, 3) == Square.sq(8, 2));
    }

    /**
     * Tests isUnblockedMove for positions From - To.
     */
    @Test
    public void testIsUnblockedMove() {
        Board b = new Board();

        b.put(WHITE, Square.sq(5, 5));
        assertTrue(b.isUnblockedMove(Square.sq(5, 5), Square.sq(7, 5), null));

        b.put(BLACK, Square.sq(5, 4));
        assertTrue(b.isUnblockedMove(Square.sq(5, 4), Square.sq(7, 4), null));

        b.put(WHITE, Square.sq(5, 3));
        assertFalse(b.isUnblockedMove(Square.sq(5, 3), Square.sq(5, 5), null));

        b.put(WHITE, Square.sq(1, 1));
        b.put(SPEAR, Square.sq(1, 2));
        assertTrue(b.isUnblockedMove(Square.sq(1, 1),
                Square.sq(1, 5), Square.sq(1, 2)));
    }

    /**
     * Tests to ensure that From is a valid starting square.
     */
    @Test
    public void testIsLegalFrom() {
        Board b = new Board();
        b.put(WHITE, Square.sq(4, 5));
        b.put(WHITE, Square.sq(5, 5));
        b.put(SPEAR, Square.sq(7, 2));

        assertTrue(b.isLegal(Square.sq(4, 5)));
        assertTrue(b.isLegal(Square.sq(5, 5)));
        assertFalse(b.isLegal(Square.sq(7, 2)));
        assertFalse(b.isLegal(Square.sq(3, 2)));
    }

    /**
     * Tests if the first part move from From - To is valid.
     */
    @Test
    public void testIsLegalFromTo() {
        Board b = new Board();
        b.put(WHITE, Square.sq(5, 5));
        b.put(WHITE, Square.sq(3, 3));
        b.put(SPEAR, Square.sq(7, 5));
        b.put(BLACK, Square.sq(5, 1));

        assertTrue(b.isLegal(Square.sq(5, 5), Square.sq(7, 7)));
        assertTrue(b.isLegal(Square.sq(3, 3), Square.sq(8, 3)));
        assertFalse(b.isLegal(Square.sq(7, 5), Square.sq(7, 2)));
        assertFalse(b.isLegal(Square.sq(5, 5), Square.sq(5, 1)));
        assertFalse(b.isLegal(Square.sq(5, 5), Square.sq(9, 5)));
    }

    /**
     * Tests if the second part move from From - To - Spear is valid.
     */
    @Test
    public void testisLegalFromToSpear() {
        Board b = new Board();

        b.put(WHITE, Square.sq(3, 0));
        assertTrue(b.isLegal(Square.sq(3, 0),
                Square.sq(3, 3), Square.sq(3, 0)));

        b.put(WHITE, Square.sq(5, 5));
        assertTrue(b.isLegal(Square.sq(5, 5),
                Square.sq(7, 5), Square.sq(5, 5)));

        b.put(WHITE, Square.sq(9, 0));
        assertTrue(b.isLegal(Square.sq(9, 0),
                Square.sq(9, 2), Square.sq(7, 2)));

        b.put(WHITE, Square.sq(6, 8));
        assertFalse(b.isLegal(Square.sq(6, 8),
                Square.sq(4, 8), Square.sq(4, 8)));
    }

    /**
     * Tests if move is the a legal move in the current position.
     */
    @Test
    public void testMakeMoveFTS() {
        Board b = new Board();

        b.put(WHITE, Square.sq(5, 5));
        b.makeMove(Square.sq(5, 5), Square.sq(2, 2), Square.sq(6, 6));
        assertTrue(b.get(Square.sq(5, 5)).toString().equals("-"));
        assertTrue(b.get(Square.sq(2, 2)).toString().equals("W"));
        assertTrue(b.get(Square.sq(6, 6)).toString().equals("S"));

        b.put(BLACK, Square.sq(3, 3));
        b.makeMove(Square.sq(3, 3), Square.sq(3, 8), Square.sq(3, 6));
        assertTrue(b.get(Square.sq(3, 3)).toString().equals("-"));
        assertTrue(b.get(Square.sq(3, 8)).toString().equals("B"));
        assertTrue(b.get(Square.sq(3, 6)).toString().equals("S"));

        b.put(WHITE, Square.sq(1, 1));
        b.makeMove(Square.sq(1, 1), Square.sq(8, 1), Square.sq(5, 1));
        assertTrue(b.get(Square.sq(1, 1)).toString().equals("-"));
        assertTrue(b.get(Square.sq(8, 1)).toString().equals("W"));
        assertTrue(b.get(Square.sq(5, 1)).toString().equals("S"));

        b.put(BLACK, Square.sq(2, 6));
        b.makeMove(Square.sq(2, 6), Square.sq(2, 8), Square.sq(2, 6));
        assertTrue(b.get(Square.sq(2, 8)).toString().equals("B"));
        assertTrue(b.get(Square.sq(2, 6)).toString().equals("S"));
    }

    /**
     * Tests if move according to MOVE.
     */
    @Test
    public void testMakeMoveM() {
        Board b = new Board();

        b.put(WHITE, Square.sq(5, 5));
        Move m1 = Move.mv(Square.sq(5, 5), Square.sq(2, 2), Square.sq(6, 6));
        b.makeMove(m1);
        assertTrue(b.get(Square.sq(5, 5)).toString().equals("-"));
        assertTrue(b.get(Square.sq(2, 2)).toString().equals("W"));
        assertTrue(b.get(Square.sq(6, 6)).toString().equals("S"));

        b.put(BLACK, Square.sq(3, 3));
        Move m2 = Move.mv(Square.sq(3, 3), Square.sq(3, 8), Square.sq(3, 6));
        b.makeMove(m2);
        assertTrue(b.get(Square.sq(3, 3)).toString().equals("-"));
        assertTrue(b.get(Square.sq(3, 8)).toString().equals("B"));
        assertTrue(b.get(Square.sq(3, 6)).toString().equals("S"));

        b.put(WHITE, Square.sq(1, 1));
        Move m3 = Move.mv(Square.sq(1, 1), Square.sq(8, 1), Square.sq(5, 1));
        b.makeMove(m3);
        assertTrue(b.get(Square.sq(1, 1)).toString().equals("-"));
        assertTrue(b.get(Square.sq(8, 1)).toString().equals("W"));
        assertTrue(b.get(Square.sq(5, 1)).toString().equals("S"));

        b.put(BLACK, Square.sq(2, 6));
        Move m4 = Move.mv(Square.sq(2, 6), Square.sq(2, 8), Square.sq(2, 6));
        b.makeMove(m4);
        assertTrue(b.get(Square.sq(2, 8)).toString().equals("B"));
        assertTrue(b.get(Square.sq(2, 6)).toString().equals("S"));
    }

    /**
     * Tests for the correct number of moves.
     */
    @Test
    public void testTotalNumberMoves() {
        Board b = new Board();

        assertTrue(b.numMoves() == 0);

        b.put(WHITE, Square.sq(5, 5));
        b.makeMove(Square.sq(5, 5), Square.sq(2, 2), Square.sq(6, 6));
        assertTrue(b.numMoves() == 1);

        b.put(BLACK, Square.sq(3, 3));
        b.makeMove(Square.sq(3, 3), Square.sq(3, 8), Square.sq(3, 6));
        assertTrue(b.numMoves() == 2);

        b.put(WHITE, Square.sq(1, 1));
        b.makeMove(Square.sq(1, 1), Square.sq(8, 1), Square.sq(5, 1));
        assertTrue(b.numMoves() == 3);
    }

    /**
     * Tests for the correct number of moves, after UNDO.
     */
    @Test
    public void testUndoMoves() {
        Board b = new Board();

        b.put(WHITE, Square.sq(5, 5));
        b.makeMove(Square.sq(5, 5), Square.sq(8, 5), Square.sq(8, 9));
        b.undo();

        assertTrue(b.get(Square.sq(8, 5)).toString().equals("-"));
        assertTrue(b.get(Square.sq(5, 5)).toString().equals("W"));
        assertTrue(b.get(Square.sq(8, 9)).toString().equals("-"));
    }

    /**
     * test iterator.
     */
    @Test
    public void boardTestReachableFromIterator() {
        Board board = new Board();
        List<Square> l0 = new ArrayList<>();
        Iterator<Square> iter = board.reachableFrom(Square.sq("d1"), null);
        Square sq;
        while (true) {
            if (!iter.hasNext()) {
                break;
            }
            sq = iter.next();
            l0.add(sq);
        }
        assertEquals("wrong number of elements", 20, l0.size());
        board.put(SPEAR, Square.sq("e1"));
        board.put(SPEAR, Square.sq("d2"));
        board.put(SPEAR, Square.sq("c2"));
        board.put(SPEAR, Square.sq("c1"));
        List<Square> l1 = new ArrayList<>();
        Iterator<Square> iter1 = board.reachableFrom(Square.sq("d1"), null);
        Square sq1;
        while (true) {
            if (!iter1.hasNext()) {
                break;
            }
            sq1 = iter1.next();
            l1.add(sq1);
        }
        assertEquals("wrong", 5, l1.size());
        board.put(SPEAR, Square.sq("e2"));
        List<Square> l2 = new ArrayList<>();
        Iterator<Square> iter2 = board.reachableFrom(Square.sq("d1"), null);
        Square sq2;
        while (true) {
            if (!iter2.hasNext()) {
                break;
            }
            sq2 = iter2.next();
            l2.add(sq2);
        }
        assertEquals("wrong", 0, l2.size());
    }


    /**
     * test LegalMoveIterator in BOARD.
     */
    @Test
    public void testLegalMoveIterator() {
        Board b = new Board();
        Iterator<Move> moves = b.legalMoves();
        while (moves.hasNext()) {
            Move mv = moves.next();
            assertEquals(String.format("%s is not legal move", mv.toString()),
                    true, b.isLegal(mv));
        }
        b.put(SPEAR, Square.sq("e1"));
        b.put(SPEAR, Square.sq("e2"));
        b.put(SPEAR, Square.sq("d2"));
        b.put(SPEAR, Square.sq("c2"));
        b.put(SPEAR, Square.sq("c1"));
        b.put(SPEAR, Square.sq("a3"));
        b.put(SPEAR, Square.sq("b3"));
        b.put(SPEAR, Square.sq("b4"));
        b.put(SPEAR, Square.sq("b5"));
        b.put(SPEAR, Square.sq("a5"));
        b.put(SPEAR, Square.sq("f2"));
        b.put(SPEAR, Square.sq("g2"));
        b.put(SPEAR, Square.sq("h2"));
        b.put(SPEAR, Square.sq("h1"));
        b.put(SPEAR, Square.sq("j3"));
        b.put(SPEAR, Square.sq("i3"));
        b.put(SPEAR, Square.sq("i4"));
        b.put(SPEAR, Square.sq("i5"));
        b.put(SPEAR, Square.sq("j5"));
        Iterator<Move> moves1 = b.legalMoves();
        ArrayList<Move> listOfMoves = new ArrayList<>();
        while (moves1.hasNext()) {
            Move mv = moves1.next();
            assertEquals(String.format("%s is not legal move", mv.toString()),
                    true, b.isLegal(mv));
            listOfMoves.add(mv);
        }
        assertEquals("Wrong number of "
                + "elements", 1, listOfMoves.size());
    }

    private void makeSmile(Board b) {
        b.put(EMPTY, Square.sq(0, 3));
        b.put(EMPTY, Square.sq(0, 6));
        b.put(EMPTY, Square.sq(9, 3));
        b.put(EMPTY, Square.sq(9, 6));
        b.put(EMPTY, Square.sq(3, 0));
        b.put(EMPTY, Square.sq(3, 9));
        b.put(EMPTY, Square.sq(6, 0));
        b.put(EMPTY, Square.sq(6, 9));
        for (int col = 1; col < 4; col += 1) {
            for (int row = 6; row < 9; row += 1) {
                b.put(SPEAR, Square.sq(col, row));
            }
        }
        b.put(EMPTY, Square.sq(2, 7));
        for (int col = 6; col < 9; col += 1) {
            for (int row = 6; row < 9; row += 1) {
                b.put(SPEAR, Square.sq(col, row));
            }
        }
        b.put(EMPTY, Square.sq(7, 7));
        for (int lip = 3; lip < 7; lip += 1) {
            b.put(WHITE, Square.sq(lip, 2));
        }
        b.put(WHITE, Square.sq(2, 3));
        b.put(WHITE, Square.sq(7, 3));
    }

    static final String INIT_BOARD_STATE =
            "   - - - B - - B - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   B - - - - - - - - B\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   W - - - - - - - - W\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - W - - W - - -\n";

    static final String SMILE =
            "   - - - - - - - - - -\n"
                    + "   - S S S - - S S S -\n"
                    + "   - S - S - - S - S -\n"
                    + "   - S S S - - S S S -\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - W - - - - W - -\n"
                    + "   - - - W W W W - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - - - - -\n";


    static final String AFTER_MOVE =
            "   - - - - - - B - - -\n"
                    + "   - - B - - - - - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   B - - W - - S - - B\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   W - - - - - - S - W\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - W - - -\n";

    static final String AFTER_MOVE_UNDO =
            "   - - - B - - B - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   B - - W - - S - - B\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   W - - - - - - - - W\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - W - - -\n";

}



