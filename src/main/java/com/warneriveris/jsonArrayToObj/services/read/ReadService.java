package com.warneriveris.jsonArrayToObj.services.read;

/**
 * Interface to define expected behavior of a read service implementation
 * 
 * @author Warner Iveris
 *
 */
public interface ReadService {
	public ReadService getReader(String filename);
	public void closeReader();
	public boolean hasNext();
	public ReadPOJO next();
}
