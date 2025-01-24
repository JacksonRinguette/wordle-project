package Wordle;

/**
 * WordValidation Class
 * 
 * Handles the core logic of validating guesses for a Wordle-style game.
 * Implements word loading, solution selection, and feedback generation.
 * 
 * Author: Jackson Ringuette, Nathan Kostynick, Max Sampson, Tan Vo
 * Date: December 8th, 2024
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordValidation {
	private List<String> wordList;  // List of valid words
	private String solution;       // The word to guess
	private int numGuesses;        // Number of guesses made
	private boolean correctGuess;  // Whether the solution was guessed correctly

	public WordValidation() {
		wordList = new ArrayList<>();
		loadWords("fiveletter.txt");
		chooseSolution();
		numGuesses = 0;
		correctGuess = false;
	}

	/**
	 * Loads the file into an Array List
	 * @param filename needed to read words
	 */
	public void loadWords(String filename) 
	{
		Scanner scan = null;

		try
		{
			File file = new File(filename);
			scan = new Scanner(file);

			while(scan.hasNext())
			{
				String word = scan.next();

				if(word.length()>5)
				{
					word = word.substring(0,5);
					wordList.add(word);
				}
			}
		}
		catch(FileNotFoundException FNFE)
		{
			System.err.println("File not found");
		}
	}

	/**
	 * Chooses the solution from the imported list of words
	 */
	public void chooseSolution() {
		Random rand = new Random();
		solution = wordList.get(rand.nextInt(wordList.size()));
	}

	/**
	 * Validates the user's guess
	 * @param guess, user inputed guess
	 * @return returns color-coded feedback as a string array
	 */
	public String[] validateGuess(String guess) {
		guess = guess.toLowerCase();
		numGuesses++;

		// Validation for guess length
		if (guess.length() != 5) {
			numGuesses--;  // Do not count invalid guesses
			return new String[]{"Guess must be exactly five letters"};
		}

		// Validation for invalid word
		if (!wordList.contains(guess)) {
			numGuesses--;
			return new String[]{"Invalid word, try again"};
		}

		String[] validation = new String[5];
		boolean[] matchedInSolution = new boolean[5];
		int correct = 0;

		// Check for green matches (correct position)
		for (int i = 0; i < 5; i++) {
			char letter = guess.charAt(i);
			if (letter == solution.charAt(i)) {
				correct++;
				validation[i] = "green";
				matchedInSolution[i] = true;
			}
		}

		// Check for yellow matches (wrong position)
		for (int i = 0; i < 5; i++) {
			if (validation[i] == null) {  // If not already green
				char letter = guess.charAt(i);
				boolean foundYellow = false;

				for (int j = 0; j < 5; j++) {
					if (!matchedInSolution[j] && letter == solution.charAt(j)) {
						validation[i] = "yellow"; // not in correct index
						matchedInSolution[j] = true;
						foundYellow = true;
						break;
					}
				}

				if (!foundYellow) {
					validation[i] = "gray";  // Letter not in the solution
				}
			}
		}

		if (correct == 5) {
			correctGuess = true;
		}
		return validation;
	}

	/**
	 * Checks if game is over
	 * @return true or false depending on if game is over
	 */
	public boolean isGameOver() {
		return correctGuess || numGuesses >= 6;
	}

	/**
	 * Gets the number of guesses
	 * @return number of guesses
	 */
	public int getNumGuesses() {
		return numGuesses;
	}

	/**
	 * returns the selected solution
	 * @return the solution that was selected
	 */
	public String getSolution() {
		return solution.toUpperCase();
	}
}
