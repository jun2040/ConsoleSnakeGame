package com.jun2040;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.jun2040.Action.*;

public class Snake {
    private Vector2 headDirection = new Vector2(1, 0);
    private Vector2 headPosition;
    private int snakeLength = 3;
    private Deque<Vector2> snakeBody = new ArrayDeque<>();

    // Calculate the snake's direction based on actions
    public final void calculateDirection(Input input) {
        // Cast boolean values into binary
        int left = input.states.get(MOVE_LEFT) ? 1 : 0;
        int right = input.states.get(MOVE_RIGHT) ? 1 : 0;
        int up = input.states.get(MOVE_UP) ? 1 : 0;
        int down = input.states.get(MOVE_DOWN) ? 1 : 0;

        // Calculate move direction
        int moveX = right - left;
        int moveY = down - up;

        // Change direction to input if it is perpendicular to the current direction
        if (headDirection.equals(Vector2.RIGHT) || headDirection.equals(Vector2.LEFT)) {
            if (moveY != 0) {
                headDirection.x = 0;
                headDirection.y = moveY;
            }
        } else if (headDirection.equals(Vector2.UP) || headDirection.equals(Vector2.DOWN)) {
            if (moveX != 0) {
                headDirection.x = moveX;
                headDirection.y = 0;
            }
        }
    }

    // Move the snake
    public final void move(int maxW, int maxH) {
        // Increment snake head by direction
        headPosition.x += headDirection.x;
        headPosition.y += headDirection.y;

        // Clamp the position to prevent out of bounds exception
        headPosition.x = Integer.max(Integer.min(headPosition.x, maxW - 1), 0);
        headPosition.y = Integer.max(Integer.min(headPosition.y, maxH - 1), 0);
    }

    // Get the body deque of the snake
    public final Deque<Vector2> getBody() {
        return snakeBody;
    }

    // Set position
    public final void setPosition(Vector2 pos) {
        headPosition = pos;
    }

    // Update the body
    public final void updateBody() {
        // Insert current position to the front of the deque
        snakeBody.offerFirst(new Vector2(headPosition.x, headPosition.y));

        // Remove last cell if the deque is longer than the length
        if (snakeBody.size() > snakeLength)
            snakeBody.removeLast();
    }

    // Check if the head is colliding with the body
    public final boolean checkSelf() {
        if (snakeBody.contains(headPosition))
            return true;

        return false;
    }

    // Check if there is a collision with apple: if so, increment length and return true
    public final boolean checkApple(Apple apple) {
        if (headPosition.equals(apple.getPosition())) {
            snakeLength++;
            return true;
        }

        return false;
    }

    // Get the total score (current length - initial length)
    public final int getScore() {
        return snakeLength - 3;
    }

    // Draw the snake on to the buffer
    public final void draw(Screen screen) {
        // Loop through all the body and replace pixel with character
        for (Vector2 pos : snakeBody) {
            screen.backBuffer[pos.y][pos.x] = '#';
        }

        // Replace the head position's character to some other character to mark where the head is
        screen.backBuffer[headPosition.y][headPosition.x] = 'x';
    }
}
