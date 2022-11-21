package com.jun2040;

import java.io.IOException;
import java.util.Arrays;

public class Screen {
    private final ProcessBuilder CLEAR = new ProcessBuilder("cmd", "/c", "cls").inheritIO();
    public char[][] backBuffer;
    private String buffer = "";
    private final int WIDTH, HEIGHT;

    // Initialize all variables
    public Screen(int w, int h) {
        WIDTH = w;
        HEIGHT = h;

        backBuffer = new char[h][w];

        // Initialize buffer
        for (char[] row : backBuffer)
            Arrays.fill(row, ' ');
    }

    // Construct frame from buffer using built in methods: for loops seem to perform worse
    public final void constructBuffer() {
        buffer = "-".repeat(WIDTH + 2) + "\n";

        buffer += Arrays.deepToString(backBuffer)
                .replace("[[", "|")
                .replace("]]", "|\n")
                .replace("[", "|")
                .replace("], ", "|\n")
                .replace(", ", "");

        buffer += "-".repeat(WIDTH + 2) + "\n";
    }

    // Clear the buffer to prepare for redraw
    public final void clearBuffer() {
        for (char[] row : backBuffer) {
            Arrays.fill(row, ' ');
        }
    }

    // Clear the console using Windows commands
    public final void clearScreen() {
        try {
            CLEAR.start().waitFor();
        } catch (IOException e) {
        } catch (InterruptedException e) {
        }
    }

    // Print frame to the console
    public final void renderScreen() {
        System.out.println(buffer);
    }
}
