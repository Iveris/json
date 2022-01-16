package validators.injectors;

import validators.implementations.URLValidatorServiceImpl;
import validators.services.ValidatorService;

public class URLValidatorServiceInjector implements ValidatorServiceInjector<String> {

	@Override
	public ValidatorService<String> getValidator() {
		return new URLValidatorServiceImpl();
	}

}
