package com.warneriveris.jsonArrayToObj.validators.injectors;

import com.warneriveris.jsonArrayToObj.validators.implementations.FileSizeValidatorServiceImpl;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

public class FileSizeValidatorServiceInjector implements ValidatorServiceInjector<Integer> {

	@Override
	public ValidatorService<Integer> getValidator() {
		return new FileSizeValidatorServiceImpl();
	}
}
