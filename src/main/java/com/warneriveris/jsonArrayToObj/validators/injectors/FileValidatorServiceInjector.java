package com.warneriveris.jsonArrayToObj.validators.injectors;

import com.warneriveris.jsonArrayToObj.validators.services.FileValidatorService;

public interface FileValidatorServiceInjector<A, F> {

	FileValidatorService<A, F> getValidator();
}
