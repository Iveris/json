package process.injectors;

import process.ProcessData;
import process.ProcessReadParallelStreamData;

public class ProcessConcurrentReadInjector implements ProcessDataInjector {

	@Override
	public ProcessData getService(String filename) {
		ProcessData pd = new ProcessReadParallelStreamData();
		pd.process(filename);
		return pd;
	}

}
