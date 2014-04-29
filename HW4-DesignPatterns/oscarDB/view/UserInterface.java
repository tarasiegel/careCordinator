package view;

import java.util.Observable;

public abstract class UserInterface extends Observable {

	public abstract void start();
	
	public abstract String getInputName();
	public abstract Integer getInputYear() throws NumberFormatException;

	public abstract void answer(String reply);

}