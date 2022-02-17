package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Wall;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.FatalSnake;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.GroundSnake;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.MagicalSnake;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;


public class BoardBuilder {
    private Scanner scanner;
    private Board board = new Board();

    public BoardBuilder(Scanner scanner) {
        this.scanner = scanner;

    }

    /**
     * give you a string in constructor.
     * <p>
     * you should read the string and create a board according to it.
     */
    public Board build() {
//BASE
        int i = 0;
        int cellx = 0;
        int celly = 0;
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                if (i == 1) {
                    celly = scanner.nextInt();
                    break;
                }
                if (i == 0) {
                    cellx = scanner.nextInt();
                    i++;
                }

            } else scanner.next();
        }
        scanner.nextLine();
        for (int j = 1; j <= cellx; j++) {
            for (int k = 1; k <= celly; k++) {
                Color color = Color.valueOf(scanner.next());
                Cell cell = new Cell(color, j, k) ;

                board.getCells().add(cell);
            }
        }

//getAdjacentCells
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

//START
        int start = 0;
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                start = scanner.nextInt();
                break;
            } else scanner.next();
        }
        scanner.nextLine();
        for (int j = 1; j <= start; j++) {

            Cell cell = board.getCell(scanner.nextInt(), scanner.nextInt());
            board.getStartingCells().put(cell, scanner.nextInt());
        }
//WALL
        int wallnumber = 0;
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                wallnumber = scanner.nextInt();
                break;
            } else scanner.next();
        }
        scanner.nextLine();
        for (int j = 1; j <=wallnumber ; j++) {
           Cell cell1 = board.getCell(scanner.nextInt(), scanner.nextInt());
           Cell cell2 = board.getCell(scanner.nextInt(), scanner.nextInt());
            Wall wall = new Wall(cell1,cell2);
            Wall wall1 = new Wall(cell2,cell1);
           board.getWalls().add(wall);
           board.getWalls().add(wall1);
        }

//getAdjacentOpenCells
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

//TRANSMITTER
        int trasmitternum = 0;
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                trasmitternum = scanner.nextInt();
                break;
            } else scanner.next();
        }
        scanner.nextLine();
        for (int j = 1; j <=trasmitternum ; j++) {
            Cell cell1 = board.getCell(scanner.nextInt(), scanner.nextInt());
            Cell cell2 = board.getCell(scanner.nextInt(), scanner.nextInt());
            String kind = scanner.next();
            if (kind.equals("t")) {
                Transmitter transmitter = new Transmitter(cell1, cell2);
                cell1.setTransmitter(transmitter);
                board.getTransmitters().add(transmitter);
            }
//Ground snake
            if (kind.equals("g")){
                GroundSnake groundSnake = new GroundSnake(cell1,cell1);
                cell1.setTransmitter(groundSnake);
                board.getTransmitters().add(groundSnake);


            }
//Magical Snake
            if (kind.equals("m")){
                MagicalSnake mg = new MagicalSnake(cell1,cell2);
                cell1.setTransmitter(mg);
                board.getTransmitters().add(mg);
            }
//Fatal Snake
            if(kind.equals("f")){
                FatalSnake fs = new FatalSnake(cell1,cell2);
                cell1.setTransmitter(fs);
                board.getTransmitters().add(fs);
            }
        }
//PRIZES
        int prize =0;
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                prize = scanner.nextInt();
                break;
            } else scanner.next();
        }
        scanner.nextLine();
        for (int j = 1; j <=prize ; j++) {
            int temp1= scanner.nextInt();
            int temp2 = scanner.nextInt();
            Cell cell =board.getCell(temp1,temp2);
            int temp3 =scanner.nextInt();
            int temp4 =scanner.nextInt();
            int temp5 =scanner.nextInt();
            board.getCell(temp1, temp2).setPrize(new Prize(cell,temp3,temp5,temp4));
        }

        scanner.close();
        return board;
    }
}
