import java.io.IOException;
import controller.Controller;
import controller.Searcher;
import log.Logger;

public class Main {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: " +
							"java Main [name of data file] [name of log file]");
		}

		Controller app = new Searcher(args[0]);
		Logger log = app.makeLogger();
		try { log.setFile(args[1]); } 
		catch (IOException e) {
			System.out.println("Error opening log file");
			return;
		}
		
		app.addObserver(log);
		app.start();
		log.close();
	}
	
}
