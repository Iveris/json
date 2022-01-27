package com.warneriveris.jsonArrayToObj.validators.implementations;

import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

/**
 * Simple test to confirm that the integer passed to it could be a valid file 
 * size. For instance, a file size cannot be negative, and that the file size
 * does not exceed the range of the integer maximum value.
 * 
 * @author Warner Iveris
 *
 */

public class FileSizeValidatorServiceImpl implements ValidatorService<Integer> {

	@Override
	public boolean isValid(Integer filesize) {
		return (filesize > 0 && filesize < Integer.MAX_VALUE);
	}

}
