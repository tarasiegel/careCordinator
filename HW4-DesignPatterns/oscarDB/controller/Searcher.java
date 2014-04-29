package controller;

import log.FileLogger;
import log.Logger;
import model.Database;
import model.MapDatabase;
import view.TextInterface;
import view.UserInterface;

public class Searcher extends Controller {
	
	public Searcher(String dataFileName) { super(dataFileName); }
	
	protected Database makeDatabase(String filename) {
		return new MapDatabase(filename);
	}
	
	protected UserInterface makeUI() {
		return new TextInterface();
	}
	
	public Logger makeLogger() {
		return FileLogger.getInstance();
	}

}
