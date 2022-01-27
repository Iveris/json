package com.warneriveris.jsonArrayToObj.validators.implementations;

import java.util.regex.Pattern;

import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

/**
 * Simple validator that uses a regular expression to confirm that only valid
 * path characters are contained in the string passed to it.
 * 
 * @author Warner Iveris
 *
 */

public class PathValidatorServiceImpl implements ValidatorService<String> {

	private String pattern = "[\\w\\d\\.\\_\\/]+";
	
	@Override
	public boolean isValid(String pathField) {
		
		return (pathField != null && 
				!pathField.isBlank() &&
				Pattern.matches(pattern, pathField));
	}

}
