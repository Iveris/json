package com.warneriveris.jsonArrayToObj.process.injectors;

import com.warneriveris.jsonArrayToObj.process.ProcessData;
import com.warneriveris.jsonArrayToObj.process.ProcessReadData;

/**
 * Read service injector to allow read service processing to be changed
 * with minimal changes to application code.
 * 
 * @author Warner Iveris
 */

public class ProcessReadServiceInjector implements ProcessDataInjector {

	@Override
	public ProcessData getService(String filename) {
		ProcessData pd = new ProcessReadData(filename);
		return pd;
	}

}
