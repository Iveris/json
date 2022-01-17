package process.injectors;

import process.ProcessData;
import process.ProcessReadData;

public class ProcessReadDataInjector implements ProcessDataInjector {

	@Override
	public ProcessData getService(String filename) {
		ProcessData pd = new ProcessReadData();
		pd.process(filename);
		return pd;
	}

}
