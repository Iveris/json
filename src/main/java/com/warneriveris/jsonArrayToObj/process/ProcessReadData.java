package com.warneriveris.jsonArrayToObj.process;

import com.warneriveris.jsonArrayToObj.logger.Summary;
import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;
import com.warneriveris.jsonArrayToObj.services.read.ReadService;
import com.warneriveris.jsonArrayToObj.services.read.ReadServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.ReadPOJOValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

/**
 * Instantiates the read service and coordinates its output so that only 
 * validated data is shared with write services. Implements the Process Data
 * interface which itself implements the Runnable interface in order to allow
 * all read operations to be encapsulated into a single thread.
 * 
 * @author Warner Iveris
 *
 */

public class ProcessReadData implements ProcessData {

	private ReadService rs = new ReadServiceInjector().getService();
	private ValidatorService<ReadPOJO> validatePOJO = 
			new ReadPOJOValidatorServiceInjector().getValidator();

	/**
	 * Instantiates read service with input file path provided by the user
	 * 
	 * @param datapath	a string representing the path to the input file
	 */
	public ProcessReadData(String datapath) {
		ReadPOJOQueue.getInstance();
		rs.getReader(datapath);
	}


	/**
	 * Coordinates the validation of data from the read service with the exchange
	 * of that data to the write service via the shared Concurrent Linked Queue
	 * in the ReadPOJOQueue class
	 */
	@Override
	public void execute() {
		while(rs.hasNext()) {
			ReadPOJO rp = rs.next();
			if(validatePOJO.isValid(rp)) {
				ReadPOJOQueue.add(rp);
				Summary.addProcessed();
			} else {
				Summary.addSkipped();
			}
		}
	}

	/**
	 * Method to encapsulate read process into a single runnable thread which
	 * ends by closing the reader that was instantiated in the Process Read 
	 * Data method above
	 */
	@Override
	public void run() {
		execute();
		rs.closeReader();
	}
}
