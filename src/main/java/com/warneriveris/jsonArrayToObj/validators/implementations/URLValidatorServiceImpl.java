package com.warneriveris.jsonArrayToObj.validators.implementations;

import org.apache.commons.validator.routines.UrlValidator;

import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

public class URLValidatorServiceImpl implements ValidatorService<String>{

	@Override
	public boolean isValid(String URL) {
		return new UrlValidator().isValid(URL);
	}
}
