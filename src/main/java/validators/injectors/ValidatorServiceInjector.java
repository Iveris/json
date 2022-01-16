package validators.injectors;

import validators.services.ValidatorService;

public interface ValidatorServiceInjector<T> {

	ValidatorService<T> getValidator();
}
