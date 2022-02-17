package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.Main;
import ir.sharif.math.bp99_1.snake_and_ladder.logic.ModelLoader;
import ir.sharif.math.bp99_1.snake_and_ladder.model.*;

public abstract class Piece  {
    protected Cell currentCell;
    protected final Color color;
    protected Player player;
    protected boolean isSelected;
    protected boolean IsDead;
    protected boolean HasPower;

    public abstract void UsePower(Piece piece);

    public abstract boolean IsValidUsePower(Piece piece);

    public int Distance (Piece piece){
        int distance =0;
        if(piece.getCurrentCell().getY() == this.getCurrentCell().getY()){
           distance=Math.abs(piece.getCurrentCell().getX() - this.getCurrentCell().getX());
           return distance;
        }
        else if (piece.getCurrentCell().getX() == this.getCurrentCell().getX()){
            distance = Math.abs(piece.getCurrentCell().getY() - this.getCurrentCell().getY());
            return distance;
        }
        return 50;

    }

    public void AutoUsePower(){};

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Piece(Player player, Color color) {
        this.color = color;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public boolean isDead() {
        return IsDead;
    }

    public void setIsDead(boolean dead) {
        if (dead==true)this.HasPower=false;
        IsDead = dead;
    }

    public boolean HasPower (){
        return HasPower;
    }

    public void setHasPower(boolean hasPower) {
        HasPower = hasPower;
    }

    /**
     * @return "true" if your movement is valid  , else return " false"
     * <p>
     * In this method, you should check if the movement is valid of not.
     * <p>
     * You can use some methods ( they are recommended )
     * <p>
     * 1) "canEnter" method in class "Cell"
     * <p>
     * if your movement is valid, return "true" , else return " false"
     */
    public boolean isValidMove(Cell destination, int diceNumber) {
//        ModelLoader modelLoader = new ModelLoader();
        Board board = Main.getLogicalAgent().getGameState().getBoard();
//(i = +)
        if(!this.isDead()) {
            if (this.getCurrentCell().getY() == destination.getY()
                    && this.getCurrentCell().getX() + diceNumber == destination.getX()
                    && destination.canEnter(this)) {


                for (int i = 0; i < diceNumber; i++) {

                    int x = this.getCurrentCell().getX() + i;
                    int y = this.getCurrentCell().getY();
                    if (!board.getCell(x, y).getAdjacentOpenCells().contains(board.getCell(x + 1, y))) {
                        return false;
                    }
                }

                return true;
            }
//(i = - )
            if (this.getCurrentCell().getY() == destination.getY()
                    && this.getCurrentCell().getX() - diceNumber == destination.getX()
                    && destination.canEnter(this)) {


                for (int i = 0; i < diceNumber; i++) {

                    int x = this.getCurrentCell().getX() - i;
                    int y = this.getCurrentCell().getY();
                    if (!board.getCell(x, y).getAdjacentOpenCells().contains(board.getCell(x - 1, y))) {
                        return false;
                    }
                }

                return true;
            }

////////////////////////////////////////////////////////////////////////////////////////////
// i =+


            if (this.getCurrentCell().getX() == destination.getX()
                    && this.getCurrentCell().getY() + diceNumber == destination.getY()

                    && destination.canEnter(this)) {

                for (int i = 0; i < diceNumber; i++) {
                    int x = this.getCurrentCell().getX();
                    int y = this.getCurrentCell().getY() + i;
                    if (!board.getCell(x, y).getAdjacentOpenCells().contains(board.getCell(x, y + 1))) {

                        return false;
                    }
                }

                return true;
            }
// i = -

            if (this.getCurrentCell().getX() == destination.getX()
                    && this.getCurrentCell().getY() - diceNumber == destination.getY()

                    && destination.canEnter(this)) {


                for (int i = 0; i < diceNumber; i++) {
                    int x = this.getCurrentCell().getX();
                    int y = this.getCurrentCell().getY() - i;
                    if (!board.getCell(x, y).getAdjacentOpenCells().contains(board.getCell(x, y - 1))) {

                        return false;
                    }
                }

                return true;
            }
        }
        return false;
    }

    /**
     * @param destination move selected piece from "currentCell" to "destination"
     */
    public void moveTo(Cell destination) {

            this.getCurrentCell().setPiece(null);
            destination.setPiece(this);
            this.setCurrentCell(destination);

    }
}
