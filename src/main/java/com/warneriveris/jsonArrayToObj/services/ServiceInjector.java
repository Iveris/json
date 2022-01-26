package com.warneriveris.jsonArrayToObj.services;

/**
 * Generic service injector interface to allow various services to be
 * injected in a uniform manner so that one injector instance can be used
 * to inject multiple services into a class if need be.
 * 
 * @author Warner Iveris
 *
 * @param <K>	generic class type which is specified by Service Injector
 * 				implementations to signify the data type being used by the 
 * 				injector.
 */
public interface ServiceInjector<K> {
	K getService();
}
