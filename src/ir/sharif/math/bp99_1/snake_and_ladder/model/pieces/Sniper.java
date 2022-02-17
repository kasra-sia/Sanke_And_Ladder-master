package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.Main;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Sniper extends Piece{
    public Sniper(Player player) {
        super(player, Color.BLUE);
    }


    @Override
    public void UsePower(Piece piece) {
        piece.setIsDead(true);
        this.HasPower = false;

    }

    @Override
    public boolean IsValidUsePower(Piece piece) {
        if(this.HasPower
                && this.Distance(piece)<= this.getPlayer().getDice().getCurrentNumber()
                && !piece.isDead()
                && !Main.getLogicalAgent().getGameState().getCurrentPlayer().getPieces().contains(piece)
                && !this.isDead()){


            return true;
        }
        return false;
    }
}
