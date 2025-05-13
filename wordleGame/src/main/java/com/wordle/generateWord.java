package com.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class generateWord {
    private String word;
    private List<String> wordList;

    public generateWord() {
        wordList = new ArrayList<>();
        loadWords();
        word = getRandomWord();
        System.out.println("Selected word: " + word); // Debug output
    }

    private void loadWords() {
        try {
            File file = new File("src/main/resources/data/words.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    wordList.add(line.trim());
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: words.txt file not found");
            e.printStackTrace();
        }
    }

    private String getRandomWord() {
        if (wordList.isEmpty()) {
            return "ERROR"; // Fallback if no words are loaded
        }
        Random random = new Random();
        int index = random.nextInt(wordList.size());
        return wordList.get(index);
    }

    public String getWord() {
        return word;
    }
} 