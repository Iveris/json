package process.injectors;

import process.ProcessData;
import process.ProcessWriteData;

public class ProcessWriteServiceInjector implements ProcessDataInjector {

	@Override
	public ProcessData getService(String filename) {
		ProcessData pd = new ProcessWriteData(filename);
		return pd;
	}

}
