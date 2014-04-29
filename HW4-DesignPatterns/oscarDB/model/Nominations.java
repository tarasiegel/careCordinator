package model;

import java.util.LinkedList;
import java.util.List;

public class Nominations implements Result {
	
	private List<Nomination> nominations;
	
	Nominations() {
		nominations = new LinkedList<Nomination>();
	}
	
	// Add any type of result to this list
	public void add(Result r) {
		if (r == null) {
			return;
		} else if (r instanceof Nominations) {
			for (Nomination n : ((Nominations) r).nominations) 
				nominations.add(n);
		} else if (r instanceof Nomination) {
			nominations.add((Nomination) r);
		} 
	}
	
	// Nominations are just many single instances in a list
	public String toString() {
		String s = "";
		for (Nomination n : nominations) {
			s += n + "\n";
		}
		
		// Remove the final \n
		if (s.length() > 0) 
			return s.substring(0, s.length() - 1);
		else return "";
	}

	// Filter the list to actors/actresses matching the argument
	@Override
	public Result getPeople(String filter) {
		Nominations people = new Nominations();
		for (Nomination n : nominations) {
			if (n.getPeople(filter) != null) { people.add(n); }
		}
		
		if (people.nominations.isEmpty()) return null;
		else return people;
	}

	// Filter the list by best picture nominations
	@Override
	public Result getPictures() {
		Nominations pictures = new Nominations();
		for (Nomination n : nominations) {
			if (n.getPictures() != null) { pictures.add(n); }
		}
		
		if (pictures.nominations.isEmpty()) return null;
		else return pictures;
	}
	
	// Find the winner of the best pictures for this year
	public Result getWinner() {
		Nominations pictures = (Nominations) getPictures();
		if (pictures == null) return null;
		
		for (Nomination n : pictures.nominations)
			if (n.won()) return n;
		return null;
	}
}
