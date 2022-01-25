package com.warneriveris.jsonArrayToObj.process;

/**
 * Interface to ensure concurrent data processing.
 * 
 * @author Warner Iveris
 */
public interface ProcessData extends Runnable{
	
	public void execute();
}
