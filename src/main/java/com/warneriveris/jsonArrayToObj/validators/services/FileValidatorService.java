package com.warneriveris.jsonArrayToObj.validators.services;

/**
 * Generic file validation interface that is used to verify file information. 
 * In the current implementation it is used to verify that the size of the file
 * listed in the JSON data matches the size of the file at the matching URL.
 * 
 * @author Warner Iveris
 *
 * @param <E>	expected value of a given attribute
 * @param <F>	the file or path to the file that needs to be match to the attribute
 */
public interface FileValidatorService<E, F> {
	boolean isValid(E expected, F file) throws Exception;
}
