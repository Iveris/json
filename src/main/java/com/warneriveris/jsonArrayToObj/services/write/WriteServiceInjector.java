package com.warneriveris.jsonArrayToObj.services.write;

import com.warneriveris.jsonArrayToObj.services.ServiceInjector;

public class WriteServiceInjector implements ServiceInjector<WriteService>{
	public WriteService getService() {
		return new WriteStreamServiceImpl();
	}
}
