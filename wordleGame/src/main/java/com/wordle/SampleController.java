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
	
    private static final String USER_STATS_PATH = System.getProperty("user.home") + "/wordle_stats.txt";
    private static final String DEFAULT_STATS_RESOURCE = "/data/stats.txt";
	
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
    
    private void handleStats(int numberOfGuesses) 
    {
        System.out.println("Handling stats for guess count: " + numberOfGuesses);
        System.out.flush();
        int statArr[] = new int[7]; 
        Scanner infile = null;
        String inputString;
        File userStatsFile = new File(USER_STATS_PATH);
        System.out.println("Stats file path: " + USER_STATS_PATH);
        System.out.println("Stats file exists: " + userStatsFile.exists());
        System.out.flush();
        try {
            if (userStatsFile.exists()) {
                System.out.println("Reading stats from: " + USER_STATS_PATH);
                infile = new Scanner(userStatsFile);
            } else {
                System.out.println("Reading default stats from resource");
                InputStream in = getClass().getResourceAsStream(DEFAULT_STATS_RESOURCE);
                if (in == null) throw new FileNotFoundException("Default stats resource not found");
                infile = new Scanner(in);
            }
            inputString = infile.nextLine();
            statArr[0] = Integer.parseInt(infile.nextLine());
            statLabel1.setText(inputString + statArr[0]);
            inputString = infile.nextLine();
            statArr[1] = Integer.parseInt(infile.nextLine());
            statLabel2.setText(inputString + statArr[1]);
            inputString = infile.nextLine();
            statArr[2] = Integer.parseInt(infile.nextLine());
            statLabel3.setText(inputString + statArr[2]);
            inputString = infile.nextLine();
            statArr[3] = Integer.parseInt(infile.nextLine());
            statLabel4.setText(inputString + statArr[3]);
            inputString = infile.nextLine();
            statArr[4] = Integer.parseInt(infile.nextLine());
            statLabel5.setText(inputString + statArr[4]);
            inputString = infile.nextLine();
            statArr[5] = Integer.parseInt(infile.nextLine());
            statLabel6.setText(inputString + statArr[5]);
            inputString = infile.nextLine();
            statArr[6] = Integer.parseInt(infile.nextLine()); // total games played
            statLabel7.setText(inputString + statArr[6]);
            infile.close();
        } catch (Exception e) {
            System.out.println("Error reading stats: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        // Write updated stats to user home
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(USER_STATS_PATH);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace(); 
            System.exit(0); 
        }
        statArr[6]++;
        statArr[numberOfGuesses - 1]++; // update correct statistic
        for (int i = 0; i < 6; i++) 
        {
            outfile.println("Successful guesses on " + (i+1)  + " guess attempt(s): \n" + statArr[i]);
        }
        outfile.println("Total games played: \n" + statArr[6]);
        outfile.close();
        statBoard();
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
    private void saveButtonPressed() // export all data to txt file, still need to make the actual buttons in FXML
    {
    	PrintWriter outfile = null;
    	try
    	{
    		outfile = new PrintWriter("./src/wordleApplication/loadgame.txt");
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
    		infile = new Scanner(new FileReader("./src/wordleApplication/loadgame.txt"));
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
    
    private void statBoard() 
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
