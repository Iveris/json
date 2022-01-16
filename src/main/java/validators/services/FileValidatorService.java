package validators.services;

public interface FileValidatorService<A, F> {
	boolean isValid(A attribute, F file) throws Exception;
}
