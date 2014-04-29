package controller;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import view.UserInterface;
import log.LogInfo;
import log.Logger;
import model.Database;
import model.QueryByName;
import model.QueryByYear;
import model.Result;

public abstract class Controller extends Observable implements Observer {
	
	private Database data;
	private UserInterface ui;
	
	protected Controller(String dataFileName) {
		data = makeDatabase(dataFileName);
		ui = makeUI();
		ui.addObserver(this);
	}
	
	protected abstract Database makeDatabase(String dataFileName);
	protected abstract UserInterface makeUI();
	public abstract Logger makeLogger();
	
	public void start() { ui.start(); }

	@Override
	public void update(Observable arg0, Object arg1) {
		int choice = ((Integer) arg1).intValue();
		setChanged();
		notifyObservers(new LogInfo(String.valueOf(choice)));
		switch (choice) {
			case 1: { // 1: Search for best picture award winner by year
				String winner = null;
				
				while (winner == null) {
					// Get input
					Integer year = null;
					while (year == null) { year = receiveYear(); }
					
					// Get and query the database
					Map<Integer, Result> years = data.getWinners();
					winner = new QueryByYear().lookup(year, years);
					
					if (winner == null) {
						ui.answer("There were no nominations matching "
										+ year + ".");
					}
				}
				
				// Reply
				ui.answer(winner);
				return;
			}
			case 2: { // 2: Search for best picture award nominees by year
				String nominees = null;
				
				while (nominees == null) {
					// Get input
					Integer year = null;
					while (year == null) { year = receiveYear(); }
					
					// Get and query the database
					Map<Integer, Result> years = data.getNominees();
					nominees = new QueryByYear().lookup(year, years);
					
					if (nominees == null) {
						ui.answer("There were no nominations matching "
								+ year + ".");
					}
				}
				
				// Reply
				ui.answer(nominees);
				return;
			}
			case 3: { // 3: Search for actor/actress nominations by name
				String nominees = null;
				while (nominees == null) {
					
					// Get input
					String name = null;
					while (name == null) { name = receiveName(); }
					
					// Look up query
					Map<Integer, Result> years = data.getNominees();
					nominees = new QueryByName().lookup(name, years);
					
					if (nominees == null) {
						ui.answer("There were no nominations matching "
								+ name + ".");
					}
				}
				
				// Reply
				ui.answer(nominees);
				return;
			}
		}	
	}
	
	private Integer receiveYear() throws NumberFormatException {
		// Receive and log input
		Integer year = null;
		try {
			year = ui.getInputYear();
			LogInfo li = new LogInfo(year.toString());
			setChanged();
			notifyObservers(li);
		}
		catch (NumberFormatException e) {
			ui.answer("Invalid year.");
			setChanged();
			notifyObservers(new LogInfo(e.getMessage()));
			return null;
		}
		return year;
	}
	
	private String receiveName() {
		// Receive and log input
		String name = ui.getInputName();
		setChanged();
		notifyObservers(new LogInfo(name));
		return name;
	}
}
