import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Set;


/*
 * This class acts as a middle layer and contains the methods for getting data 
 * from the FileReader. It also holds the cached data and a few other helper
 * methods.
 */

public class Searcher {
	
	// for reading from the file
	private FileReader reader;
	
	// caches for Best Picture award winners and nominees
	private HashMap<Integer, String> bestPictureWinners = new HashMap<Integer, String>();
	private HashMap<Integer, String[]> bestPictureNominees = new HashMap<Integer, String[]>();
	
	public Searcher(String dataFileName) {
		reader = new FileReader(this, dataFileName);
	}
	
	public FileReader getReader() { return reader; }
	
	/*
	 * Uses the FileReader to look for the Best Picture award winner
	 */
	public SearchResult getBestPictureWinner(int year) {
		
		log("Searching for best picture winner in " + year);
	
		SearchResult result = reader.searchWinner(Integer.toString(year), "Best Picture");
		
		return result;
	}
	
	/*
	 * Uses the FileReader to look for the Best Picture award nominees
	 */
	public String[] getBestPictureNominees(int year) {
		
		log("Searching for best picture nominees in " + year);
		
		SearchResult[] result = reader.lookThroughListAndAddResultsToArray(Integer.toString(year), "Best Picture");
				
		// need to convert the SearchResult[] to a String[]
		String[] results = new String[result.length];
		for (int i = 0; i < results.length; i++)
			results[i] = result[i].getNominee();
		
		return null;
	}
	
	/*
	 * Ask the user to enter the year, then show all the nominees
	 */
	public void showBestPictureNominees() {
		System.out.println("Please enter the year: ");
		int year = new UserInterface(null).getInputInt();
		// check the cache first
		String[] nominees = checkBestPictureNomineesCache(Integer.toString(year));
		// if not there, then check the file
		if (nominees == null) nominees = getBestPictureNominees(year);
		// if nothing found, then shown an error message
		if (nominees == null) System.out.println("There were no nominees in " + year);
		else {
			System.out.println("Here are the nominees in " + year + ":");
			for (String nom : nominees) System.out.println(nom);
		}

	}

	/*
	 * Update the Best Picture winners cache after a cache miss
	 */
	public void updateCache(SearchResult sr) {
		if (sr.getCategory().equals("Best Picture")) {
			bestPictureWinners.put(Integer.parseInt(sr.getYear()), sr.getNominee());
			log("Updating cache with key " + Integer.parseInt(sr.getYear()));
		}
	}

	/*
	 * Update the Best Picture nominees cache after a cache miss
	 */
	public void updateCache(SearchResult[] sr) {
		String[] nominees = new String[sr.length];
		for (int i = 0; i < nominees.length; i++) nominees[i] = sr[i].getNominee();

		if (sr[0].getCategory().equals("Best Picture")) {
			bestPictureNominees.put(Integer.parseInt(sr[0].getYear()), nominees);
			log("Updating cache with key " + Integer.parseInt(sr[0].getYear()));
		}
	}

	/*
	 * Look in the Best Picture winners cache
	 */
	public String checkCacheForYear(String year) {
		log("Looking in cache for key " + year);
		if (bestPictureWinners.containsKey(Integer.parseInt(year))) {
			log("cache hit for winners in " + year);
			return bestPictureWinners.get(year);
		}
		else {
			log("cache miss for winners in " + year);
		}
		return null;
	}

	/*
	 * Look in the Best Picture nominees cache
	 */
	public String[] checkBestPictureNomineesCache(String year) {
		log("Looking in cache for key " + year);
		if (bestPictureNominees.containsKey(Integer.parseInt(year))) {
			log("cache hit for nominees in " + year);
			return bestPictureNominees.get(year);
		}
		else {
			log("cache miss for nominees in " + year);
		}
		return null;
	}


	/*
	 * write to the log file
	 */
	protected void log(String event) {
		try {
			FileWriter writer = new FileWriter(new File(UserInterface.logFileName), true);
			writer.write(System.currentTimeMillis() + " (Searcher): " + event +"\n");
			writer.flush();
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	


}
