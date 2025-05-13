# Wordle Game

A simple Java-based Wordle game built with JavaFX.

## Prerequisites

- Java 17 or higher
- Maven

## How to Compile and Run

1. **Clone the repository** (if applicable):
   ```bash
   git clone <repository-url>
   cd wordleGame
   ```

2. **Compile the project**:
   ```bash
   mvn clean compile
   ```

3. **Run the game**:
   ```bash
   mvn javafx:run
   ```

## Game Instructions

- Enter a 5-letter word guess in the text field and press "Enter" to submit.
- The game will indicate correct letters in green, misplaced letters in yellow, and incorrect letters in gray.
- After a successful guess, your stats will be displayed.

## Project Structure

- `src/main/java/com/wordle/`: Contains the Java source files.
- `src/main/resources/fxml/`: Contains the FXML layout files.
- `src/main/resources/data/`: Contains the word list and stats files.

## Troubleshooting

- Ensure Java 17+ and Maven are installed and configured correctly.
- If you encounter any issues, check the console output for error messages. 