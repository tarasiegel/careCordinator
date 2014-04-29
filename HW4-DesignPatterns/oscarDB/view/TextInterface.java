package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class holds all of the methods for interacting with the user.
 */

public class TextInterface extends UserInterface {
	
	private BufferedReader in;
	private static final int QUIT = 0;
	private static final int INVALID = 4;
	
	
	public TextInterface() {
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	/*
	 * This method does the interaction with the user. It shows the 
	 * prompt, branches to the appropriate method, and continues
	 * looping until the user quits.
	 */
	@Override
	public void start() {
		
		println("Welcome to the Oscars database!");

		do {
			showPrompt();
			
			int choice = parseInputChoice();
			if (choice == QUIT) { break; }
			else if (choice == INVALID) { println("Invalid input."); } 
			else {
				setChanged();
				notifyObservers(choice);
			}
		}
		while (true);
		println("Good bye!");
	}

	/*
	 * This just shows the prompt/menu
	 */
	private void showPrompt() {
		println("");
		println("Please make your selection:");
		println("1: Search for best picture award winner by year");
		println("2: Search for best picture award nominees by year");
		println("3: Search for actor/actress nominations by name");
		println("Q: Quit");
		print("> ");
	}

	/*
	 * Helper methods for writing to the screen.
	 */
	private void println(String s) { System.out.println(s); }
	private void print(String s) { System.out.print(s); }
	
	/*
	 * Helper methods for reading from keyboard.
	 */
	
	private int parseInputChoice() {
		int choice = INVALID;
		
		try {
			
			String input = in.readLine();
			if (input == null) { return QUIT; }
			
			if ("Q".equals(input.toUpperCase())) { return QUIT; }
			else { choice = Integer.parseInt(input); }
		} catch (Exception e) { return INVALID; }
		
		if (QUIT < choice && choice < INVALID) { return choice; }
		else { return INVALID; }
	}

	@Override
	public String getInputName() {
		println("Please enter the query word.");
		print("> ");
		try {
			String line = in.readLine();
			return line;
		} catch (IOException e) {
			return null;
		}
	}
	
	@Override
	public Integer getInputYear() throws NumberFormatException {
		println("Please enter the year.");
		print("> ");
		String line = "";
		try {
			line = in.readLine();
			return Integer.parseInt(line);
		} catch (IOException e) {
			println("Error reading input year.");
			return null;
		} catch (NumberFormatException e) {
			throw new NumberFormatException(line);
		}
	}
	
	/* 
	 * Handle a response by printing it
	 */
	@Override
	public void answer(String reply) {
		println(reply);
	}
}
