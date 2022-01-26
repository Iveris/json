package com.warneriveris.jsonArrayToObj.validators.services;

/**
 * Interface that defines the expected behavior of every validation service in 
 * this application that only requires a single argument.
 * 
 * @author Warner Iveris
 *
 * @param <T>	generally T will be a string, but other types can be implemented
 * 				should the need arise.
 */
public interface ValidatorService<T> {
	boolean isValid(T t);
}
