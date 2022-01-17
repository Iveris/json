package process.injectors;

import process.ProcessData;
import process.ProcessWriteData;

public class ProcessWriteDataInjector implements ProcessDataInjector {

	@Override
	public ProcessData getService(String filename) {
		ProcessData pd = new ProcessWriteData();
		pd.process(filename);
		return pd;
	}

}
