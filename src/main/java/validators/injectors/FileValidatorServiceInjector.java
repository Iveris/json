package validators.injectors;

import validators.services.FileValidatorService;

public interface FileValidatorServiceInjector<A, F> {

	FileValidatorService<A, F> getValidator();
}
