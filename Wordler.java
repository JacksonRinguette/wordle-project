package Wordle;

/**
 * Wordler Class
 * 
 * The main JFrame for a Wordle-style game. Handles the GUI, user input, 
 * and interaction between the game logic and visual components.
 * 
 * Author: Nathan Kostynick, Jackson Ringuette, Max Sampson, Tan Vo
 * Date: December 8th, 2024
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


public class Wordler extends JFrame {
	private JLabel title = new JLabel("Turtle: Guess the 5 letter word \n");
	private JLabel authors = new JLabel("Created By: Max Sampson, Nathan Kostynick, Jackson Ringuette, and Tan Vo");
	private JLabel instructions1 = new JLabel("Yellow Square: letter is in the word but in wrong place");
	private JLabel instructions2 = new JLabel("Green Square: letter is in the correct place in the word");
	private JLabel instructions3  = new JLabel("Gray Square: letter is not in the word");
	;    JLabel emptyLine = new JLabel(" ");
	private TileMaker tm = new TileMaker();  // Manages the grid for guesses
	private JTextField inputField = new JTextField(10);  // Input field for user guesses
	private LetterPad letterPad;  // On-screen keyboard
	private WordValidation game;  // Core game logic


	/**
     * Constructor for Wordler.
     * Sets up the GUI and initializes components.
     */
	public Wordler() {

		game = new WordValidation();

		// Configure LetterPad with references to inputField and TileMaker
		letterPad = new LetterPad(inputField, tm);

		// Configure main layout of JFrame

		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		authors.setAlignmentX(Component.CENTER_ALIGNMENT);

		instructions1.setAlignmentX(Component.CENTER_ALIGNMENT);
		instructions2.setAlignmentX(Component.CENTER_ALIGNMENT);
		instructions3.setAlignmentX(Component.CENTER_ALIGNMENT);



		JPanel heading = new JPanel();
		heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS)); //added by Nathan dec 8th, 5pm

		title.setHorizontalAlignment(JTextField.CENTER);
		authors.setHorizontalAlignment(JTextField.CENTER);




		heading.add(title);   // Title at the top
		heading.add(authors); // Authors below the title

		heading.add(emptyLine);

		heading.add(instructions1); // yellow squares
		heading.add(instructions2); //green squares
		heading.add(instructions3); //gray squares



		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tm, BorderLayout.CENTER);          // TileMaker in the center
		heading.add(inputField, BorderLayout.NORTH);  // Input field at the top
		//getContentPane().add(inputField, BorderLayout.NORTH);  

		getContentPane().add(letterPad, BorderLayout.SOUTH);   // LetterPad at the bottom
		getContentPane().add(heading, BorderLayout.NORTH); // Heading at top
		inputField.setHorizontalAlignment(JTextField.CENTER);  // Center-align input text


		// Add ActionListener to register enter key
		inputField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleGuess(inputField.getText().toLowerCase());
			}
		});

		// Frame settings
		setSize(1000, 1000);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Handles user's guess
	 * @param guess Users inputted guess
	 */
	private void handleGuess(String guess) {

		HashMap<Character, String> letterColorMap = new HashMap<>();



		if (game.isGameOver()) {
			JOptionPane.showMessageDialog(this, "Game over! The solution was: " + game.getSolution());
			return;
		}


		//feedback is 5 in length and a string list of colors
		String[] feedback = game.validateGuess(guess);


		if (feedback.length == 1) {  // Validation message
			JOptionPane.showMessageDialog(this, feedback[0]); 
			/**
			 * Jackson made the validation method in Demo still give an array of strings that represents the "feedback"
			 * even if the guess isn't valid. The 0th index will be the message
			 * Thats just so that we can just take the message he had already written into the logic instead of re-making it here
			 * Essentially, just call feedback[0] and you will get what was wrong with the guess.
			 * -Nathan
			 * I wrote this whole paragraph just to use my own advice 1 time :)
			 */
		} else {
			tm.updateRow(guess);  // Update the grid with the guess
			applyFeedback(feedback);  // Color the tiles based on feedback



			char[] letters = guess.toCharArray();
			// {s, h , o, r, e}


			for (int i = 0; i < 5; i++) {

				letterColorMap.put(letters[i], feedback[i]);
				// { 's' : "green", 'h' : "yellow" ... 'e' : "gray" } // stage 2
			}



			letterPad.updateColors(letterColorMap); // updates the colors on the keypad

			if (game.isGameOver()) {
				if (game.getSolution().equalsIgnoreCase(guess)) {
					JOptionPane.showMessageDialog(this, "Congratulations! You guessed the word! It was: " + game.getSolution());
				} else {
					JOptionPane.showMessageDialog(this, "Game over! The solution was: " + game.getSolution());
				}
			}
		}
		inputField.setText("");  //Clear input field for next guess
	}



	// Apply feedback to the tiles in TileMaker instance
	/**
	 * Apply feedback to the tiles in TileMaker instance
	 * @param feedback String array needed for feedback
	 */
	private void applyFeedback(String[] feedback) {
		int startI = (game.getNumGuesses() - 1) * 5;  // Calculate row index
		for (int i = 0; i < feedback.length; i++) {
			Color color;
			switch (feedback[i]) {
			case "green":
				color = Color.GREEN;
				break;
			case "yellow":
				color = Color.YELLOW;
				break;
			case "gray":
			default:
				color = Color.LIGHT_GRAY;
				break;
			}
			tm.getTile(startI + i).setBackground(color);  // Update tile background
		}
	}

	public static void main(String[] args) {
		new Wordler();  // Launch the game
	}
}


