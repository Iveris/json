package com.warneriveris.jsonArrayToObj.validators.injectors;

import com.warneriveris.jsonArrayToObj.validators.implementations.URLValidatorServiceImpl;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

public class URLValidatorServiceInjector implements ValidatorServiceInjector<String> {

	@Override
	public ValidatorService<String> getValidator() {
		return new URLValidatorServiceImpl();
	}

}
