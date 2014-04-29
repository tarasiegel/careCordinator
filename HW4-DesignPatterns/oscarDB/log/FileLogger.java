package log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;


public class FileLogger implements Logger {
	
	private static FileLogger log; // Singleton instance
	private FileWriter writer;     // Actual "FileLogger"
	
	/*
	 * If a FileLogger already exists, return that.
	 * Otherwise creates a new FileLogger with the given log name.
	 */
	public static FileLogger getInstance() {
		if (log == null) log = new FileLogger();
		return log;
	}
	
	private FileLogger() { }
	
	public void setFile(String filename) throws IOException {
		this.writer = new FileWriter(new File(filename), true);
		this.writer.write("\tInput\t\t\tTime Entered\n");
	}
	
	public void close() {
		try { this.writer.close(); }
		catch (IOException e) { }
	}
	
	/*
	 * Write to the log file
	 */
	public void update(Observable arg0, Object arg1) {
		if (writer == null || arg1 == null) { return; }
		
		LogInfo li = (LogInfo) arg1;
		
		try {
			writer.write(li +"\n");
			writer.flush();
		}
		catch (Exception e) {  }
	}
}
