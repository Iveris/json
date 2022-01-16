package validators.injectors;

import java.net.URL;

import validators.implementations.FileSizeValidatorServiceImpl;
import validators.services.FileValidatorService;

public class FileSizeValidatorServiceInjector implements FileValidatorServiceInjector<Integer, URL> {

	@Override
	public FileValidatorService<Integer, URL> getValidator() {
		return new FileSizeValidatorServiceImpl();
	}
}
