package com.warneriveris.jsonArrayToObj.validators.injectors;

import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

public interface ValidatorServiceInjector<T> {

	ValidatorService<T> getValidator();
}
