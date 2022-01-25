package com.warneriveris.jsonArrayToObj.process.injectors;

import com.warneriveris.jsonArrayToObj.process.ProcessData;
import com.warneriveris.jsonArrayToObj.process.ProcessWriteData;

/**
 * Write service processing injector to allow changes in write processing
 * implementation with minimal changes to application code.
 * 
 * @author Warner Iveris
 */

public class ProcessWriteServiceInjector implements ProcessDataInjector {

	@Override
	public ProcessData getService(String filename) {
		ProcessData pd = new ProcessWriteData(filename);
		return pd;
	}

}
