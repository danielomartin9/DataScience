package amazons;

import java.util.Iterator;

import static amazons.Piece.*;

/** A Player that automatically generates moves.
 *  @author Daniel Martin
 */
class AI extends Player {

    /**
     * A position magnitude indicating a win (for white if positive, black
     * if negative).
     */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /**
     * A magnitude greater than a normal value.
     */
    private static final int INFTY = Integer.MAX_VALUE;

    /**
     * A new AI with no piece or controller (intended to produce
     * a template).
     */
    AI() {
        this(null, null);
    }

    /**
     * A new AI playing PIECE under control of CONTROLLER.
     */
    AI(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new AI(piece, controller);
    }

    @Override
    String myMove() {
        Move move = findMove();
        _controller.reportMove(move);
        return move.toString();
    }

    /**
     * Return a move for me from the current position, assuming there
     * is a move.
     */
    private Move findMove() {
        Board b = new Board(board());
        if (_myPiece == WHITE) {
            findMove(b, maxDepth(b), true, 1, -INFTY, INFTY);
        } else {
            findMove(b, maxDepth(b), true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /**
     * The move found by the last call to one of the ...FindMove methods
     * below.
     */
    private Move _lastFoundMove;

    /**
     * Find a move from position BOARD and return its value, recording
     * the move found in _lastFoundMove iff SAVEMOVE. The move
     * should have maximal value or have value > BETA if SENSE==1,
     * and minimal value or value < ALPHA if SENSE==-1. Searches up to
     * DEPTH levels.  Searching at level 0 simply returns a static estimate
     * of the board value and does not set _lastMoveFound.
     */

    private int findMove(Board board, int depth, boolean saveMove, int sense,
            int alpha, int beta) {
        if (depth == 0 || board.winner() != null) {
            return staticScore(board);
        }
        int maximum = -INFTY;
        int minimum = INFTY;
        Iterator<Move> iter = board.legalMoves();
        if (sense == 1) {
            while (iter.hasNext()) {
                Move m = iter.next();
                board.makeMove(m);
                int val = findMove(board, depth - 1,
                        false, -1, alpha, beta);
                board.undo();
                if (saveMove && val > maximum) {
                    _lastFoundMove = m;
                }
                if (maximum > val) {
                    maximum = maximum;
                } else {
                    maximum = val;
                }
                if (maximum >= beta) {
                    return maximum;
                }
                if (alpha > maximum) {
                    alpha = alpha;
                } else {
                    alpha = maximum;
                }
            }
            return maximum;

        } else {
            while (iter.hasNext()) {
                Move m = iter.next();
                board.makeMove(m);
                int val = findMove(board, depth - 1,
                        false, 1, alpha, beta);
                board.undo();
                if (saveMove && val < minimum) {
                    _lastFoundMove = m;
                }
                if (minimum < val) {
                    minimum = minimum;
                } else {
                    minimum = val;
                }
                if (minimum <= alpha) {
                    return minimum;
                }
                if (beta < minimum) {
                    beta = beta;
                } else {
                    beta = minimum;
                }
            }
            return minimum;
        }
    }

    /** Return a heuristically determined maximum search depth
     *  based on characteristics of BOARD. */
    private int maxDepth(Board board) {
        int N = board.numMoves();
        return 1;
    }

    /** Method that gives the heuristic value.
     * @return result
     * @param board board
     * */
    private int staticScore(Board board) {
        int oppSquares = 0;
        int turnSquares = 0;

        for (int i = 0; i < 10 * 10 - 1; i++) {
            Square temp = Square.sq(i);
            Iterator<Square> iter = board.reachableFrom(temp, null);

            if (board.get(temp).equals(Piece.BLACK)) {
                while (iter.hasNext()) {
                    iter.next();
                    oppSquares = oppSquares + 1;
                }
            } else if (board.get(temp).equals(Piece.WHITE)) {
                while (iter.hasNext()) {
                    iter.next();
                    turnSquares = turnSquares + 1;
                }
            }
        }
        int result = turnSquares - oppSquares;
        return result;
    }
}
