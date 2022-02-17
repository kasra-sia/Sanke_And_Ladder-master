package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.*;
import java.util.Scanner;

public class ModelLoader {
    private final File boardFile, playersDirectory, archiveFile;


    /**
     * DO NOT CHANGE ANYTHING IN CONSTRUCTOR.
     */
    public ModelLoader() {
        boardFile = Config.getConfig("mainConfig").getProperty(File.class, "board");
        playersDirectory = Config.getConfig("mainConfig").getProperty(File.class, "playersDirectory");
        archiveFile = Config.getConfig("mainConfig").getProperty(File.class, "archive");
        if (!playersDirectory.exists()) playersDirectory.mkdirs();
    }


    /**
     * read file "boardFile" and craete a Board
     * <p>
     * you can use "BoardBuilder" class for this purpose.
     * <p>
     * pay attention add your codes in "try".
     */
    public Board loadBord() {
        try {
            Scanner scanner = new Scanner(boardFile);
          BoardBuilder boardBuilder =  new BoardBuilder(scanner);
//         scanner.close();
            return boardBuilder.build();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * load player.
     * if no such a player exist, create an account(file) for him/her.
     * <p>
     * you can use "savePlayer" method of this class for that purpose.
     * <p>
     * add your codes in "try" block .
     */
    public Player loadPlayer(String name, int playerNumber)  {
        try {
            File playerFile = getPlayerFile(name);
            Scanner scanner = new Scanner(playerFile);
            Player player = new Player(name,0,0,playerNumber);

//            System.out.println(name);
//            System.out.println(playerNumber);
            // Code in this part

            return player;

        } catch (FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * if player does not have a file, create one.
     * <p>
     * else update his/her file.
     * <p>
     * add your codes in "try" block .
     */
    public void savePlayer(Player player) {
        try {
            // add your codes in this part

            File file = getPlayerFile(player.getName());
            Scanner scanner = new Scanner(file);
            if (scanner.hasNext()) {
                player.setSumScore(player.getScore() + scanner.nextInt());
            }else player.setSumScore(player.getScore());
            PrintStream printStream = new PrintStream(new FileOutputStream(file,false));
            printStream.println(player.getSumScore());
            scanner.close();
            printStream.close();
            printStream.flush();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }

    }

    /**
     * give you a name (player name), search for its file.
     * return the file if exist.
     * return null if not.
     */
    private File getPlayerFile(String name) {
        for (File file : playersDirectory.listFiles()) {
         if (file.getName().equals(name+".txt")) return file;

        }
        File file = new File(playersDirectory,name+".txt");
        try {
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * at the end of the game save game details
     */
    public void archive(Player player1, Player player2) {
        try {
            // add your codes in this part
            PrintStream printStream = new PrintStream(new FileOutputStream(archiveFile, true));
            printStream .println();
            printStream.println("Player 1("+player1.getName()+") final score = "+player1.getScore());
            printStream.println("Player 2("+player2.getName()+") final score = "+player2.getScore());
            printStream.println();
            if (player1.getScore()>player2.getScore())
            printStream.println("WINNER = "+player1.getName());
            if (player1.getScore()<player2.getScore())
                printStream.println("WINNER = "+player2.getName());
            if (player1.getScore()==player2.getScore())
                printStream.println("Game is drew");
            printStream.println("----------------------------");
            printStream.close();
            printStream.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
