package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

/** t : Transmitter ==> BLUE
 *  m : MagicalSnake ==> BLACK
 *  g : GroundSnake ==> GREEN
 *  f : FatalSnake ==> RED
 */




public class Transmitter {
    protected  Cell firstCell, lastCell;
    protected Color color = Color.BLUE;

    public Transmitter(Cell firstCell, Cell lastCell) {
        this.firstCell = firstCell;
        this.lastCell = lastCell;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Cell getFirstCell() {
        return firstCell;
    }

    public Cell getLastCell() {
        return lastCell;
    }

    /**
     * transmit piece to lastCell
     */
    public void transmit(Piece piece) {
        piece.getPlayer().setScore(piece.getPlayer().getScore()-3);
        if (this.lastCell.canEnter(piece)) {
            piece.moveTo(this.lastCell);
            if (piece.getColor()==Color.YELLOW)
            {
                piece.setIsDead(true);
                piece.setIsDead(false);
            }

        }
    }
}
