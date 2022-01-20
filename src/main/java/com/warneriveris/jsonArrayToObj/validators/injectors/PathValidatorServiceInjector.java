package com.warneriveris.jsonArrayToObj.validators.injectors;

import com.warneriveris.jsonArrayToObj.validators.implementations.PathValidatorServiceImpl;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

public class PathValidatorServiceInjector implements ValidatorServiceInjector<String> {

	@Override
	public ValidatorService<String> getValidator() {
		return new PathValidatorServiceImpl();
	}

}
