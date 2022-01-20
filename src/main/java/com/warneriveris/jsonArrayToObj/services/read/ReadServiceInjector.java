package com.warneriveris.jsonArrayToObj.services.read;

import com.warneriveris.jsonArrayToObj.services.ServiceInjector;

public class ReadServiceInjector implements ServiceInjector<ReadService> {

	@Override
	public ReadService getService() {
		return new ReadParallelStreamLocalFileService();
	}

}
