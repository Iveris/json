package validators.implementations;

import java.util.regex.Pattern;

import validators.services.ValidatorService;

public class PathValidatorServiceImpl implements ValidatorService<String> {

	private String pattern = "[\\w\\d\\.\\_\\/]+";
	
	@Override
	public boolean isValid(String pathField) {
		
		return (pathField != null && 
				!pathField.isBlank() &&
				Pattern.matches(pattern, pathField));
	}

}
