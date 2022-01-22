package com.warneriveris.jsonArrayToObj.validators.injectors;

import com.warneriveris.jsonArrayToObj.validators.implementations.UniqueKeyValidatorImpl;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

public class UniqueKeyValidatorInjector<T> implements ValidatorServiceInjector<T> {

	@Override
	public ValidatorService<T> getValidator() {
		return new UniqueKeyValidatorImpl<>();
	}

}
