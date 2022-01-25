package com.warneriveris.jsonArrayToObj.process.injectors;

import com.warneriveris.jsonArrayToObj.process.ProcessData;

/**
 * Interface to provide uniform dependency injection so that future implementations
 * of read and write services can be used with minimal change to application code.
 * 
 * @author Warner Iveris
 */
public interface ProcessDataInjector {
	public ProcessData getService(String filename);
}
