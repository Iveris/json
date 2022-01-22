package com.warneriveris.jsonArrayToObj.validators.implementations;


import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

/**
 * 
 * @author warner
 *
 * @param <T> for this program T will be a String, but it's possible
 * 			that this could be an Integer or other data type in the 
 * 			future, so class is generic.
 */

public class UniqueKeyValidatorImpl<T> implements ValidatorService<T> {

	private ConcurrentHashMap<T, Integer> map = new ConcurrentHashMap<>();
	private Set<T> keys = map.newKeySet(1);
	
	@Override
	//returns true if key value has not been already read and passed to
	//the ReadQueue
	public boolean isValid(T t) {
		boolean contains = keys.add(t);
		return contains;
	}

}
