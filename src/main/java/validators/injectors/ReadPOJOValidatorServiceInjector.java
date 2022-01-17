package validators.injectors;

import read.ReadPOJO;
import validators.implementations.ReadPOJOValidatorImpl;
import validators.services.ValidatorService;

public class ReadPOJOValidatorServiceInjector implements ValidatorServiceInjector<ReadPOJO> {

	@Override
	public ValidatorService<ReadPOJO> getValidator() {
		ValidatorService<ReadPOJO> vs = new ReadPOJOValidatorImpl();
		return vs;
	}

}
