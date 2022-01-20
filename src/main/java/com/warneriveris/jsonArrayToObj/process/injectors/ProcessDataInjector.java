package com.warneriveris.jsonArrayToObj.process.injectors;

import com.warneriveris.jsonArrayToObj.process.ProcessData;

public interface ProcessDataInjector {
	public ProcessData getService(String filename);
}
