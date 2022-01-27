package com.warneriveris.jsonArrayToObj.validators.implementations;

import com.warneriveris.jsonArrayToObj.errors.ErrorReporter;
import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;
import com.warneriveris.jsonArrayToObj.validators.injectors.FileSizeValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.PathValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.URLValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.UniqueKeyValidatorInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.ValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;
/**
 * Validates the data entry read from the input file and determines if that entry
 * should be skipped, or sent to the ReadPOJOQueue for write services to process.
 * 
 * @author Warner Iveris
 *
 */
public class ReadPOJOValidatorImpl implements ValidatorService<ReadPOJO> {

	ValidatorService<String> validatePath;
	ValidatorService<String> uniqueKey;
	ValidatorService<String> validateURL;
	ValidatorServiceInjector<String> injector;

	ValidatorService<Integer> validateFileSize;
	
	public ReadPOJOValidatorImpl() {
		injector = new PathValidatorServiceInjector();
		validatePath = injector.getValidator();
		injector = new UniqueKeyValidatorInjector<>();
		uniqueKey = injector.getValidator();
		injector = new URLValidatorServiceInjector();
		validateURL = injector.getValidator();
		injector = null;
		
		// validateFileSize uses an Integer type instead of a String type, so it
		// cannot use the injector declared above to get its validator.
		validateFileSize = new FileSizeValidatorServiceInjector().getValidator();
	}

	/**
	 * Validates the value of every field in the POJO object and immediately
	 * returns false if any value fails. By immediately returning false, the
	 * application gains a small amount of efficiency by not having to run the
	 * other validations since any failure will result in the entire entry 
	 * being skipped. Also, the more operationally expensive validations, such 
	 * as the uniqueKey validation which entails a read and write operation to a 
	 * hash map, are put at the end of the validation chain to save time on entries
	 * that fail the less expensive validation tests.
	 * 
	 * @param pojo	a ReadPOJO object to be validated
	 */
	@Override
	public boolean isValid(ReadPOJO pojo) {
		boolean result = true;
		if (pojo == null) {
			return false;
		}
		if (!validateFileSize.isValid(pojo.getSize())) {
			ErrorReporter.add("Entry skipped due to invalid size value");
			return false;
		}
		if (!validateURL.isValid(pojo.getUrl())) {
			return false;
		}
		if (!validatePath.isValid(pojo.getPath())) {
			ErrorReporter.add("Entry skipped due to invalid path");
			return false;
		}
		if (!uniqueKey.isValid(pojo.getPath())) {
			ErrorReporter.add("Entry skipped because there " + "is already a path labelled " + pojo.getPath());
			return false;
		}
		return result;
	}

}
