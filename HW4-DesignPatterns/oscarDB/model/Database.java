package model;

import java.util.Map;

public interface Database {
	
	//public Nomination getWinnerInYear(int y);
	//public <E> List<Nomination> getNominees(E key);
	public Map<Integer, Result> getNominees();
	public Map<Integer, Result> getWinners();
	
}
