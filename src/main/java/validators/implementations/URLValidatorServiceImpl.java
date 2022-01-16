package validators.implementations;

import org.apache.commons.validator.routines.UrlValidator;

import validators.services.ValidatorService;

public class URLValidatorServiceImpl implements ValidatorService<String>{

	@Override
	public boolean isValid(String URL) {
		return new UrlValidator().isValid(URL);
	}
}
