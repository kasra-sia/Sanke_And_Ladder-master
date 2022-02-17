package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.GameState;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

import java.util.Random;

public class GroundSnake extends Transmitter{
    private Cell lastRandomCell;
    private Color color = Color.GREEN;
    public GroundSnake(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
    }


    public void setLastRandomCell(Cell lastRandomCell) {
        this.lastRandomCell = lastRandomCell;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void transmit(Piece piece) {
        piece.getPlayer().setScore(piece.getPlayer().getScore()-3);
        if (this.lastRandomCell.canEnter(piece)) {
            piece.moveTo(this.lastRandomCell);
            if (piece.getColor()==Color.YELLOW)
            {
                piece.setIsDead(true);
                piece.setIsDead(false);
            }

        }
    }
}
