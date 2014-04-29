package log;

import java.io.IOException;
import java.util.Observer;

public interface Logger extends Observer {
	
	public void setFile(String filename) throws IOException;
	public void close();
	
}
