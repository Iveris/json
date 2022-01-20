package com.warneriveris.jsonArrayToObj.process;

import com.warneriveris.jsonArrayToObj.logger.Summary;
import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;
import com.warneriveris.jsonArrayToObj.services.read.ReadService;
import com.warneriveris.jsonArrayToObj.services.read.ReadServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.ReadPOJOValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

public class ProcessReadData implements ProcessData {

	private ReadService rs;
	private ValidatorService<ReadPOJO> validatePOJO = 
			new ReadPOJOValidatorServiceInjector().getValidator();

	public ProcessReadData(String datapath) {
		rs = new ReadServiceInjector().getService();
		rs.getReader(datapath);
	}

	@Override
	public void execute() {
		while(rs.hasNext()) {
			ReadPOJO rp = (ReadPOJO) rs.next(ReadPOJO.class);
			if(validatePOJO.isValid(rp)) {
				ReadPOJOQueue.add(rp);
				Summary.addSuccess();
			} else {
				Summary.addSkipped();
			}
		}
	}

	@Override
	public void run() {
		execute();
		rs.closeReader();
	}
}
