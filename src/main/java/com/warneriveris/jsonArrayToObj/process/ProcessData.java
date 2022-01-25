package com.warneriveris.jsonArrayToObj.process;

/**
 * Interface to aid the processing and exchange of data between the read
 * services and the write services. Implements Runnable to allow this processing
 * to occur concurrently for efficiency.
 * 
 * @author Warner Iveris
 */
public interface ProcessData extends Runnable{
	
	public void execute();
}
