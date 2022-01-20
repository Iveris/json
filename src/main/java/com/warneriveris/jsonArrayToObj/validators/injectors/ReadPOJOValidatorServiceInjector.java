package com.warneriveris.jsonArrayToObj.validators.injectors;

import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;
import com.warneriveris.jsonArrayToObj.validators.implementations.ReadPOJOValidatorImpl;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

public class ReadPOJOValidatorServiceInjector implements ValidatorServiceInjector<ReadPOJO> {

	@Override
	public ValidatorService<ReadPOJO> getValidator() {
		ValidatorService<ReadPOJO> vs = new ReadPOJOValidatorImpl();
		return vs;
	}

}
