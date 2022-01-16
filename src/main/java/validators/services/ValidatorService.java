package validators.services;

public interface ValidatorService<T> {
	boolean isValid(T t);
}
