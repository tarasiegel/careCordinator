import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * This class holds all the  methods for reading from the file
 */
public class FileReader {
	
	// reference to Searcher so it can access cache
	private Searcher searcher;
	private String fileName;


	public FileReader(Searcher s, String dataFileName) {
		searcher = s;
		fileName = dataFileName;
	}

	/*
	 * Look through the datafile for an entry matching the given year and category, 
	 * in which the "result" field is set to "YES", indicating that this is the winner
	 */
	public SearchResult searchWinner(String searchYear, String searchCategory) {
		
		log("Searching data file with year " + searchYear + " and category " + searchCategory);

		Scanner in = null;
		
		try {
			in = new Scanner(new File(fileName));

			while (in.hasNextLine()) {
				

				String line = in.nextLine();

				String[] tokens = line.split(",");

				String year = tokens[0].trim();
				String category = tokens[1].trim();
				String nominee = tokens[2].trim();
				String result = tokens[3].trim();

				if (searchYear == null || year.contains(searchYear)) {
					if (searchCategory == null || searchCategory.equals(category)) {
						if (result.equals("YES")) {
							SearchResult sr = new SearchResult(searchYear, searchCategory, nominee, result);
							searcher.updateCache(sr);
							return sr;
						}
					}
				}
				
			}

		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally { try {	in.close();	} catch (Exception e) {	} }
		
		return null;

	}

	/*
	 * Look through the datafile for entries matching the given year and category 
	 */
	public SearchResult[] lookThroughListAndAddResultsToArray(String searchYear, String searchCategory) {
		
		log("Searching data file with year " + searchYear + " and category " + searchCategory);

		Scanner in = null;
		
		ArrayList<SearchResult> results = new ArrayList<SearchResult>();
		
		try {
			in = new Scanner(new File(fileName));

			while (in.hasNextLine()) {
				

				String line = in.nextLine();

				String[] tokens = line.split(",");

				String year = tokens[0].trim();
				String category = tokens[1].trim();
				String nominee = tokens[2].trim();
				String result = tokens[3].trim();

				if (year.contains(searchYear) && searchCategory.equals(category)) {
					results.add(new SearchResult(searchYear, category, nominee, result));
				}
				
			}

		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally { try {	in.close();	} catch (Exception e) {	} }
		
		SearchResult[] sr = results.toArray(new SearchResult[results.size()]);
		searcher.updateCache(sr);
		return sr;
	}
	
	/*
	 * Look through the datafile for any entry that is a partial match to the given name
	 * in categories that contain "Actor" or "Actress"
	 */

	public SearchResult[] findEntriesByName(String name) {
		
		log("Searching data file for " + name);

		Scanner in = null;
		
		ArrayList<SearchResult> results = new ArrayList<SearchResult>();
		
		try {
			in = new Scanner(new File(fileName));

			while (in.hasNextLine()) {
				

				String line = in.nextLine();

				String[] tokens = line.split(",");

				String year = tokens[0].trim();
				String category = tokens[1].trim();
				String nominee = tokens[2].trim();
				String result = tokens[3].trim();

				if (nominee.contains(name) && (category.contains("Actor") || category.contains("Actress"))) {
					results.add(new SearchResult(year, category, nominee, result));
				}
				
			}

		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally { try {	in.close();	} catch (Exception e) {	} }
		
		SearchResult[] sr = results.toArray(new SearchResult[results.size()]);

		return sr;
	}

	/*
	 * write to the log file
	 */
	protected void log(String event) {
		try {
			FileWriter writer = new FileWriter(new File(UserInterface.logFileName), true);
			writer.write(System.currentTimeMillis() + " (FileReader): " + event +"\n");
			writer.flush();
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	

}
