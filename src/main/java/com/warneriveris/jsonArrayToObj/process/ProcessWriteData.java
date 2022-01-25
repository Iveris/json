package com.warneriveris.jsonArrayToObj.process;

import com.warneriveris.jsonArrayToObj.services.write.WriteService;
import com.warneriveris.jsonArrayToObj.services.write.WriteServiceInjector;

/**
 * Instantiates the write service and encapsulates it in its own thread.
 * 
 * @author Warner Iveris
 *
 */

public class ProcessWriteData implements ProcessData {
	
	
	private WriteService ws = new WriteServiceInjector().getService();
	
	/**
	 * Instantiates writing service writer object with path to output file
	 * that is either provided by the user or contains the default value of
	 * "output.json."
	 * 
	 * @param datapath	a string containing the path for the output file
	 */
	public ProcessWriteData(String datapath) {
		ws.getWriter(datapath);
	}

	/**
	 * Implements Process Data method by calling the write service class to 
	 * begin writing to the output file. Although the write process in 
	 * this particular case is quite simple, this method could potentially 
	 * provide additional data validation or processing as is done in the 
	 * Process Read Data class.
	 */
	@Override
	public void execute() {
		ws.write();
	}
	
	/**
	 * Method required by the Thread.class to make this class runnable by a 
	 * single thread.
	 */
	@Override
	public void run() {
		execute();
	}
}
