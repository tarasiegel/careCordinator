/**
 * This class represents a single entry in the data file.
 *
 */


public class SearchResult {
	
	private String year;
	private String category;
	private String nominee;
	private String result;
	
	public SearchResult(String y, String c, String n, String r) {
		year = y;
		category = c;
		nominee = n;
		result = r;
	}
	
	public String getYear() { return year; }
	public String getCategory() { return category; }
	public String getNominee() { return nominee; }
	public String getResult() { return result; }

}
