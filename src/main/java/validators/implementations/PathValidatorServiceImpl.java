package validators.implementations;

import validators.services.ValidatorService;

public class PathValidatorServiceImpl implements ValidatorService<String> {

	@Override
	public boolean isValid(String pathField) {
		return (pathField != null && !pathField.isBlank());
	}

}
