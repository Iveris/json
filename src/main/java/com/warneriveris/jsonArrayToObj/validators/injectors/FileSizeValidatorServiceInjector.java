package com.warneriveris.jsonArrayToObj.validators.injectors;

import com.warneriveris.jsonArrayToObj.validators.implementations.FileSizeValidatorServiceImpl;
import com.warneriveris.jsonArrayToObj.validators.services.FileValidatorService;

public class FileSizeValidatorServiceInjector implements FileValidatorServiceInjector<Integer, String> {

	@Override
	public FileValidatorService<Integer, String> getValidator() {
		return new FileSizeValidatorServiceImpl();
	}
}
