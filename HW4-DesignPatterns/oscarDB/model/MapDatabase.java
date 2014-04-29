package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MapDatabase implements Database {
	
	// The actual "databases" of the database
	Map<Integer, Result> nominees;
	Map<Integer, Result> winners;

	// Read in the file and extract the winners from the resulting data
	public MapDatabase(String dataFileName) {
		Reader r = new CSVFileReader(dataFileName);
		nominees = r.readData();
		winners = extractWinners(nominees);
	}

	private Map<Integer, Result> extractWinners(Map<Integer, Result> original) {
		if (original == null) return null;
		
		Map<Integer, Result> w = new HashMap<Integer, Result>();
		Set<Integer> keys = original.keySet();
		for (Integer key : keys) {
			Nominations ns = (Nominations) original.get(key);
			w.put(key, ns.getWinner());
		}
		return w;
	}
	
	// ***** ACCESSORS *****
	public Map<Integer, Result> getNominees() {
		return nominees;
	}
	
	public Map<Integer, Result> getWinners() {
		return winners;
	}
}