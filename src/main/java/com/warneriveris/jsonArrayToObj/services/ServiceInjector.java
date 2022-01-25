package com.warneriveris.jsonArrayToObj.services;

/**
 * Generic service injector interface to allow various services to be
 * injected into a class with only one injector instance.
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
