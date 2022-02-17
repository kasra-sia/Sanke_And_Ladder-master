package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.*;

public class Board {
    private final List<Cell> cells;
    private final List<Transmitter> transmitters;
    private final List<Wall> walls;
    private final LinkedHashMap<Cell, Integer> startingCells;

    public Board() {
        cells = new LinkedList<>();
        transmitters = new LinkedList<>();
        walls = new LinkedList<>();
        startingCells = new LinkedHashMap<>();
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public LinkedHashMap<Cell, Integer> getStartingCells() {
        return startingCells;
    }

    public List<Transmitter> getTransmitters() {
        return transmitters;
    }


    /**
     * give x,y , return a cell with that coordinates
     * return null if not exist.
     */
    public Cell getCell(int x, int y) {
        for (Cell cell : cells) {
            if (cell.getX() == x && cell.getY()==y){return cell;}

        }
        return null;
    }
}
