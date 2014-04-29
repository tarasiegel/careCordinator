package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public abstract class Reader {
	protected BufferedReader data;
	
	protected Reader(String filename) {
		if (filename == null || "".equals(filename)) { return; }
		try {
			data = new BufferedReader(new FileReader(filename));
		} catch (Exception e) {
			System.out.println("Error opening data file");
		}
	}
	
	// Subclasses define how to parse a line
	protected abstract Nomination parseLine(String line);
	
	// Read from the data file, if opened
	Map<Integer, Result> readData() {
		if (data == null) { return null; }
		
		Map<Integer, Result> years = 
				new HashMap<Integer, Result>();
		try {
			String line = null;
			while ((line = data.readLine()) != null) {
				// Parse the nomination
				Nomination n = parseLine(line);
				
				// Add it to the year map
				int year = n.getYear();
				Nominations yearList = (Nominations) years.get(year);
				if (yearList == null) yearList = new Nominations();
				yearList.add(n);
				years.put(year, yearList);
			}
		} catch (Exception e) { 
			System.out.println("Error reading data file. " +
							   "Results may be partial.");
			return years;
		} finally { try { data.close(); }
				    catch (Exception e) {
				    	System.out.println("Error closing data file. " +
								   "Results may be partial.");
						return years;
					}
		}
		return years;
	}
}
