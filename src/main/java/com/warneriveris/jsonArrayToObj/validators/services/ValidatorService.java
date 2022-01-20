package com.warneriveris.jsonArrayToObj.validators.services;

public interface ValidatorService<T> {
	boolean isValid(T t);
}
