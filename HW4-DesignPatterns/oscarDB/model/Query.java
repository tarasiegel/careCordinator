package model;

import java.util.Map;

public interface Query<E> {
	
	// For querying a database for a given year or for a given name
	public String lookup(E key, Map<Integer, Result> data);
	
}
