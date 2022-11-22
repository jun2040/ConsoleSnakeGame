package com.jun2040;

import java.io.*;
import java.util.*;
import java.util.Map.*;

public class ScoreManager {
    private static File file = new File("Data");
    private static Scanner scanner, fileScanner;
    private static FileWriter writer;

    public ScoreManager() {
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

    // Discard already existing data
    private static void clearPrompt() {
        System.out.println("Press enter to see results");
        scanner.nextLine();
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
        Map<String, Integer> entries = new LinkedHashMap<>();

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
            // Check if key already exists
            if (entries.containsKey(name)) {
                // If score is greater than existing value replace
                if (score > entries.get(name))
                    entries.put(name, score);
            } else {
                entries.put(name, score);
            }
        }

        return entries;
    }

    // Display the scores to the console
    public static final void displayScores() {
        // Sort map
        List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(getEntries().entrySet());
        Collections.sort(entries, new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Set title
        String col1Title = "Player", col2Title = "Score";

        // Get max column width
        int maxNameColWidth = 0, maxScoreColWidth = 0;

        for (Entry<String, Integer> entry : entries) {
            maxNameColWidth = Integer.max(maxNameColWidth, entry.getKey().length());
            maxScoreColWidth = Integer.max(maxScoreColWidth, entry.getValue().toString().length());
        }

        // Calculate column widths
        int placeColWidth = (entries.size() + "").length();
        int nameColWidth = Integer.max(maxNameColWidth, col1Title.length());
        int scoreColWidth = Integer.max(maxScoreColWidth, col2Title.length());

        // Setup table title
        String paddedIndexTitle = " ".repeat(placeColWidth);
        String paddedNameTitle = String.format("%-" + nameColWidth + "s", col1Title);
        String paddedScoreTitle = String.format("%-" + scoreColWidth + "s", col2Title);

        // Print table title
        System.out.println("-".repeat(10 + placeColWidth + nameColWidth + scoreColWidth));

        System.out.println("| " + paddedIndexTitle + " | " + paddedNameTitle + " | " + paddedScoreTitle + " |");

        System.out.println("-".repeat(10 + placeColWidth + nameColWidth + scoreColWidth));

        // Print table content
        int index = 1;
        for (Entry<String, Integer> entry : entries) {
            String paddedIndex = String.format("%-" + placeColWidth + "s", index);
            String paddedName = String.format("%-" + nameColWidth + "s", entry.getKey());
            String paddedScore = String.format("%-" + scoreColWidth + "s", entry.getValue());
            System.out.println("| " + paddedIndex + " | " + paddedName + " | " + paddedScore + " |");

            index++;
        }

        System.out.println("-".repeat(10 + placeColWidth + nameColWidth + scoreColWidth));
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
