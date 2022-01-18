package process.injectors;

import process.ProcessData;
import process.ProcessReadParallelStreamData;

public class ProcessReadServiceInjector implements ProcessDataInjector {

	@Override
	public ProcessData getService(String filename) {
		ProcessData pd = new ProcessReadParallelStreamData(filename);
		return pd;
	}

}
