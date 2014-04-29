package log;

public class LogInfo {
	
	private String input;
	
	public LogInfo(String input) { this.input = input; }
	
	public String toString() {
		return "\t" + input + "\t\t\t(" + System.currentTimeMillis() + ")";
	}

}
