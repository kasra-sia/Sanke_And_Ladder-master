package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.logic.LogicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.FatalSnake;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.GroundSnake;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.MagicalSnake;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.io.*;
import java.util.Scanner;

public class GameState {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private int turn;

    public GameState(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        turn = 0;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getPlayer(int i) {
        if (i == 1) return player1;
        else if (i == 2) return player2;
        else return null;
    }

    public boolean isStarted() {
        return turn != 0;
    }

    public int getTurn() {
        return turn;
    }


    /**
     * return null if game is not started.
     * else return a player who's turn is now.
     */
////
    public Player getCurrentPlayer() {
        if (!isStarted()) {
            return null;
        } else {
            if (turn % 2 == 0) {
                return player2;
            } else {
                return player1;
            }
        }
    }


    /**
     * finish current player's turn and update some fields of this class;
     * you can use method "endTurn" in class "Player" (not necessary, but recommended)
     */
    public void nextTurn() {
        LogicalAgent.Click=0;
        LogicalAgent.Click1=0;
        if(turn > 0)
        getCurrentPlayer().getDice().setPreviousNumber(getCurrentPlayer().getDice().getCurrentNumber());
        if (turn==0)turn=1;
        else {
            getCurrentPlayer().endTurn();
            turn++;
        }
        for (Piece piece:this.getCurrentPlayer().getPieces() ) {
            if (piece.isSelected())piece.setSelected(false);
        }
        saveGame();
    }


    @Override
    public String toString() {
        return "GameState{" +
                "board=" + board +
                ", playerOne=" + player1 +
                ", playerTwo=" + player2 +
                ", turn=" + turn +
                '}';
    }

    public void saveGame() {
        File file = new File("resources/ir/sharif/math/bp99_1/snake_and_ladder/Temp GameLoad/" + player1.getName() + "-" + player2.getName() + ".txt");
        try {
            file.createNewFile();
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.println(turn);
            printStream.println(board.getCells().size());
            for (Cell cell : board.getCells()) {
                printStream.println(cell.toString());
                printStream.println(cell.getColor());
            }
//transmitter
            printStream.println(board.getTransmitters().size());
            for (Transmitter transmitter : board.getTransmitters()) {
                printStream.println(transmitter.getFirstCell().toString());
                printStream.println(transmitter.getLastCell().toString());
                printStream.println(transmitter.getColor());
            }
// wall
            printStream.println(board.getWalls().size());
            for (Wall wall : board.getWalls()) {
                printStream.println(wall.getCell1().toString());
                printStream.println(wall.getCell2().toString());
            }
//Prize
            int sz = 0;
            for (Cell cell : board.getCells())
                if (cell.getPrize() != null)
                    sz++;
            printStream.println(sz);
            for (Cell cell : board.getCells())
                if (cell.getPrize() != null)
                    printStream.println(cell.getPrize().toString());
//player 1 : a
//player 2 : b
            for (Player player : new Player[]{player1, player2}) {
                printStream.println(player.getName());
                printStream.println(player.getScore());
                printStream.println(player.getSumScore());
                printStream.println(player.getPlayerNumber());
                printStream.println(player.getPieces().size());
//---Piece
                for (Piece piece : new Piece[]{player.getBomber(), player.getSniper(), player.getHealer(), player.getRobber()}) {
                    printStream.println(piece.getCurrentCell().toString());
                    printStream.println(piece.getColor());
                    printStream.println(piece.isDead());
                    printStream.println(piece.HasPower());
                }
//---robber
                printStream.println(player.getRobber().hasPrize());
                if (player.getRobber().hasPrize())
                    printStream.println(player.getRobber().getPrize().toString());
//---Dice
                printStream.println(player.getDice().toKasraString());
                printStream.println(player.isDicePlayedThisTurn());
                printStream.println(player.isReady());
                printStream.println(player.getMoveLeft());
            }
            printStream.flush();
            printStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean loadGame() {
        File file = new File("resources/ir/sharif/math/bp99_1/snake_and_ladder/Temp GameLoad/" + player1.getName() + '-' + player2.getName() + ".txt");
        if (file.exists())
            try {
                Scanner scanner = new Scanner(file);
                turn = scanner.nextInt();
                board.getCells().clear();
                board.getTransmitters().clear();
                board.getWalls().clear();
                board.getStartingCells().clear();
//set cells
                int sz = scanner.nextInt();
                for (int i = 0; i < sz; i++) {
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    Color color = Color.WHITE;
                    String c = scanner.next();
                    if (c.equals("RED"))
                        color = Color.RED;
                    if (c.equals("YELLOW"))
                        color = Color.YELLOW;
                    if (c.equals("GREEN"))
                        color = Color.GREEN;
                    if (c.equals("BLUE"))
                        color = Color.BLUE;
                    if (c.equals("BLACK"))
                        color = Color.BLACK;
                    board.getCells().add(new Cell(color, x, y));
                }
//set transmitter
                sz = scanner.nextInt();
                for (int i = 0; i < sz; i++) {
                    int x1 = scanner.nextInt(), y1 = scanner.nextInt();
                    int x2 = scanner.nextInt(), y2 = scanner.nextInt();
                    Color color = Color.WHITE;
                    String c = scanner.next();
                    if (c.equals("RED")){
                        color = Color.RED;
                        FatalSnake transmitter = new FatalSnake(board.getCell(x1, y1), board.getCell(x2, y2));
                        transmitter.setColor(color);
                        transmitter.getFirstCell().setTransmitter(transmitter);
                        transmitter.getLastCell().setTransmitter(transmitter);
                        board.getTransmitters().add(transmitter);
                    }
//                    if (c.equals("YELLOW"))
//                        color = Color.YELLOW;
                    if (c.equals("GREEN")){
                        color = Color.GREEN;
                        GroundSnake transmitter = new GroundSnake(board.getCell(x1, y1), board.getCell(x2, y2));
                        transmitter.setColor(color);
                        transmitter.getFirstCell().setTransmitter(transmitter);
                        transmitter.getLastCell().setTransmitter(transmitter);
                        board.getTransmitters().add(transmitter);
                    }
                    if (c.equals("BLUE")){
                        color = Color.BLUE;
                        Transmitter transmitter = new Transmitter(board.getCell(x1, y1), board.getCell(x2, y2));
                        transmitter.setColor(color);
                        transmitter.getFirstCell().setTransmitter(transmitter);
                        transmitter.getLastCell().setTransmitter(transmitter);
                        board.getTransmitters().add(transmitter);
                    }
                    if (c.equals("BLACK")) {
                        color = Color.BLACK;
                        MagicalSnake transmitter = new MagicalSnake(board.getCell(x1, y1), board.getCell(x2, y2));
                        transmitter.setColor(color);
                        transmitter.getFirstCell().setTransmitter(transmitter);
                        transmitter.getLastCell().setTransmitter(transmitter);
                        board.getTransmitters().add(transmitter);
                    }
                }
//wall
                sz = scanner.nextInt();
                for (int i = 0; i < sz; i++) {
                    int x1 = scanner.nextInt(), y1 = scanner.nextInt();
                    int x2 = scanner.nextInt(), y2 = scanner.nextInt();
                    board.getWalls().add(new Wall(board.getCell(x1, y1), board.getCell(x2, y2)));
                }
//AdjacentCells
                for (Cell cell: board.getCells()) {
                    if(board.getCell(cell.getX()+1,cell.getY()) != null)
                        cell.getAdjacentCells().add(board.getCell(cell.getX()+1,cell.getY()));
                    if(board.getCell(cell.getX()-1,cell.getY()) != null)
                        cell.getAdjacentCells().add(board.getCell(cell.getX()-1,cell.getY()));
                    if(board.getCell(cell.getX(),cell.getY()+1) != null)
                        cell.getAdjacentCells().add(board.getCell(cell.getX(),cell.getY()+1));
                    if(board.getCell(cell.getX(),cell.getY()-1) != null)
                        cell.getAdjacentCells().add(board.getCell(cell.getX(),cell.getY()-1));
                }

//AdjacentOpenCells
                for (Cell cell : board.getCells()) {
                    cell.getAdjacentOpenCells().addAll(cell.getAdjacentCells());
                    for (Cell cell1 : cell.getAdjacentCells()) {
                        int index = cell.getAdjacentOpenCells().indexOf(cell1);

                        for (Wall wall : board.getWalls() ) {
                            if (wall.getCell1()==cell && wall.getCell2()==cell1)
                                cell.getAdjacentOpenCells().remove(index);
                        }
                    }
                }
//set prize
                sz = scanner.nextInt();
                for (int i = 0; i < sz; i++) {
                    int x = scanner.nextInt(), y = scanner.nextInt(), point = scanner.nextInt();
                    int chance = scanner.nextInt(), diceNumber = scanner.nextInt();
                    board.getCell(x, y).setPrize(new Prize(board.getCell(x, y), point, chance, diceNumber));
                }
//Set player
                for (int t = 0; t < 2; t++) {
                    Player player = (t == 0 ? player1 : player2);
                    player.setName(scanner.next());
                    player.setScore(scanner.nextInt());
                    player.setSumScore(scanner.nextInt());
                    player.setPlayerNumber(scanner.nextInt());
                    sz = scanner.nextInt();
                    for (int i = 0; i < sz; i++) {
                        int x = scanner.nextInt(), y = scanner.nextInt();
                        String c = scanner.next();
                        boolean isDead = scanner.nextBoolean();
                        boolean hasPower = scanner.nextBoolean();
                        if (c.equals("RED")) {
                            Bomber bomber = player.getBomber();
                            bomber.setCurrentCell(board.getCell(x, y));
                            bomber.setPlayer(player);
                            bomber.setIsDead(isDead);
                            bomber.setHasPower(hasPower);
                            board.getCell(x, y).setPiece(bomber);
                        }
                        if (c.equals("YELLOW")) {
                            Robber robber = player.getRobber();
                            robber.setCurrentCell(board.getCell(x, y));
                            robber.setPlayer(player);
                            robber.setIsDead(isDead);
                            robber.setHasPower(hasPower);
                            board.getCell(x, y).setPiece(robber);
                        }
                        if (c.equals("GREEN")) {
                            Healer healer = player.getHealer();
                            healer.setCurrentCell(board.getCell(x, y));
                            healer.setPlayer(player);
                            healer.setIsDead(isDead);
                            healer.setHasPower(hasPower);
                            board.getCell(x, y).setPiece(healer);
                        }
                        if (c.equals("BLUE")) {
                            Sniper sniper = player.getSniper();
                            sniper.setCurrentCell(board.getCell(x, y));
                            sniper.setPlayer(player);
                            sniper.setIsDead(isDead);
                            sniper.setHasPower(hasPower);
                            board.getCell(x, y).setPiece(sniper);
                        }
                    }
                    player.getRobber().setHasPrize(scanner.nextBoolean());
                    if (player.getRobber().hasPrize()) {
                        int x = scanner.nextInt(), y = scanner.nextInt(), point = scanner.nextInt();
                        int chance = scanner.nextInt(), diceNumber = scanner.nextInt();
                        player.getRobber().setTempPrize(new Prize(board.getCell(x, y), point, chance, diceNumber));
                    }
                    for (int i = 0; i < 6; i++) {
                        int number = scanner.nextInt();
                        int chance = scanner.nextInt();
                        player.getDice().addChance(number, chance - 1);
                    }
                    player.setDicePlayedThisTurn(scanner.nextBoolean());
                    player.setIsReady(scanner.nextBoolean());
                    player.setMoveLeft(scanner.nextInt());
                }
                scanner.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return false;
    }
}
