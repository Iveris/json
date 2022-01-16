package process;

public interface ProcessData {

	//validate data from read
	//send that data to be written
	//throw exceptions and write error messages
	
	public void process(String datapath);
	
	public void execute();
	
}
