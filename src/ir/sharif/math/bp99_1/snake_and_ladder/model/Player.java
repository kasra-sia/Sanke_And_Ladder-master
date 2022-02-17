package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private String name;
    private int score;
    private int sumScore;
    private final List<Piece> pieces;
    private final Dice dice;
    private Player rival;
    private final int id;
    private int playerNumber;
    private boolean isReady;
    private boolean dicePlayedThisTurn;
    private int moveLeft;
    private Piece selectedPiece;

    private Healer healer;
    private Bomber bomber;
    private Robber robber;
    private Sniper sniper;

    public void setHealer(Healer healer) {
        this.healer = healer;
    }
    public void setBomber(Bomber bomber) {
        this.bomber = bomber;
    }
    public void setRobber(Robber robber) {
        this.robber = robber;
    }
    public void setSniper(Sniper sniper) {
        this.sniper = sniper;
    }

    public void setIsReady(boolean isReady) {
        this.isReady = isReady;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player(String name, int score, int id, int playerNumber) {
        this.name = name;
        this.score = score;
        this.id = id;
        this.playerNumber = playerNumber;
        this.dice = new Dice();
        this.pieces = new ArrayList<>();
        this.healer = new Healer(this);
        this.bomber = new Bomber(this);
        this.sniper = new Sniper(this);
        this.robber = new Robber(this);
        this.pieces.add(this.bomber);
        this.pieces.add(this.sniper);
        this.pieces.add(this.healer);
        this.pieces.add(this.robber);
        this.moveLeft = 0;
        this.selectedPiece = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Dice getDice() {
        return dice;
    }

    public int getScore() {
        return score;
    }

    public int getSumScore() { return sumScore;}

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Player getRival() {
        return rival;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public boolean isDicePlayedThisTurn() {
        return dicePlayedThisTurn;
    }

    public void setDicePlayedThisTurn(boolean dicePlayedThisTurn) {
        this.dicePlayedThisTurn = dicePlayedThisTurn;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public void setMoveLeft(int moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setRival(Player rival) {
        this.rival = rival;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public  void setSumScore(int sumScore){ this.sumScore = sumScore;}

    public void applyOnScore(int score) {
        this.score += score;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public Bomber getBomber() {
        return bomber;
    }

    public Sniper getSniper() {
        return sniper;
    }

    public Healer getHealer() {
        return healer;
    }

    public Robber getRobber() {
        return robber;
    }



    /**
     * @param prize according to input prize , apply necessary changes to score and dice chance
     *              <p>
     *              you can use method "addChance" in class "Dice"(not necessary, but recommended)
     */
    public void usePrize(Prize prize) {
        score += prize.getPoint();
        dice.addChance( prize.getChance(),prize.getDiceNumber());
        System.out.println(prize.getChance());
    }


    /**
     * check if any of player pieces can move to another cell.
     *
     * @return true if at least 1 piece has a move , else return false
     * <p>
     * you can use method "isValidMove" in class "Piece"(not necessary, but recommended)
     */
    public boolean hasMove(Board board, int diceNumber) {
        for (Piece x :pieces) {
            for (Cell cell: board.getCells()) {
                if (x.isValidMove(cell,diceNumber)
                  || this.healer.IsValidUsePower(x)
                  )
                    return true;

            }
        }

        return false;
    }


    /**
     * Deselect selectedPiece and make some changes in this class fields.
     */
    // **
    public void endTurn() {
        if (selectedPiece != null) {
            selectedPiece.setSelected(false);
        }
            selectedPiece = null;
        moveLeft = 0;
        dicePlayedThisTurn = false;
    }


    /**
     * DO NOT CHANGE FOLLOWING METHODS.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

