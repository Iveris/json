package com.warneriveris.jsonArrayToObj.validators.implementations;

import com.warneriveris.jsonArrayToObj.errors.ErrorReporter;
import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;
import com.warneriveris.jsonArrayToObj.validators.injectors.PathValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.URLValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.ValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

public class ReadPOJOValidatorImpl implements ValidatorService<ReadPOJO> {

	ValidatorService<String> validateURL;
	ValidatorService<String> validatePath;
	ValidatorServiceInjector<String> injector;
	
	public ReadPOJOValidatorImpl() {
		injector = new URLValidatorServiceInjector();
		validateURL = injector.getValidator();
		injector = new PathValidatorServiceInjector();
		validatePath = injector.getValidator();
		injector = null;
	}
	
	@Override
	public boolean isValid(ReadPOJO pojo) {
		boolean result = true;
		if(pojo == null) {
			return false;
		}
		if(!validateURL.isValid(pojo.getUrl())) {
			ErrorReporter.add("Entry skipped due to invalid URL");
			result = false;
		}
		if(!validatePath.isValid(pojo.getPath())) {
			ErrorReporter.add("Entry skipped due to invalid path");
			result = false;
		}
		if(pojo.getSize() < 0 || pojo.getSize() > Integer.MAX_VALUE) {
			ErrorReporter.add("Entry skipped due to invalid size value");
			result = false;
		}
		return result;
	}

}
