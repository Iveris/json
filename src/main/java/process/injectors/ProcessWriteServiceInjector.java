package process.injectors;

import process.ProcessData;
import process.ProcessWriteParallelStreamData;

public class ProcessWriteServiceInjector implements ProcessDataInjector {

	@Override
	public ProcessData getService(String filename) {
		ProcessData pd = new ProcessWriteParallelStreamData(filename);
		return pd;
	}

}
