package validators.injectors;

import validators.implementations.PathValidatorServiceImpl;
import validators.services.ValidatorService;

public class PathValidatorServiceInjector implements ValidatorServiceInjector<String> {

	@Override
	public ValidatorService<String> getValidator() {
		return new PathValidatorServiceImpl();
	}

}
