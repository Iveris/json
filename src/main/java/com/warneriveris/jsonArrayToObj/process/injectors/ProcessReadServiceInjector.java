package com.warneriveris.jsonArrayToObj.process.injectors;

import com.warneriveris.jsonArrayToObj.process.ProcessData;
import com.warneriveris.jsonArrayToObj.process.ProcessReadData;

public class ProcessReadServiceInjector implements ProcessDataInjector {

	@Override
	public ProcessData getService(String filename) {
		ProcessData pd = new ProcessReadData(filename);
		return pd;
	}

}
