import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class holds all of the methods for interacting with the user.
 */

public class UserInterface {
	
	public static String logFileName;
	
	// for reading from the keyboard
	protected Scanner in; 
	
	// for conducting searches
	Searcher searcher; 
	
	public UserInterface(String dataFileName) {
		in = new Scanner(System.in);
		searcher = new Searcher(dataFileName);
	}

	/*
	 * This method does the interaction with the user. It shows the 
	 * prompt, branches to the appropriate method, and continues
	 * looping until the user quits.
	 */
	public void start() {
		
		System.out.println("Welcome to the Oscars database!");
		log("Starting application");

		// the thing that the user chooses to do
		String choice = null; 

		do {
			showPrompt();
			
			choice = getInputString();
			log("User input: " + choice);
			
			// 1 = find Best Picture winner in a given year
			if (choice.equals("1")) {
				log("User wants to search by year");
				println("Please enter the year: ");
				int year = getInputInt();
				// check the cache first
				String winner = searcher.checkCacheForYear(Integer.toString(year));
				// if not found, go to the datafile
				if (winner == null)	winner = searcher.getBestPictureWinner(year).getNominee();
				// if still not found, give up
				if (winner == null) System.out.println("There was no winner in " + year);
				else System.out.println("The winner in " + year + " was " + winner);
				
			}
			// 2 = list Best Picture nominees in a given year
			else if (choice.equals("2")) {
				searcher.showBestPictureNominees();
			}
			// 3 = find all award (wins and nomination) for a given actor/actress
			else if (choice.equals("3")) {
				log("User wants to search by name");
				println("Please enter all or part of the person's name: ");
				String name = getInputString();
				SearchResult[] results = searcher.getReader().findEntriesByName(name);
				if (results.length == 0) println("No results found for " + name);
				for (SearchResult sr : results) {
					if (sr.getResult().equals("YES"))
						println(sr.getNominee() + " won " + sr.getCategory() + " in " + sr.getYear());
					else
						println(sr.getNominee() + " was nominated for " + sr.getCategory() + " in " + sr.getYear());
				}
				
			}
			else if (!quit(choice)) {
				println("That is not a valid selection.");
			}
			
			
		}
		while (!quit(choice));
		println("Good bye");
	}

	/*
	 * This just shows the prompt/menu
	 */
	protected void showPrompt() {
		println("");
		println("Please make your selection:");
		println("1: Search for best picture award winner by year");
		println("2: Search for best picture award nominees by year");
		println("3: Search for actor/actress nominations by name");
		println("Q: Quit");
		print("> ");

	}

	/*
	 * Determining whether or not to quit
	 */
	private boolean quit(String choice) {
		return choice.equals("Q") || choice.equals("q");
	}

	/*
	 * Helper methods for writing to the screen.
	 */
	private void println(String s) { System.out.println(s); }
	private void print(String s) { System.out.print(s); }
	
	/*
	 * Helper methods for reading from keyboard.
	 */
	protected String getInputString() {
		return in.nextLine();
	}
	protected int getInputInt() {
		return in.nextInt();
	}

	/*
	 * For logging to the file
	 */
	protected void log(String event) {
		try {
			FileWriter writer = new FileWriter(new File(logFileName), true);
			writer.write(System.currentTimeMillis() + " (UserInterface): " + event +"\n");
			writer.flush();
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java UserInterface [name of data file] [name of log file]");
		}
		logFileName = args[1];
		UserInterface ui = new UserInterface(args[0]);
		ui.start();

	}
}
