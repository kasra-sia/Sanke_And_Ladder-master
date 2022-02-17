package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.Main;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

import java.time.temporal.Temporal;

public class Robber extends Piece {
    boolean HasPrize;
    Prize TempPrize;

    public Robber(Player player) {
        super(player, Color.YELLOW);
        this.HasPower = false;

    }

    @Override
    public void UsePower(Piece piece) {
        if (!HasPrize && piece.getCurrentCell().getPrize() != null) {
            TempPrize = piece.getCurrentCell().getPrize();
            piece.getCurrentCell().setPrize(null);
            HasPrize = true;
        } else if (HasPrize) {
            piece.getCurrentCell().setPrize(TempPrize);
            TempPrize = null;
            HasPrize = false;
        }
        if (HasPrize && piece.getCurrentCell().getPrize() != null) {
            piece.getCurrentCell().setPrize(null);
            piece.getCurrentCell().setPrize(TempPrize);
            TempPrize = null;
            HasPrize = false;
        }
    }

    @Override
    public boolean IsValidUsePower(Piece piece) {
        if (!this.isDead()) {
            if (piece.getCurrentCell().getPrize() != null || HasPrize)
                return true;
        }
        return false;
    }

    @Override
    public void setHasPower(boolean hasPower) {
        super.setHasPower(true);
    }

    public boolean hasPrize() {
        return HasPrize;
    }
    public Prize getPrize() {
        return TempPrize;
    }

    @Override
    public boolean isValidMove(Cell destination, int diceNumber) {
//        ModelLoader modelLoader = new ModelLoader();
        Board board = Main.getLogicalAgent().getGameState().getBoard();
//(i = +)
        if (!this.isDead()) {
            if (this.getCurrentCell().getY() == destination.getY()
                    && this.getCurrentCell().getX() + diceNumber == destination.getX()) {


                for (int i = 0; i < diceNumber; i++) {

                    int x = this.getCurrentCell().getX() + i;
                    int y = this.getCurrentCell().getY();
                    if (!board.getCell(x, y).getAdjacentCells().contains(board.getCell(x + 1, y))) {
                        return false;
                    }
                }

                return true;
            }
//(i = - )
            if (this.getCurrentCell().getY() == destination.getY()
                    && this.getCurrentCell().getX() - diceNumber == destination.getX()
            ) {


                for (int i = 0; i < diceNumber; i++) {

                    int x = this.getCurrentCell().getX() - i;
                    int y = this.getCurrentCell().getY();
                    if (!board.getCell(x, y).getAdjacentCells().contains(board.getCell(x - 1, y))) {
                        return false;
                    }
                }

                return true;
            }

////////////////////////////////////////////////////////////////////////////////////////////
// i =+


            if (this.getCurrentCell().getX() == destination.getX()
                    && this.getCurrentCell().getY() + diceNumber == destination.getY()

            ) {

                for (int i = 0; i < diceNumber; i++) {
                    int x = this.getCurrentCell().getX();
                    int y = this.getCurrentCell().getY() + i;
                    if (!board.getCell(x, y).getAdjacentCells().contains(board.getCell(x, y + 1))) {

                        return false;
                    }
                }

                return true;
            }
// i = -

            if (this.getCurrentCell().getX() == destination.getX()
                    && this.getCurrentCell().getY() - diceNumber == destination.getY()

            ) {


                for (int i = 0; i < diceNumber; i++) {
                    int x = this.getCurrentCell().getX();
                    int y = this.getCurrentCell().getY() - i;
                    if (!board.getCell(x, y).getAdjacentCells().contains(board.getCell(x, y - 1))) {

                        return false;
                    }
                }

                return true;
            }
        }
        return false;
    }

    @Override
    public void setIsDead(boolean dead) {
        if (IsDead == true) {
            HasPrize = false;
            TempPrize = null;
        }
        super.setIsDead(dead);
    }

    public void setHasPrize(boolean hasPrize) {
        HasPrize = hasPrize;
    }
    public void setTempPrize(Prize prize) {
        TempPrize = prize;
    }
}
