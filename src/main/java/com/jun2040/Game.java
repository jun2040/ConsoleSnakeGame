package com.jun2040;

import java.util.Random;

public class Game {
    private final Input INPUT;
    private final Screen SCREEN;
    private final Random RANDOM = new Random();

    private final int WIDTH, HEIGHT;
    private final int FPS = 10;
    private boolean isAlive = true;

    // Game objects
    private Snake snake;
    private Apple apple;

    public Game(int w, int h) {
        // Initialize game variables
        WIDTH = w;
        HEIGHT = h;

        INPUT = new Input();
        SCREEN = new Screen(WIDTH, HEIGHT);

        snake = new Snake();
        apple = new Apple();

        setup();
        start();
    }

    // Setup game
    private void setup() {
        snake.setPosition(new Vector2(RANDOM.nextInt(WIDTH / 2), RANDOM.nextInt(HEIGHT / 2)));
        apple.initAvailPos(WIDTH, HEIGHT);
        apple.spawnApple(snake);
    }

    // Start game loop
    private void start() {
        final long NSPF = (long) 1e9 / FPS;
        long frameTime = 0;
        long previousTime = System.nanoTime();

        // Run game loop until not isAlive
        while (isAlive) {
            long currentTime = System.nanoTime();
            long loopTime = currentTime - previousTime;

            frameTime += loopTime;

            previousTime = currentTime;

            // When a specific time is passed, executed the update and render function
            if (frameTime >= NSPF) {
                update();
                render();

                // Decrement by target to reset accurately
                frameTime -= NSPF;
            }
        }

        // Initialize post game sequence
        new ScoreManager();

        // Add new entry
        ScoreManager.addEntry(snake.getScore());

        // Display all scores
        ScoreManager.displayScores();
    }

    // Game update function
    private void update() {
        // Calculate snake
        snake.calculateDirection(INPUT);
        snake.move(WIDTH, HEIGHT);

        // Calculate collisions
        if (snake.checkSelf()) {
            isAlive = false;
            return;
        }

        if (snake.checkApple(apple))
            apple.spawnApple(snake);

        // Update snake
        snake.updateBody();
    }

    // Kind of double buffered render function
    private void render() {
        // Clear buffer while not clearing the console
        SCREEN.clearBuffer();

        // Draw the game objects to the buffer
        snake.draw(SCREEN);
        apple.draw(SCREEN);

        // Create the buffer
        SCREEN.constructBuffer();

        // Clear the screen and render
        SCREEN.clearScreen();
        SCREEN.renderScreen();

        // UI
        System.out.println("Score: " + snake.getScore());
    }
}
