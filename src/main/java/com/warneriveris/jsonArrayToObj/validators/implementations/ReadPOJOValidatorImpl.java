package com.warneriveris.jsonArrayToObj.validators.implementations;

import org.apache.logging.log4j.LogManager;

import com.warneriveris.jsonArrayToObj.errors.ErrorReporter;
import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;
import com.warneriveris.jsonArrayToObj.validators.injectors.FileSizeValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.FileValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.PathValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.UniqueKeyValidatorInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.ValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.services.FileValidatorService;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

public class ReadPOJOValidatorImpl implements ValidatorService<ReadPOJO> {

	ValidatorService<String> validatePath;
	FileValidatorService<Integer, String> validateRemoteFileSize;
	ValidatorService<String> uniqueKey;
	ValidatorServiceInjector<String> injector;
	FileValidatorServiceInjector<Integer, String> finjector = new FileSizeValidatorServiceInjector();
	
	public ReadPOJOValidatorImpl() {
		injector = new PathValidatorServiceInjector();
		validatePath = injector.getValidator();
		injector = new UniqueKeyValidatorInjector<>();
		uniqueKey = injector.getValidator();
		injector = null;
		validateRemoteFileSize = finjector.getValidator();
	}
	
	@Override
	public boolean isValid(ReadPOJO pojo) {
		boolean result = true;
		if(pojo == null) {
			return false;
		}
		if(!validatePath.isValid(pojo.getPath())) {
			ErrorReporter.add("Entry skipped due to invalid path");
			result = false;
		}
		if(!uniqueKey.isValid(pojo.getPath())) {
			ErrorReporter.add("Entry skipped because there "
					+ "is already a path labelled " + pojo.getPath());
			result = false;
		}
		if(pojo.getSize() < 0 || pojo.getSize() > Integer.MAX_VALUE) {
			ErrorReporter.add("Entry skipped due to invalid size value");
			result = false;
		}
		try {
			if(!validateRemoteFileSize.isValid(pojo.getSize(), pojo.getUrl())) {
				result = false;
			}
		} catch (Exception e) {
			String msg = "Entry skipped due to error validating remote file URL "
					+ "or remote file size";
			ErrorReporter.add(msg);
			LogManager.getLogger().error(msg, e);
			result = false;
		}
		return result;
	}

}
