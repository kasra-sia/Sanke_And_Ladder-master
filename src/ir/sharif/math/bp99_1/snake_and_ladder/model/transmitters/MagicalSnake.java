package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class MagicalSnake extends Transmitter{
    private Color color = Color.BLACK;

    public MagicalSnake(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void transmit(Piece piece) {
        if (this.lastCell.canEnter(piece)) {
            piece.moveTo(this.lastCell);
            piece.getPlayer().setScore(piece.getPlayer().getScore()+3);
            piece.setHasPower(true);
            if (piece.getColor()==Color.YELLOW)
            {
                piece.setIsDead(true);
                piece.setIsDead(false);
            }
        }
    }
}
