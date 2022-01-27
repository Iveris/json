package com.warneriveris.jsonArrayToObj.services.write;

/**
 * Interface which defines the behavior of WriteService implementations.
 * 
 * 
 * @author Warner Iveris
 *
 */

public interface WriteService {
	public WriteService getWriter(String filename);
	public void write();
	public void closeWriter();
}
