package process.injectors;

import process.ProcessData;

public interface ProcessDataInjector {
	public ProcessData getService(String filename);
}
