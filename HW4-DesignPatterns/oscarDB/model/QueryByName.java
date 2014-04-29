package model;

import java.util.Map;
import java.util.Set;

public class QueryByName implements Query<String> {
	
	@Override
	public String lookup(String key, Map<Integer, Result> data) {
		if (key == null || data == null) { return null; }
		
		Nominations matches = new Nominations();
		Set<Integer> keys = data.keySet();
		
		for (Integer k : keys) {
			Result match = data.get(k).getPeople(key);
			matches.add(match);
		}
		
		String reply = matches.toString();
		if (reply.equals("")) return null;
		else return "The nominations matching " + key + " are:\n" + reply;
	}
}
