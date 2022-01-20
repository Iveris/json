package com.warneriveris.jsonArrayToObj.validators.injectors;

import java.net.URL;

import com.warneriveris.jsonArrayToObj.validators.implementations.FileSizeValidatorServiceImpl;
import com.warneriveris.jsonArrayToObj.validators.services.FileValidatorService;

public class FileSizeValidatorServiceInjector implements FileValidatorServiceInjector<Integer, URL> {

	@Override
	public FileValidatorService<Integer, URL> getValidator() {
		return new FileSizeValidatorServiceImpl();
	}
}
