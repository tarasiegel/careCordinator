package model;

import java.io.IOException;

public class Nomination implements Result {

	protected int year;		   // when the nominee was nominated	
	protected String ordinality; // second part of year entry
	protected String category;   // category nominated for
	protected String name;       // nominee's name as per input file
	protected String nameLC;     // nominee's name all lowercase
	protected boolean won;
	protected String result;
		
	/**
	 * 
	 * @param year - The year and ordinality of the entry
	 * @param category
	 * @param name - The nominee
	 * @param won - "YES" or "NO" -> true or false
	 * @throws IOException - If any parameters are invalid. Makes it easy
	 * 						 to deal with in loadFiles()
	 */
	Nomination(String year, String category, 
			String name, String won) throws IOException {
		// Corner cases
		if (year == null || category == null || name == null) {
			throw new IOException("Null inputs, could not construct " +
					"the nomination");
		}
		
		// Extract the year and ordinality
		String[] words = year.split(" ");
		if (words.length != 2) {
			throw new IOException("Poorly formed year + ordinality");
		} else {
			// Extract the year
			String[] o = words[0].split("/");
			if (o.length == 1) { // If there's no "/", set the year
				this.year = Integer.parseInt(o[0]);
			} else if (o.length == 2) { // If there's a "/", use the 2nd
				this.year = Integer.parseInt("" +  o[0].charAt(0) + 
						o[0].charAt(1) + o[1].charAt(0) + o[1].charAt(1));
			} else { // No other valid year formats
				throw new IOException("Poorly formed year");
			}
			
			// Set the ordinality
			this.ordinality = words[1];
		}
		
		// Set the rest of the information
		this.category = category;
		this.name = name;
		this.nameLC = name.trim().toLowerCase();
		this.result = won;
		if (won == null || !won.toUpperCase().equals("YES")) {
			this.won = false;
		} else {
			this.won = true;
		}
	}
	
	protected Nomination(Nomination other) {
		if (other == null) { return; }
		this.year = other.year;
		this.ordinality = other.ordinality;
		this.category = other.category;
		this.name = other.name;
		this.nameLC = other.nameLC;
		this.won = other.won;
		this.result = other.result;
	}
	
	public int getYear() { return year; }
	public String getOrdinality() { return ordinality; }
	public String getCategory() { return category; }
	public String getName() { return name; }
	public String getNameLC() { return nameLC; }
	public boolean won() { return won; }

	public String toString() {
		return "{ Name : " + name + ", Category : " + category + 
				", Year : " + year + " }";
	}
	
	// Filter this instance for actors/actresses containing the filter
	@Override
	public Result getPeople(String filter) {
		if ((category.toUpperCase().contains("ACTOR") ||
			category.toUpperCase().contains("ACTRESS"))
			&&
			name.toUpperCase().contains(filter.toUpperCase())) {
			return this;
		} else return null;
	}

	// Filter this instance for best pictures
	@Override
	public Result getPictures() {
		if (category.toUpperCase().contains("BEST PICTURE")) return this;
		else return null;
	}
}
