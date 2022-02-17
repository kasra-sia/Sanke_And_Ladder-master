package ir.sharif.math.bp99_1.snake_and_ladder;


import ir.sharif.math.bp99_1.snake_and_ladder.logic.LogicalAgent;


public class Main {

    private static LogicalAgent logicalAgent;

    public static LogicalAgent getLogicalAgent() {
        return logicalAgent;
    }

    public static void main(String[] args) {
        logicalAgent = new LogicalAgent() ;
        logicalAgent.initialize();
    }
}