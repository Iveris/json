package com.warneriveris.jsonArrayToObj.services.write;

public interface WriteService {
	public WriteService getWriter(String filename);
	public void write();
	public void closeWriter();
}
