package ir.sharif.math.bp99_1.snake_and_ladder.logic;


import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.GameState;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.GroundSnake;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class is an interface between logic and graphic.
 * some methods of this class, is called from graphic.
 * DO NOT CHANGE ANY PART WHICH WE MENTION.
 */
public class LogicalAgent {
    private final ModelLoader modelLoader;
    private final GraphicalAgent graphicalAgent;
    private final GameState gameState;
    private static Board board;
    public static int Click = 0;
    public static int Click1 = 0;

    public static Board getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameState;
    }

    /**
     * DO NOT CHANGE CONSTRUCTOR.
     */
    public LogicalAgent() {
        this.graphicalAgent = new GraphicalAgent(this);
        this.modelLoader = new ModelLoader();
        this.gameState = loadGameState();
    }


    /**
     * NO CHANGES NEEDED.
     */
    private GameState loadGameState() {
        this.board = modelLoader.loadBord();
        Player player1 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(1), 1);
        Player player2;
        do {
            player2 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(2), 2);
        } while (player1.equals(player2));
        player1.setRival(player2);
        player2.setRival(player1);
        return new GameState(board, player1, player2);
    }

    /**
     * NO CHANGES NEEDED.
     */
    public void initialize() {
        graphicalAgent.initialize(gameState);
    }

    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who clicks "ReadyButton".) you should somehow change that player state.
     * if both players are ready. then start the game.
     */
    public void readyPlayer(int playerNumber) {
        Player player = gameState.getPlayer(playerNumber);
        if (player.isReady()) player.setReady(false);
        else player.setReady(true);

        if (gameState.getPlayer1().isReady() && gameState.getPlayer2().isReady()) {
            if (!gameState.loadGame()) {
                Map<Cell, Integer> map = gameState.getBoard().getStartingCells();
                int i = 0;

                for (Cell cell : map.keySet()) {
                    if (i == 4) i = 0;
                    //                Piece piece = new Piece(gameState.getPlayer(map.get(cell)), cell.getColor());
                    if (i == 0) {
                        gameState.getPlayer(map.get(cell)).getPieces().
                                set(i, gameState.getPlayer(map.get(cell)).getBomber());
                    }

                    if (i == 1) {
                        gameState.getPlayer(map.get(cell)).getPieces().
                                set(i, gameState.getPlayer(map.get(cell)).getSniper());
                    }

                    if (i == 2) {
                        gameState.getPlayer(map.get(cell)).getPieces().
                                set(i, gameState.getPlayer(map.get(cell)).getHealer());
                    }

                    if (i == 3) {
                        gameState.getPlayer(map.get(cell)).getPieces().
                                set(i, gameState.getPlayer(map.get(cell)).getRobber());
                    }


                    cell.setPiece(gameState.getPlayer(map.get(cell)).getPieces().get(i));
                    gameState.getPlayer(map.get(cell)).getPieces().get(i).setCurrentCell(cell);
                    //                System.out.println(cell.getColor());


                    i++;
                }
//            System.out.println(gameState.getBoard().getCell(1,2).getAdjacentOpenCells().size());

                gameState.nextTurn();
            }
        }

//         dont touch this line
        graphicalAgent.update(gameState);
    }

    /**
     * give x,y (coordinates of a cell) :
     * you should handle if user want to select a piece
     * or already selected a piece and now want to move it to a new cell
     */
    // ***
    public void selectCell(int x, int y) {
        if (gameState.getCurrentPlayer().getBomber().getCurrentCell()
           == gameState.getBoard().getCell(x,y))Click++;

        if (gameState.getCurrentPlayer().getRobber().getCurrentCell()
                == gameState.getBoard().getCell(x,y))Click1++;

        if (gameState.getBoard().getCell(x, y).getPiece() != null
                && gameState.getCurrentPlayer().getPieces().contains(gameState.getBoard().getCell(x, y).getPiece())
        ) {
            if (gameState.getBoard().getCell(x, y).getPiece().isSelected())
                gameState.getBoard().getCell(x, y).getPiece().setSelected(false);
            else gameState.getBoard().getCell(x, y).getPiece().setSelected(true);
        }
        if (gameState.getBoard().getCell(x, y).getPiece() == null) {
            for (Piece piece : gameState.getCurrentPlayer().getPieces()) {

                if (piece.isSelected()
                        && piece.isValidMove(gameState.getBoard().getCell(x, y), gameState.getCurrentPlayer().getDice().getCurrentNumber())) {


                    piece.moveTo(gameState.getBoard().getCell(x, y));

                    if (piece.getColor() == gameState.getBoard().getCell(x, y).getColor())
                        gameState.getCurrentPlayer().applyOnScore(4);
//Transmitter
                    if (gameState.getBoard().getCell(x, y).getTransmitter() != null) {
                        Random random = new Random();

                        int x1 = random.nextInt(7) + 1;
                        int y1 = random.nextInt(16) + 1;
                        try {
                            ((GroundSnake) (gameState.getBoard().getCell(x, y).getTransmitter())).setLastRandomCell(gameState.getBoard().getCell(x1, y1));
                        } catch (Exception e) {}
                        gameState.getBoard().getCell(x, y).getTransmitter().transmit(piece);
                    }

                    if (gameState.getBoard().getCell(x, y).getPrize() != null)
                        gameState.getCurrentPlayer().usePrize(gameState.getBoard().getCell(x, y).getPrize());



//AutoPower
                    for (Piece piece1 : gameState.getCurrentPlayer().getPieces()) {
                        piece1.AutoUsePower();
                    }
                    gameState.nextTurn();
                    piece.setSelected(false);
                }


            }

        }
//Healer
        if(gameState.getBoard().getCell(x,y).getPiece()!=null
           && gameState.getCurrentPlayer().getPieces().contains(gameState.getBoard().getCell(x, y).getPiece())
           && gameState.getCurrentPlayer().getHealer().isSelected()) {
            Healer healer = gameState.getCurrentPlayer().getHealer();
            Piece piece = gameState.getBoard().getCell(x, y).getPiece();
            System.out.println(1);
            if (healer.IsValidUsePower(piece)) {
                System.out.println(2);
                healer.UsePower(piece);
                gameState.nextTurn();
                piece.setSelected(false);
                healer.setSelected(false);
            }
        }

//Sniper
        if(gameState.getBoard().getCell(x,y).getPiece() != null
          && gameState.getCurrentPlayer().getRival().getPieces().contains(gameState.getBoard().getCell(x,y).getPiece())
          && gameState.getCurrentPlayer().getSniper().isSelected()){
            Sniper sniper = gameState.getCurrentPlayer().getSniper();
            Piece piece = gameState .getBoard().getCell(x,y).getPiece();
            if (sniper.IsValidUsePower(piece)){
                sniper.UsePower(piece);
                gameState.nextTurn();
                sniper.setSelected(false);
                piece.setSelected(false);
            }

        }
//Bomber
        if (Click==2
           && gameState.getCurrentPlayer().getBomber().IsValidUsePower(gameState.getBoard().getCell(x,y).getPiece()))
        {
            gameState.getCurrentPlayer().getBomber().UsePower(gameState.getBoard().getCell(x,y).getPiece());
            gameState.nextTurn();
        }
//Robber
        if (Click1 == 2
        && gameState.getCurrentPlayer().getRobber().IsValidUsePower(gameState.getBoard().getCell(x,y).getPiece()))
        {
            gameState.getCurrentPlayer().getRobber().UsePower(gameState.getBoard().getCell(x,y).getPiece());
            gameState.nextTurn();
        }

        // dont touch this line
        graphicalAgent.update(gameState);
        checkForEndGame();
    }

    public String getCellDetails(int x, int y) {
        return "call out " + x + "," + y;
    }

    /**
     * check for endgame and specify winner
     * if player one in winner set winner variable to 1
     * if player two in winner set winner variable to 2
     * If the game is a draw set winner variable to 3
     */
    private void checkForEndGame() {
        if (gameState.getTurn() == 41) {
            int winner = 0;
            if (gameState.getPlayer1().getScore() > gameState.getPlayer2().getScore()) {
                winner = 1;
            }
            if (gameState.getPlayer2().getScore() > gameState.getPlayer1().getScore()) {
                winner = 2;
            }
            if (gameState.getPlayer1().getScore() == gameState.getPlayer2().getScore()) {
                winner = 3;
            }
            // your code

            File file = new File("resources/ir/sharif/math/bp99_1/snake_and_ladder/Temp GameLoad/" + gameState.getPlayer1().getName() + "-" + gameState.getPlayer2().getName() + ".txt");
            file.delete();


            System.out.println(winner);

            // dont touch it
            graphicalAgent.playerWin(winner);
            /* save players*/
            modelLoader.savePlayer(gameState.getPlayer1());
            modelLoader.savePlayer(gameState.getPlayer2());
            modelLoader.archive(gameState.getPlayer1(), gameState.getPlayer2());
            LogicalAgent logicalAgent = new LogicalAgent();
            logicalAgent.initialize();
        }
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who left clicks "dice button".) you should roll his/her dice
     * and update *****************
     */
    public void rollDice(int playerNumber) {
        if (!gameState.getPlayer(playerNumber).isDicePlayedThisTurn() && gameState.getPlayer(playerNumber)
                == gameState.getCurrentPlayer()) {
            gameState.getPlayer(playerNumber).setMoveLeft(gameState.getPlayer(playerNumber).getDice().roll());

            if (gameState.getPlayer(playerNumber).getDice().getCurrentNumber() == 6) {
                gameState.getPlayer(playerNumber).setScore(gameState.getPlayer(playerNumber).getScore() + 4);
            }
//Healer
            if (gameState.getPlayer(playerNumber).getDice().getCurrentNumber() == 1)
                gameState.getCurrentPlayer().getHealer().setHasPower(true);
//Sniper
            if (gameState.getPlayer(playerNumber).getDice().getCurrentNumber() == 5)
                gameState.getPlayer(playerNumber).getSniper().setHasPower(true);
//Bomber
            if (gameState.getPlayer(playerNumber).getDice().getCurrentNumber() == 3)
                gameState.getPlayer(playerNumber).getBomber().setHasPower(true);

            gameState.getPlayer(playerNumber).setDicePlayedThisTurn(true);

            if (!gameState.getPlayer(playerNumber).hasMove(gameState.getBoard(), gameState.getPlayer(playerNumber).getDice().getCurrentNumber())
              ) {
                gameState.getPlayer(playerNumber).applyOnScore(-3);




                gameState.nextTurn();
                graphicalAgent.update(gameState);
                checkForEndGame();
            }
            if (gameState.getCurrentPlayer().getDice().getCurrentNumber()
            == gameState.getCurrentPlayer().getDice().getPreviousNumber()){
                gameState.getCurrentPlayer().getDice().addChance(gameState.getCurrentPlayer().getDice().getCurrentNumber()
                ,1);
                gameState.getCurrentPlayer().getDice().setPreviousNumber(0);
            }

        }

        // dont touch this line
        graphicalAgent.update(gameState);
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who right clicks "dice button".) you should return the dice detail of that player.
     * you can use method "getDetails" in class "Dice"(not necessary, but recommended )
     */
    public String getDiceDetail(int playerNumber) {

        return gameState.getPlayer(playerNumber).getDice().getDetails();
    }
}
