package process;

import java.util.concurrent.Callable;

public interface ProcessData extends Callable<Boolean>{
	
	public void process(String datapath);
	public void execute();
	public void readComplete(boolean complete);
}
