package process.injectors;

import process.ProcessData;
import process.ProcessReadData;

public class ProcessReadServiceInjector implements ProcessDataInjector {

	@Override
	public ProcessData getService(String filename) {
		ProcessData pd = new ProcessReadData(filename);
		return pd;
	}

}
