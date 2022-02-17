package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.Main;
import ir.sharif.math.bp99_1.snake_and_ladder.logic.LogicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.GameState;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Healer extends Piece {
    final boolean IsDead;
    public Healer(Player player) {
        super(player, Color.GREEN);
        this.IsDead = false;

    }

    @Override
    public void UsePower(Piece piece) {
        piece.setIsDead(false);
        this.HasPower = false;


    }
    @Override
    public boolean IsValidUsePower(Piece piece){

        if(this.HasPower
                && this.Distance(piece)<= this.getPlayer().getDice().getCurrentNumber()
                && piece.isDead()){
            return true;
        }
        return false;
    }

    @Override
    public void AutoUsePower() {
        for (Piece piece : Main.getLogicalAgent().getGameState().getCurrentPlayer().getPieces()) {
            if (this.Distance(piece)==4 && this.HasPower)
                piece.setIsDead(false);

        }
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void setIsDead(boolean dead) {
        super.setIsDead(false);
    }


}




