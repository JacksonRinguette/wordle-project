package Wordle;

/**
 * TileMaker Class
 * 
 * Manages a grid of tiles for displaying guesses in a Wordle-style game.
 * Each tile represents a single letter, with color changes to indicate feedback.
 * 
 * Author: Nathan Kostynick, Jackson Ringuette, Max Sampson, Tan Vo
 * Date: December 8th, 2024
 */

import java.awt.*;
import javax.swing.*;


public class TileMaker extends JPanel {
	private final static int NPANELS = 30;  // 6 rows x 5 columns = 30 tiles
	private JTextField[] letterSlots; 
	private int currentRow = 0;  // Tracks the current row to update

	/**
     * Constructor for TileMaker.
     * Initializes a grid of tiles with 6 rows and 5 columns.
     */
	public TileMaker() {
		letterSlots = new JTextField[NPANELS];
		for (int i = 0; i < letterSlots.length; i++) {
			letterSlots[i] = new JTextField(1);
			letterSlots[i].setEditable(false);  // Disable editing by default
			letterSlots[i].setHorizontalAlignment(JTextField.CENTER);  // Center-align text in the tile
			letterSlots[i].setFont(new Font("Arial", Font.BOLD, 20)); //Larger font
			letterSlots[i].setBackground(Color.WHITE);  // Default background color to white
			add(letterSlots[i]);
		}
		setLayout(new GridLayout(6, 5, 5, 5));  // 6 rows, 5 columns, with padding
	}

	/**
	 * Updates the current row with the letters of users guess
	 * @param guess inputted by user
	 */
	public void updateRow(String guess) {
		if (currentRow >= 6 || guess.length() != 5) {
			return;  // Do nothing if rows are full or input is invalid
		}

		for (int i = 0; i < 5; i++) {
			JTextField slot = letterSlots[currentRow * 5 + i];
			slot.setText(Character.toString(guess.charAt(i)).toUpperCase());
		}
		currentRow++;  // Move to the next row
	}
	
	/**
	 * Gets the tile given the specific index
	 * @param index of the tile in the grid
	 * @return JTextField representing the tile at the given index
	 */
	public JTextField getTile(int index) {
		return letterSlots[index];
	}
}


