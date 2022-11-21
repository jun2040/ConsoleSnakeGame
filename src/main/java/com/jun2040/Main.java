package com.jun2040;

public class Main {
    public static void main(String[] args) {
        // Initialize and run the game
        Game game = new Game(20, 20);

        // Initialize post game sequence
        new PostGame();

        // Save score and display all scores
        PostGame.addEntry(game.getScore());
        PostGame.displayScores();

        // Exit the program
        System.exit(0);
    }
}
