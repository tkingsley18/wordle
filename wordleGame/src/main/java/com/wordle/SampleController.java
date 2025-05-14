package com.wordle;

import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;

import java.io.*;
import java.time.Duration;
import java.util.*;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Insets;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.nio.file.Files;

public class SampleController {
    @FXML private Label boxLabel1, boxLabel2, boxLabel3, boxLabel4, boxLabel5, boxLabel6, boxLabel7, boxLabel8, boxLabel9, boxLabel10;
    @FXML private Label boxLabel11, boxLabel12, boxLabel13, boxLabel14, boxLabel15, boxLabel16, boxLabel17, boxLabel18, boxLabel19, boxLabel20;
    @FXML private Label boxLabel21, boxLabel22, boxLabel23, boxLabel24, boxLabel25, boxLabel26, boxLabel27, boxLabel28, boxLabel29, boxLabel30;
    @FXML private TextField userText;
    @FXML private Button enterButton, exitButton, saveButton, loadButton;
    @FXML private Label statLabel1, statLabel2, statLabel3, statLabel4, statLabel5, statLabel6, statLabel7;
    @FXML private VBox statBox;
    @FXML private HBox mainBox1, mainBox2, mainBox3, mainBox4, mainBox5, mainBox6;
    private Label[] labelArr;
    private Label[] statLabels;
    int guessCount = 0;
    int arrayCount = 0; // keep track of where you are in massive array label
    String wordArr[] = new String[30];
	String guessArr[] = new String[30];
	generateWord gameWord;
	String gameString;
	
    private static final String USER_STATS_PATH = "src/main/resources/data/stats.txt";
    private static final String DEFAULT_STATS_RESOURCE = "/data/stats.txt";
    private static final String LOAD_GAME_PATH = "src/main/resources/data/loadgame.txt";
	
    @FXML
    public void initialize() 
    {
        labelArr = new Label[]{boxLabel1, boxLabel2, boxLabel3, boxLabel4, boxLabel5, boxLabel6, boxLabel7, boxLabel8, boxLabel9, boxLabel10,
        		boxLabel11, boxLabel12, boxLabel13, boxLabel14, boxLabel15, boxLabel16, boxLabel17, boxLabel18, boxLabel19, boxLabel20, boxLabel21, boxLabel22,
        		boxLabel23, boxLabel24, boxLabel25, boxLabel26, boxLabel27, boxLabel28, boxLabel29, boxLabel30	
        };
 
        statLabels = new Label[] {statLabel1, statLabel2, statLabel3, statLabel4, statLabel5, statLabel6, statLabel7};
        resetGame();
    }
    @FXML
    private void resetButtonPressed() 
    {
    	resetGame();
    }
    @FXML
    private void userGuess() // logic for printing the word in the label boxes as well as the background colors
    {
        System.out.println("User guess called");
        guessCount++;
        String guess = userText.getText();
        System.out.println("Guess: " + guess + ", Game word: " + gameString);
        int currentArrayCount = arrayCount;
        if (guess.equals(gameString)) // maybe separate into a different method
        {
            System.out.println("Correct guess! Calling handleStats");
            for (int i = arrayCount; i < currentArrayCount + 5; i++) 
            {
                arrayCount++;
                labelArr[i].setBackground(new Background(new BackgroundFill(Color.LAWNGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                labelArr[i].setText(wordArr[i]);
            }
            handleStats(guessCount);
            System.out.flush();
        }
        List<String> wordList = Arrays.asList(wordArr); // so that can use contains
        for (int i = 0; i < 5; i++) 
        {
            guessArr[i + arrayCount] = String.valueOf(guess.charAt(i)); // might work?? test more
        }
        for (int i = arrayCount; i < currentArrayCount + 5; i++) 
        {
            arrayCount++;
            if (guessArr[i].equals(wordArr[i])) 
            {
                labelArr[i].setBackground(new Background(new BackgroundFill(Color.LAWNGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                labelArr[i].setText(guessArr[i]);
            }
            else if (wordList.contains(guessArr[i])) 
            {
                labelArr[i].setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                labelArr[i].setText(guessArr[i]);
            }
            else 
            {
                labelArr[i].setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                labelArr[i].setText(guessArr[i]);
            }
        }
        
        userText.clear();
    }
    
    private void resetGame() // reset
    {
    	generateWord gameWord = new generateWord(); // empty the wordArr and guessArr so that 
    	gameString = gameWord.getWord(); 
    	guessCount = 0;
    	System.out.println("Game word: " + gameString);
    	for (int i = 0; i < 30; i++) 
		{
    		if (guessCount == 5) {guessCount = 0;}
			wordArr[i] = String.valueOf(gameString.charAt(guessCount));
			guessCount++; // add each car of the word in 6 times over
		}
    	for (Label labelIt : labelArr) 
    	{
    		labelIt.setText(" ");
    		labelIt.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    	}
    	guessCount = 0;
    	arrayCount = 0;
    }
    
    private void handleStats(int numberOfGuesses) {
        System.out.println("Handling stats for guess count: " + numberOfGuesses);
        System.out.flush();
        File userStatsFile = new File(USER_STATS_PATH);
        System.out.println("Stats file path: " + USER_STATS_PATH);
        System.out.println("Stats file exists: " + userStatsFile.exists());
        System.out.flush();
        try {
            List<String> stats = Files.readAllLines(userStatsFile.toPath());
            int[] statsArray = new int[7]; // 6 for guesses, 1 for total games
            int index = 0;
            for (String line : stats) {
                // Extract the number after the colon
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String numPart = parts[1].trim();
                    // Extract only the number
                    numPart = numPart.replaceAll("[^0-9]", "");
                    if (!numPart.isEmpty()) {
                        statsArray[index++] = Integer.parseInt(numPart);
                        if (index >= 7) break;
                    }
                }
            }
            statsArray[numberOfGuesses - 1]++;
            statsArray[6]++; // Increment total games played
            try (PrintWriter writer = new PrintWriter(new FileWriter(userStatsFile))) {
                for (int i = 0; i < 6; i++) {
                    writer.println("Successful guesses on " + (i + 1) + " guess attempt(s): " + statsArray[i]);
                }
                writer.println("Total games played: " + statsArray[6]);
            }
            statBoard(statsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void closeButtonPressed() 
    {
    	statBox.setVisible(false);
    	exitButton.setVisible(false);
    	userText.setVisible(true);
    	enterButton.setVisible(true);
    	showLabels();
    }
    
    @FXML
    private void saveButtonPressed() // export all data to txt file
    {
    	PrintWriter outfile = null;
    	try
    	{
    		outfile = new PrintWriter(LOAD_GAME_PATH);
    	}
    	catch (FileNotFoundException e)
    	{
	    	System.out.println("File not found");
	    	e.printStackTrace(); 
	    	System.exit(0); 
    	}
    	
    	outfile.println(gameString);
    	outfile.println(guessCount);
    	for (int i = 0; i < 30; i++) 
    	{
    		outfile.println(wordArr[i]);
    	}
    	for (Label labelIt : labelArr) 
    	{
    		outfile.println(labelIt.getText());
    		outfile.println(labelIt.getBackground());
    	}
    	outfile.println(arrayCount);
    	
    	outfile.close();
    }
    
    @FXML
    private void loadButtonPressed() // import all data to txt file
    {
    	Scanner infile = null;
    	String inputString;
    	try
    	{
    		infile = new Scanner(new FileReader(LOAD_GAME_PATH));
    	}
    	catch (FileNotFoundException e)
    	{
	    	System.out.println("File not found");
	    	e.printStackTrace(); 
	    	System.exit(0);
    	}
    	
    	gameString = infile.nextLine();
    	guessCount = Integer.parseInt(infile.nextLine());
    	for (int i = 0; i < 30; i++) 
    	{
    		wordArr[i] = infile.nextLine();
    	}
    	for (Label labelIt : labelArr) 
    	{
    		labelIt.setText(infile.nextLine());
    		inputString = infile.nextLine();
    		if (inputString.equals("javafx.scene.layout.Background@d28a1382")) // light
    		{
    			labelIt.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY))); // empty
    		}
    		else if (inputString.equals("javafx.scene.layout.Background@25024682")) // dark
    		{
    			labelIt.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    		}
    		else if (inputString.equals("javafx.scene.layout.Background@c6f3c682")) // yellow
    		{
    			labelIt.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
    		}
    		else // green
    		{
    			labelIt.setBackground(new Background(new BackgroundFill(Color.LAWNGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    		}	
    	}
    	arrayCount = Integer.parseInt(infile.nextLine());
    	infile.close();
    }
    
    private void statBoard(int[] statsArray) 
    {
        System.out.println("Showing stat board");
        hideLabels();
        userText.setVisible(false);
        enterButton.setVisible(false);
        statBox.setVisible(true);
        exitButton.setVisible(true);
        
        // Force layout update
        statBox.requestLayout();
        statBox.layout();
        
        // Debug visibility
        System.out.println("Stat box visible: " + statBox.isVisible());
        System.out.println("Stat box layout bounds: " + statBox.getLayoutBounds());
        
        statLabel1.setText("Successful guesses on 1 guess attempt(s): " + statsArray[0]);
        statLabel2.setText("Successful guesses on 2 guess attempt(s): " + statsArray[1]);
        statLabel3.setText("Successful guesses on 3 guess attempt(s): " + statsArray[2]);
        statLabel4.setText("Successful guesses on 4 guess attempt(s): " + statsArray[3]);
        statLabel5.setText("Successful guesses on 5 guess attempt(s): " + statsArray[4]);
        statLabel6.setText("Successful guesses on 6 guess attempt(s): " + statsArray[5]);
        statLabel7.setText("Total games played: " + statsArray[6]);
    }
    
    private void hideLabels() 
    {
    	mainBox1.setVisible(false);
    	mainBox2.setVisible(false);
    	mainBox3.setVisible(false);
    	mainBox4.setVisible(false);
    	mainBox5.setVisible(false);
    	mainBox6.setVisible(false);
    }
    private void showLabels() 
    {
    	mainBox1.setVisible(true);
    	mainBox2.setVisible(true);
    	mainBox3.setVisible(true);
    	mainBox4.setVisible(true);
    	mainBox5.setVisible(true);
    	mainBox6.setVisible(true);
    }
}
