package amazons;

import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

import static amazons.Utils.*;

/** Represents a position on an Amazons board.  Positions are numbered
 *  from 0 (lower-left corner) to 99 (upper-right corner).  Squares
 *  are immutable and unique: there is precisely one square created for
 *  each distinct position.  Clients create squares using the factory method
 *  sq, not the constructor.  Because there is a unique Square object for each
 *  position, you can freely use the cheap == operator (rather than the
 *  .equals method) to compare Squares, and the program does not waste time
 *  creating the same square over and over again.
 *
 *  @author Daniel Martin
 */
final class Square {

    /** My index position. */
    private final int _index;

    /** My row and column (redundant, since these are determined by _index). */
    private final int _row, _col;

    /** My String denotation. */
    private final String _str;

    /** ArrayList of characters associated with columns. */
    private static final List<String> CHARLST = Arrays.asList("a", "b", "c",
            "d", "e", "f", "g", "h", "i", "j");

    /** The regular expression for a square designation (e.g.,
     *  a3). For convenience, it is in parentheses to make it a
     *  group.  This subpattern is intended to be incorporated into
     *  other pattern that contain square designations (such as
     *  patterns for moves). */
    static final String SQ = "([a-j](?:[1-9]|10))";

    /** Return my row position, where 0 is the bottom row. */
    int row() {
        return _row;
    }

    /** Return my column position, where 0 is the leftmost column. */
    int col() {
        return _col;
    }

    /** Return my index position (0-99).  0 represents square a1, and 99
     *  is square j10. */
    int index() {
        return _index;
    }

    /** Return true iff THIS - TO is a valid queen move. */
    boolean isQueenMove(Square to) {
        if (to == null) {
            return false;
        }

        int rows = this.row() - to.row();
        int cols = this.col() - to.col();
        return this != to && (rows == 0 || cols == 0
                || rows == cols || rows == -cols);
    }

    /** Definitions of direction for queenMove.  DIR[k] = (dcol, drow)
     *  means that to going one step from (col, row) in direction k,
     *  brings us to (col + dcol, row + drow). */
    private static final int[][] DIR = {
        { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 },
        { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }
    };

    /** Return the Square that is STEPS>0 squares away from me in direction
     *  DIR, or null if there is no such square.
     *  DIR = 0 for north, 1 for northeast, 2 for east, etc., up to 7 for
     *  northwest. If DIR has another value, return null. Thus, unless the
     *  result is null the resulting square is a queen move away from me. */
    Square queenMove(int dir, int steps) {
        if (!(dir >= 0 && dir < 8)) {
            return null;
        }

        int column = this._col,
                row = this._row;
        for (int i = 0; i < steps; i++) {
            column = column + DIR[dir][0];
            row = row + DIR[dir][1];
        }
        if (!exists(column, row)) {
            return null;
        } else {
            return sq(column, row);
        }
    }

    /** Return the direction (an int as defined in the documentation
     *  for queenMove) of the queen move THIS-TO. */
    int direction(Square to) {
        assert isQueenMove(to);

        if (to._col == this._col) {
            if (to._index > this._index) {
                return 0;
            } else {
                return 4;
            }
        } else if (to._row == this._row) {
            if (to._index > this._index) {
                return 2;
            } else {
                return 6;
            }
        } else if (Math.abs(to._index - this._index) % 11 == 0) {
            if (to._index > this._index) {
                return 1;
            } else {
                return 5;
            }
        } else if (Math.abs(to._index - this._index) % 9 == 0) {
            if (to._index > this._index) {
                return 7;
            } else {
                return 3;
            }
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return _str;
    }

    /** Return true iff COL ROW is a legal square. */
    static boolean exists(int col, int row) {
        return row >= 0 && col >= 0 && row < Board.SIZE && col < Board.SIZE;
    }

    /** Return the (unique) Square denoting COL ROW. */
    static Square sq(int col, int row) {
        if (!exists(row, col)) {
            throw error("row or column out of bounds");
        }

        if (row == 0) {
            return sq(col);
        } else {
            String total = Integer.toString(row) + Integer.toString(col);
            int cleanTotal = Integer.parseInt(total);
            return sq(cleanTotal);
        }
    }

    /** Return the (unique) Square denoting the position with index INDEX. */
    static Square sq(int index) {
        return SQUARES[index];
    }

    /** Return the (unique) Square denoting the position COL ROW, where
     *  COL ROW is the standard text format for a square (e.g., a4). */
    static Square sq(String col, String row) {
        int cleanRow = Integer.parseInt(row) - 1;
        int cleanCol = CHARLST.indexOf(col);
        return sq(cleanCol, cleanRow);
    }

    /** Return the (unique) Square denoting the position in POSN, in the
     *  standard text format for a square (e.g. a4). POSN must be a
     *  valid square designation. */
    static Square sq(String posn) {
        assert posn.matches(SQ);
        String cleanCol = Character.toString(posn.charAt(0));
        String cleanRow = posn.substring(1);
        return sq(cleanCol, cleanRow);
    }

    /** Return an iterator over all Squares. */
    static Iterator<Square> iterator() {
        return SQUARE_LIST.iterator();
    }

    /** Return the Square with index INDEX. */
    private Square(int index) {
        _index = index;

        if (_index < 10) {
            _row = 0;
            _col = _index;
        } else if (_index < 100) {
            _row = _index / 10;
            _col = _index % 10;
        } else {
            throw error("Invalid input, input must (0 - 99");
        }
        _str = String.format("%s", CHARLST.get(_col) + (_row + 1));
    }

    /** The cache of all created squares, by index. */
    private static final Square[] SQUARES =
        new Square[Board.SIZE * Board.SIZE];

    /** SQUARES viewed as a List. */
    private static final List<Square> SQUARE_LIST = Arrays.asList(SQUARES);

    static {
        for (int i = Board.SIZE * Board.SIZE - 1; i >= 0; i -= 1) {
            SQUARES[i] = new Square(i);
        }
    }
}
