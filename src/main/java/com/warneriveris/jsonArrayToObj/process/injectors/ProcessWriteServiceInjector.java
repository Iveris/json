package com.warneriveris.jsonArrayToObj.process.injectors;

import com.warneriveris.jsonArrayToObj.process.ProcessData;
import com.warneriveris.jsonArrayToObj.process.ProcessWriteData;

public class ProcessWriteServiceInjector implements ProcessDataInjector {

	@Override
	public ProcessData getService(String filename) {
		ProcessData pd = new ProcessWriteData(filename);
		return pd;
	}

}
