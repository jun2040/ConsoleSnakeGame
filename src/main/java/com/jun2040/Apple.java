package com.jun2040;

import java.util.ArrayList;
import java.util.Random;

public class Apple {
    private final Random RANDOM = new Random();
    private final ArrayList<Vector2> available = new ArrayList<>();
    private Vector2 position;

    public final Vector2 getPosition() {
        return position;
    }

    // Spawn apple on random available location
    public final void spawnApple(Snake snake) {
        ArrayList<Vector2> remaining = new ArrayList<>(available);
        remaining.removeAll(snake.getBody());

        int gen = RANDOM.nextInt(remaining.size());
        position = remaining.get(gen);
    }

    // Initialize all the initial available positions for the apple to spawn on
    public void initAvailPos(int w, int h) {
        for (int y = 0; y < w; y++) {
            for (int x = 0; x < h; x++) {
                available.add(new Vector2(x, y));
            }
        }
    }

    // Draw function called by render function to draw the apple on the buffer
    public final void draw(Screen screen) {
        screen.backBuffer[position.y][position.x] = 'o';
    }
}
