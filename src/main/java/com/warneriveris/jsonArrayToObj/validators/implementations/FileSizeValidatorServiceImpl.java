package com.warneriveris.jsonArrayToObj.validators.implementations;

import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

public class FileSizeValidatorServiceImpl implements ValidatorService<Integer> {

	@Override
	public boolean isValid(Integer filesize) {
		return (filesize > 0 && filesize < Integer.MAX_VALUE);
	}

}
