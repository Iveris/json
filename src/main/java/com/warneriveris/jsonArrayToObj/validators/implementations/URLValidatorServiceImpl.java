package com.warneriveris.jsonArrayToObj.validators.implementations;

import org.apache.commons.validator.routines.UrlValidator;

import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

/**
 * Validates that the string passed to the isValid method has a valid URL construction.
 * It does NOT validate that the URL or the file it points to actually exists.
 * 
 * This implementation is provided by the Apache Commons Routines URL Validator
 * class. This class is a wrapper for the Apache Commons validator in order
 * to allow a single interface to represent nearly all the validation services in 
 * this application. 
 * 
 * @author Warner Iveris
 */
public class URLValidatorServiceImpl implements ValidatorService<String>{

	/**
	 * Method returns true if the string is a valid URL construction and false
	 * if it is not.
	 */
	@Override
	public boolean isValid(String URL) {
		return new UrlValidator().isValid(URL);
	}
}
