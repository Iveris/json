package process.injectors;

import process.ProcessData;
import process.ProcessWriteParallelStreamData;

public class ProcessParallelWriteInjector implements ProcessDataInjector {

	@Override
	public ProcessData getService(String filename) {
		ProcessData pd = new ProcessWriteParallelStreamData();
		pd.process(filename);
		return pd;
	}

}
