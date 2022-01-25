package com.warneriveris.jsonArrayToObj.validators.implementations;


import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

/**
 * The process of reading JSON objects in an array and converting them into a
 * JSON object in which the value of the path variable is used as a key to 
 * access the other values, allows for the possibility of the same key being
 * used multiple times. This class ensures that the resulting output contains
 * only unique keys. Should this service not be needed, the only change to the
 * code would be to set the isValid method to always return true.
 * 
 * @author Warner Iveris
 *
 * @param <T> for this program T will be a String, but it's possible
 * 			that this could be an Integer or other data type in the 
 * 			future, so class is generic.
 */

public class UniqueKeyValidatorImpl<T> implements ValidatorService<T> {

	/* map to hold every key processed and determine if potential keys are 
	 * unique or not. This map is not used directly, but serves as the 
	 * underlying data structure for a concurrent set data structure. At the
	 * time of this writing, no such data structure exists in Java, so this is
	 * the common work around.
	 */
	private ConcurrentHashMap<T, Integer> map = new ConcurrentHashMap<>();
	private Set<T> keys = map.newKeySet(1);
	
	@Override
	/**
	 * returns true if key value has not been already read and passed to 
	 * the read queue, otherwise returns false.
	 */
	public boolean isValid(T t) {
		boolean contains = keys.add(t);
		return contains;
	}

}
