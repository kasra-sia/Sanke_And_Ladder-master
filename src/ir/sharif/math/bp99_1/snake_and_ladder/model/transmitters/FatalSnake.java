package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class FatalSnake extends Transmitter{
    Color color = Color.RED;

    @Override
    public Color getColor() {
        return color;
    }

    public FatalSnake(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);

    }

    @Override
    public void transmit(Piece piece) {
        super.transmit(piece);
        piece.setIsDead(true);
    }
}
