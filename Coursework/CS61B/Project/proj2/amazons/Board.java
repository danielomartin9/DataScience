package amazons;

import java.util.Stack;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import static amazons.Piece.*;

/** The state of an Amazons Game.
 *  @author Daniel Martin
 */
class Board {

    /**
     * An empty iterator for initialization.
     */
    private static final Iterator<Square> NO_SQUARES =
            Collections.emptyIterator();

    /**
     * Piece whose turn it is (BLACK or WHITE).
     */
    private Piece _turn;

    /**
     * Cached value of winner on this board, or EMPTY if it has not been
     * computed.
     */
    private Piece _winner;

    /**
     * The number of squares on a side of the board.
     */
    static final int SIZE = 10;

    /**
     * Integer variable to store the total number of moves.
     */
    private int totalMoves = 0;

    /**
     * Map to containing current Square, Piece values.
     */
    private Map<Square, Piece> currMap = new HashMap<Square, Piece>();

    /**
     * Stack to contain Moves.
     */
    private Stack<Move> moveContainer = new Stack<>();

    /**
     * Initializes a game board with SIZE squares on a side in the
     * initial position.
     */
    Board() {
        init();
    }

    /**
     * Initializes a copy of MODEL.
     */
    Board(Board model) {
        copy(model);
    }

    /**
     * Copies MODEL into me.
     */
    void copy(Board model) {
        this.currMap.putAll(model.currMap);
        _turn = model.turn();
        totalMoves = model.numMoves();
        _winner = model.winner();
        moveContainer = new Stack<>();
        moveContainer.addAll(model.moveContainer);
    }

    /**
     * Clears the board to the initial position.
     */
    void init() {

        for (int i = 0; i < 100; i++) {
            currMap.put(Square.sq(i), Piece.EMPTY);
        }

        currMap.put(Square.sq("a4"), Piece.WHITE);
        currMap.put(Square.sq("d1"), Piece.WHITE);
        currMap.put(Square.sq("g1"), Piece.WHITE);
        currMap.put(Square.sq("j4"), Piece.WHITE);

        currMap.put(Square.sq("a7"), Piece.BLACK);
        currMap.put(Square.sq("d10"), Piece.BLACK);
        currMap.put(Square.sq("g10"), Piece.BLACK);
        currMap.put(Square.sq("j7"), Piece.BLACK);

        _turn = WHITE;
        _winner = EMPTY;
        totalMoves = 0;
    }

    /**
     * Return the Piece whose move it is (WHITE or BLACK).
     */
    Piece turn() {
        return _turn;
    }

    /**
     * Return the number of moves (that have not been undone) for this
     * board.
     */
    int numMoves() {
        return totalMoves;
    }

    /**
     * Return the winner in the current position, or null if the game is
     * not yet finished.
     */
    Piece winner() {
        if (!legalMoves().hasNext()) {
            _winner = _turn.opponent();
        } else {
            return null;
        }
        return _winner;
    }

    /**
     * Return the contents the square at S.
     */
    final Piece get(Square s) {
        return currMap.get(s);
    }

    /**
     * Return the contents of the square at (COL, ROW), where
     * 0 <= COL, ROW <= 9.
     */

    /** Return the contents of the square at (COL, ROW), where
     *  0 <= COL, ROW <= 9. */
    final Piece get(int col, int row) {
        return currMap.get(Square.sq(col, row));
    }

    /**
     * Return the contents of the square at COL ROW.
     */
    final Piece get(char col, char row) {
        return get(col - 'a', row - '1');
    }

    /**
     * Set square S to P.
     */
    final void put(Piece p, Square s) {
        currMap.put(s, p);
    }

    /**
     * Set square (COL, ROW) to P.
     */
    final void put(Piece p, int col, int row) {
        currMap.put(Square.sq(col, row), p);
    }

    /**
     * Set square COL ROW to P.
     */
    final void put(Piece p, char col, char row) {
        put(p, col - 'a', row - '1');
    }

    /**
     * Return true iff FROM - TO is an unblocked queen move on the current
     * board, ignoring the contents of ASEMPTY, if it is encountered.
     * For this to be true, FROM-TO must be a queen move and the
     * squares along it, other than FROM and ASEMPTY, must be
     * empty. ASEMPTY may be null, in which case it has no effect.
     */
    boolean isUnblockedMove(Square from, Square to, Square asEmpty) {

        if (from.isQueenMove(to)) {
            int fromDir = from.direction(to);
            int stepCount = 1;

            if (fromDir == 0 || fromDir == 4) {
                stepCount = Math.abs(to.index() - from.index()) / 10;
            } else if (fromDir == 1 || fromDir == 5) {
                stepCount = Math.abs(to.index() - from.index()) / 11;
            } else if (fromDir == 2 || fromDir == 6) {
                stepCount = Math.abs(to.index() - from.index());
            } else if (fromDir == 3 || fromDir == 7) {
                stepCount = Math.abs(to.index() - from.index()) / 9;
            } else {
                stepCount = -1;
            }

            for (int i = 1; i <= stepCount; i++) {
                Square temp = from.queenMove(fromDir, i);

                if (temp.equals(asEmpty)) {
                    continue;
                } else if (!get(temp).equals(EMPTY)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Return true iff FROM is a valid starting square for a move.
     */
    boolean isLegal(Square from) {
        return currMap.get(from) == _turn;
    }

    /**
     * Return true iff FROM-TO is a valid first part of move, ignoring
     * spear throwing.
     */
    boolean isLegal(Square from, Square to) {
        return isLegal(from)
                && isUnblockedMove(from, to, null);
    }

    /**
     * Return true iff FROM-TO(SPEAR) is a legal move in the current
     * position.
     */
    boolean isLegal(Square from, Square to, Square spear) {
        return isLegal(from) && isLegal(from, to)
                && isUnblockedMove(to, spear, from);
    }

    /**
     * Return true iff MOVE is a legal move in the current
     * position.
     */
    boolean isLegal(Move move) {
        return isLegal(move.from()) && isLegal(move.from(), move.to());
    }

    /**
     * Move FROM-TO(SPEAR), assuming this is a legal move.
     */
    void makeMove(Square from, Square to, Square spear) {
        moveContainer.push(Move.mv(from, to, spear));
        currMap.remove(from);
        currMap.put(from, EMPTY);
        currMap.remove(to);
        currMap.put(to, _turn);
        currMap.remove(spear);
        currMap.put(spear, SPEAR);
        _turn = _turn.opponent();
        totalMoves = numMoves() + 1;
    }

    /**
     * Move according to MOVE, assuming it is a legal move.
     */
    void makeMove(Move move) {
        makeMove(move.from(), move.to(), move.spear());
    }

    /**
     * Undo one move.  Has no effect on the initial board.
     */
    void undo() {
        if (numMoves() > 0) {
            Move last = moveContainer.pop();
            put(get(last.to()), last.from());
            put(EMPTY, last.to());
            put(EMPTY, last.spear());
            totalMoves = numMoves() - 1;
            _winner = EMPTY;
        }
    }

    /**
     * Return an Iterator over the Squares that are reachable by an
     * unblocked queen move from FROM. Does not pay attention to what
     * piece (if any) is on FROM, nor to whether the game is finished.
     * Treats square ASEMPTY (if non-null) as if it were EMPTY.  (This
     * feature is useful when looking for Moves, because after moving a
     * piece, one wants to treat the Square it came from as empty for
     * purposes of spear throwing.)
     */
    Iterator<Square> reachableFrom(Square from, Square asEmpty) {
        return new ReachableFromIterator(from, asEmpty);
    }

    /**
     * Return an Iterator over all legal moves on the current board.
     */
    Iterator<Move> legalMoves() {
        return new LegalMoveIterator(_turn);
    }

    /**
     * Return an Iterator over all legal moves on the current board for
     * SIDE (regardless of whose turn it is).
     */
    Iterator<Move> legalMoves(Piece side) {
        return new LegalMoveIterator(side);
    }

    /**
     * An iterator used by reachableFrom.
     */
    private class ReachableFromIterator implements Iterator<Square> {

        /**
         * Starting square.
         */
        private Square _from;
        /**
         * Current direction.
         */
        private int _dir;
        /**
         * Current distance.
         */
        private int _steps;
        /**
         * Square treated as empty.
         */
        private Square _asEmpty;

        /**
         * Iterator of all squares reachable by queen move from FROM,
         * treating ASEMPTY as empty.
         */
        ReachableFromIterator(Square from, Square asEmpty) {
            _from = from;
            _dir = -1;
            _steps = 0;
            _asEmpty = asEmpty;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _dir < 8;
        }

        @Override
        public Square next() {
            if (hasNext()) {
                Square result = _from.queenMove(_dir, _steps);
                toNext();
                return result;
            } else {
                throw new Error("Invalid next square");
            }
        }

        /**
         * Advance _dir and _steps, so that the next valid Square is
         * _steps steps in direction _dir from _from.
         */
        private void toNext() {
            if (_from.queenMove(_dir, _steps + 1) != null
                    && isUnblockedMove(_from,
                            _from.queenMove(_dir, _steps + 1), _asEmpty)) {
                _steps += 1;
            } else {
                _dir += 1;
                _steps = 1;
                if (_dir > 7) {
                    return;
                } else if (_from.queenMove(_dir, _steps) == null
                        || !isUnblockedMove(_from,
                                _from.queenMove(_dir, _steps), _asEmpty)) {
                    toNext();
                }
            }
        }
    }

    /**
     * An iterator used by legalMoves.
     */
    private class LegalMoveIterator implements Iterator<Move> {

        /**
         * Color of side whose moves we are iterating.
         */
        private Piece _fromPiece;
        /**
         * Current starting square.
         */
        private Square _start;
        /**
         * Remaining starting squares to consider.
         */
        private Iterator<Square> _startingSquares;
        /**
         * Current piece's new position.
         */
        private Square _nextSquare;
        /** Spear from next possible square. */
        private Square _spear;
        /**
         * Remaining moves from _start to consider.
         */
        private Iterator<Square> _pieceMoves;
        /**
         * Remaining spear throws from _piece to consider.
         */
        private Iterator<Square> _spearThrows;
        /**
         * Counter to keep track of queens.
         */
        private int _counter;

        /**
         * All legal moves for SIDE (WHITE or BLACK).
         */
        LegalMoveIterator(Piece side) {
            _startingSquares = Square.iterator();
            _spearThrows = NO_SQUARES;
            _pieceMoves = NO_SQUARES;
            _fromPiece = side;
            _counter = 0;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _counter < 5;
        }


        @Override
        public Move next() {
            if (hasNext()) {
                Move m = Move.mv(_start, _nextSquare, _spear);
                toNext();
                return m;
            } else {
                throw new Error("help me");
            }
        }

        /**
         * Advance so that the next valid Move is
         * _start-_nextSquare(sp), where sp is the next value of
         * _spearThrows.
         */
        private void toNext() {
            if (!_spearThrows.hasNext()) {
                while (!_pieceMoves.hasNext()) {
                    if (_counter == 4) {
                        _counter += 1;
                        return;
                    }
                    _start = findQueen();
                    _pieceMoves = new ReachableFromIterator(_start, null);
                }
                findSpear();
            }
            _spear = _spearThrows.next();
        }

        /** Method to return Queen square. */
        private Square findQueen() {
            Square queen = _startingSquares.next();
            while (!get(queen).equals(_fromPiece)) {
                queen = _startingSquares.next();
            }
            _counter += 1;
            return queen;
        }

        /** Method to find next spear. */
        private void findSpear() {
            _nextSquare = _pieceMoves.next();
            _spearThrows = new ReachableFromIterator(_nextSquare, _start);
        }
    }


    @Override
    public String toString() {
        String result = "";
        for (int i = SIZE - 1; i >= 0; i--) {
            for (int j = 0; j < 10; j++) {
                if (j == 9) {
                    result += " " + currMap.get(Square.sq(j, i)).toString()
                            + "\n";
                } else if (j == 0) {
                    result += "   " + currMap.get(Square.sq(j, i)).toString();
                } else {
                    result += " " + currMap.get(Square.sq(j, i)).toString();
                }
            }
        }
        return result;
    }

}
