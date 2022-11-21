package com.jun2040;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PostGame {
    private static File file;
    private static Scanner scanner, fileScanner;
    private static FileWriter writer;

    public PostGame() {
        // Create target file object
        file = new File("Data");

        // Check if file exists: if not, create one
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Couldn't create new file");
                return;
            }
        }

        // Initialize console scanner for user input
        scanner = new Scanner(System.in);

        // Initialize file scanner for reading files
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find file");
            return;
        }

        // Set delimiter for file scanner
        fileScanner.useDelimiter("\n");

        // initialize file writer
        try {
            writer = new FileWriter("Data", true);
        } catch (IOException e) {
            System.err.println("Couldn't find file");
            return;
        }

        clearPrompt();
    }

    // Run this to clear the input space because of key presses during the game
    private static void clearPrompt() {
        System.out.println("Press enter to see results");
        String z = scanner.nextLine();
    }

    // Get the name of the player
    private static String getName() {
        System.out.println("Enter name: ");

        String name = "";

        // Ask until they enter a string that has one or more characters
        while (true) {
            name = scanner.nextLine();

            if (name.length() < 1) {
                System.out.println("Please type a name: ");
            } else {
                break;
            }
        }

        return name;
    }

    // Get entries and organize into key value pairs
    public static final Map<String, Integer> getEntries() {
        Map<String, Integer> entries = new HashMap<>();

        while (fileScanner.hasNext()) {
            String entry = fileScanner.nextLine();

            // Split input by comma
            String[] split = entry.split(",");

            String name = split[0];
            int score = 0;

            // Try parsing the string to integer: if can't exlcude
            try {
                score = Integer.parseInt(split[1]);
            } catch (NumberFormatException e) {
                System.err.println("Score not a number");
                continue;
            }

            // Insert entry
            entries.put(name, score);
        }

        return entries;
    }

    // Display the scores to the console
    public static final void displayScores() {
        Map<String, Integer> entries = getEntries();

        entries.forEach((k, v) -> {
            System.out.println(k + " " + v);
        });
    }

    // Add entry to the save file
    public static final void addEntry(int score) {
        String name = getName();

        try {
            writer.write(name + "," + score + "\n");
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot insert entry");
        }
    }
}
