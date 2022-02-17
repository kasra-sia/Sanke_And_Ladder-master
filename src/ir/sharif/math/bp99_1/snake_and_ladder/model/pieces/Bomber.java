package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.Main;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Bomber extends Piece{
    public Bomber(Player player) {
        super(player,Color.RED);
    }

    @Override
    public void UsePower(Piece piece) {
    Board board = Main.getLogicalAgent().getGameState().getBoard();
    int x = this.getCurrentCell().getX();
    int y = this.getCurrentCell().getY();

        if (board.getCell(x,y).getPrize()!=null)board.getCell(x,y).setPrize(null);

        board.getCell(x,y).setColor(Color.BLACK);

        if (x+1<=7){
            if (board.getCell(x+1,y).getPrize()!=null)board.getCell(x+1,y).setPrize(null);
            if (board.getCell(x+1,y).getPiece()!=null)board.getCell(x+1,y).getPiece().setIsDead(true);
                }
        if (x-1>=1){
            if (board.getCell(x-1,y).getPrize()!=null)board.getCell(x-1,y).setPrize(null);
            if (board.getCell(x-1,y).getPiece()!=null)board.getCell(x-1,y).getPiece().setIsDead(true);
                }

        if (y+1<=16){
            if (board.getCell(x,y+1).getPrize()!=null)board.getCell(x,y+1).setPrize(null);
            if (board.getCell(x,y+1).getPiece()!=null)board.getCell(x,y+1).getPiece().setIsDead(true);
                }

        if (y-1>=1){
            if (board.getCell(x,y-1).getPrize()!=null)board.getCell(x,y-1).setPrize(null);
            if (board.getCell(x,y-1).getPiece()!=null)board.getCell(x,y-1).getPiece().setIsDead(true);
        }

        if (x+1<=7 && y+1<=16){
            if (board.getCell(x+1,y+1).getPrize()!=null)board.getCell(x+1,y+1).setPrize(null);
            if (board.getCell(x+1,y+1).getPiece()!=null)board.getCell(x+1,y+1).getPiece().setIsDead(true);
        }
        if (x+1<=7 && y-1>=1){
            if (board.getCell(x+1,y-1).getPrize()!=null)board.getCell(x+1,y-1).setPrize(null);
            if (board.getCell(x+1,y-1).getPiece()!=null)board.getCell(x+1,y-1).getPiece().setIsDead(true);
        }
        if (x-1>=1 && y+1<=16){
            if (board.getCell(x-1,y+1).getPrize()!=null)board.getCell(x-1,y+1).setPrize(null);
            if (board.getCell(x-1,y+1).getPiece()!=null)board.getCell(x-1,y+1).getPiece().setIsDead(true);
        }
        if (x-1>=1 && y-1>=1){
            if (board.getCell(x-1,y-1).getPrize()!=null)board.getCell(x-1,y-1).setPrize(null);
            if (board.getCell(x-1,y-1).getPiece()!=null)board.getCell(x-1,y-1).getPiece().setIsDead(true);
        }



    this.setIsDead(true);
    this.setHasPower(false);



    }

    @Override
    public boolean IsValidUsePower(Piece piece) {
        if (this.HasPower && !this.IsDead){
            return true;
        }
        return false;
    }
}
