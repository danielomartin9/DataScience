package amazons;

import static amazons.Move.isGrammaticalMove;

/** A Player that takes input as text commands from the standard input.
 *  @author Daniel Martin
 */
class TextPlayer extends Player {

    /** A new TextPlayer with no piece or controller (intended to produce
     *  a template). */
    TextPlayer() {
        this(null, null);
    }

    /** A new TextPlayer playing PIECE under control of CONTROLLER. */
    private TextPlayer(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new TextPlayer(piece, controller);
    }

    @Override
    String myMove() {
        while (true) {
            String line = _controller.readLine();
            if (line == null) {
                return "quit";
            } else if (isGrammaticalMove(line)) {
                if (_controller.board().winner() != null
                        || !_controller.board().turn().equals(_myPiece)) {
                    _controller.reportError("Invalid move. "
                            + "Please try again.");
                    continue;
                }
                return line;
            } else {
                return line;
            }
        }
    }
}
