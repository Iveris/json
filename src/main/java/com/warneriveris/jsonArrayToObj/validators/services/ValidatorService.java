package com.warneriveris.jsonArrayToObj.validators.services;

/**
 * Generic interface that defines the expected behavior of more specific
 * validation service interfaces in this application.
 * 
 * @author Warner Iveris
 *
 * @param <T> the object type that will be validated by the implemented
 *            validation service.
 */
public interface ValidatorService<T> {
	boolean isValid(T t);
}
