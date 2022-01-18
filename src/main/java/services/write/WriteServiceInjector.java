package services.write;

import services.ServiceInjector;

public class WriteServiceInjector implements ServiceInjector<WriteService>{
	public WriteService getService() {
		return new WriteStreamServiceImpl();
	}
}
