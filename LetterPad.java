package Wordle;

/**
 * LetterPad Class
 * 
 * Implements an on-screen keyboard for a Wordle-style game. 
 * Handles user interaction and updates button colors based on feedback.
 * 
 * Author: Nathan Kostynick, Jackson Ringuette, Max Sampson, Tan Vo
 * Date: December 8th, 2024
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class LetterPad extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L; // eclipse recommended to add this

	private final String[] KEYS = {
			"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
			"A", "S", "D", "F", "G", "H", "J", "K", "L",
			"Z", "X", "C", "V", "B", "N", "M", "ENTER", "DEL"
	};


	ArrayList<Character> justLetters = new ArrayList<>(Arrays.asList(
			'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P',
			'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L',
			'Z', 'X', 'C', 'V', 'B', 'N', 'M'
			));

	private JTextField inputField;
	private TileMaker tileMaker;

	/*
	 * TileMaker instance needs to be used in order to update the tile with the letter entered
	 * from the "inputField" in each tile
	 * Reminder: a tile is comprised of a JPanel containing a JTextfield, you must access each panel and then the
	 * text field within
	 */

	private JButton[] buttons;

	
	public LetterPad(JTextField inputField, TileMaker tileMaker) {
		this.inputField = inputField;
		this.tileMaker = tileMaker;
		setLayout(new GridLayout(3, 10)); // 3 rows and 10 columns


		buttons = new JButton[KEYS.length];
		/*
		 * So originally there was not list because we could just set and forget the buttons containing each key
		 * now there has to be a list of buttons because we need to access each one in order to change its color
		 */

		/**
        for (String key : KEYS) {

            JButton button = new JButton(key);
            button.addActionListener(this);
            add(button);

        }
		 */


		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Oracle swing UI manager
		} catch (Exception e) {
			System.out.println(e);
		}


		for (int i = 0; i < KEYS.length; i++) {
			buttons[i] = new JButton(KEYS[i]);
			buttons[i].addActionListener(this);

			//buttons[i].setBackground(Color.ORANGE);  //used to test the setting of background


			add(buttons[i]);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		/*
		 * The command in this case is any letter that gets typed
		 * but can also be whenever the enter or delete key is pressed or any other button we add in the future
		 */

		if ("ENTER".equals(command)) {
			String text = inputField.getText();
			if (text.length() == 5) {
				tileMaker.updateRow(text); // Update TileMaker with the letters
				inputField.setText(""); // Clear the input field after submission
			}
		} else if ("DEL".equals(command)) {
			String text = inputField.getText();
			if (!text.isEmpty()) {
				inputField.setText(text.substring(0, text.length() - 1));
			}
		} else {
			// Append the letter to the input field (up to 5 characters)
			if (inputField.getText().length() < 5) {
				inputField.setText(inputField.getText() + command);
			}
		}

	}// end actionPerformed

	/*
	 * The letterColorMap will always have just 5 
	 */


	//the problem has to be here somewhere
	public void updateColors(Map<Character, String> letterColorMap) {
		for (Map.Entry<Character, String> entry : letterColorMap.entrySet()) {
			// 's' : "green"
			switch(entry.getValue()) {

			case "green":
				buttons[justLetters.indexOf(Character.toUpperCase(entry.getKey()))].setBackground(Color.GREEN);
				break;
			case "yellow":
				buttons[justLetters.indexOf(Character.toUpperCase(entry.getKey()))].setBackground(Color.YELLOW);
				break;
			case "gray":
				buttons[justLetters.indexOf(Character.toUpperCase(entry.getKey()))].setBackground(Color.GRAY);
				break;
			default:

				//buttons[justLetters.indexOf(Character.toUpperCase(entry.getKey()))].setBackground(null);
				break;
			}

		}

	}


}


//main for testing

/**
    public static void main(String[] args) {
        JFrame frame = new JFrame("Wordle Letterpad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JTextField inputField = new JTextField(10);
        inputField.setHorizontalAlignment(JTextField.CENTER);

        LetterPad letterpad = new LetterPad(inputField);

        frame.setLayout(new BorderLayout());
        frame.add(inputField, BorderLayout.NORTH);
        frame.add(letterpad, BorderLayout.CENTER);

        frame.setVisible(true);
    }
 */

