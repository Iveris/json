package com.warneriveris.jsonArrayToObj.validators.implementations;

import com.warneriveris.jsonArrayToObj.errors.ErrorReporter;
import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;
import com.warneriveris.jsonArrayToObj.validators.injectors.FileSizeValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.PathValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.URLValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.UniqueKeyValidatorInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.ValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

public class ReadPOJOValidatorImpl implements ValidatorService<ReadPOJO> {

	ValidatorService<String> validatePath;
	ValidatorService<String> uniqueKey;
	ValidatorService<String> validateURL;
	ValidatorService<Integer> validateFileSize;
	ValidatorServiceInjector<String> injector;

	public ReadPOJOValidatorImpl() {
		injector = new PathValidatorServiceInjector();
		validatePath = injector.getValidator();
		injector = new UniqueKeyValidatorInjector<>();
		uniqueKey = injector.getValidator();
		injector = new URLValidatorServiceInjector();
		validateURL = injector.getValidator();
		injector = null;
		validateFileSize = new FileSizeValidatorServiceInjector().getValidator();
	}

	@Override
	public boolean isValid(ReadPOJO pojo) {
		boolean result = true;
		if (pojo == null) {
			return false;
		}
		if (!validatePath.isValid(pojo.getPath())) {
			ErrorReporter.add("Entry skipped due to invalid path");
			result = false;
		}
		if (!uniqueKey.isValid(pojo.getPath())) {
			ErrorReporter.add("Entry skipped because there " + "is already a path labelled " + pojo.getPath());
			result = false;
		}
		if (!validateURL.isValid(pojo.getUrl())) {
			result = false;
		}
		if (!validateFileSize.isValid(pojo.getSize())) {
			ErrorReporter.add("Entry skipped due to invalid size value");
			result = false;
		}
		return result;
	}

}
