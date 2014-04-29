package model;

import java.io.IOException;

public class CSVFileReader extends Reader {
	
	public CSVFileReader(String filename) {
		super(filename);
		if (filename == null || "".equals(filename)) { return; }
	}
	
	// Read a CSV file by parsing each line into a nomination
	protected Nomination parseLine(String line) {
		String[] tokens = line.split(",");

		String year = tokens[0].trim();
		String category = tokens[1].trim();
		String nominee = tokens[2].trim();
		String result = tokens[3].trim();
		
		try {
			return new Nomination(year, category, nominee, result);
		} catch (IOException e) {
			return null;
		}
	}
}
