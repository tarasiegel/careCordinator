package model;

import java.util.Map;

public class QueryByYear implements Query<Integer> {
	
	@Override
	public String lookup(Integer key, Map<Integer, Result> data) {
		
		if (key == null || data == null) { return null; }
		
		Result r = data.get(key);
		if (r == null) return null;
		r = r.getPictures();
		if (r == null) return null;
		
		String reply  = r.toString();
		if (reply.length() == 0) 
			return "There was no data for year " + key + ".";
		else 
			return "The results in year " + key + " were:\n" + reply;
	}

}