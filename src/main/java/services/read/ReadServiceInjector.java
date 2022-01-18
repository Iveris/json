package services.read;

import services.ServiceInjector;

public class ReadServiceInjector implements ServiceInjector<ReadService> {

	@Override
	public ReadService getService() {
		return new ReadParallelStreamLocalFileService();
	}

}
