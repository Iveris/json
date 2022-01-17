package process;

import read.ReadLocalFileServiceImpl;
import read.ReadPOJO;
import read.ReadService;
import read.ReadURLFileServiceImpl;
import validators.implementations.ReadPOJOValidatorImpl;
import validators.injectors.URLValidatorServiceInjector;
import validators.injectors.ValidatorServiceInjector;
import validators.services.ValidatorService;

public class ProcessConcurrentReadData implements ProcessData {

	private ReadService rs;
	private boolean hasFinished = false;
	
	@Override
	public void process(String dataSource) {
		ValidatorServiceInjector<String> injector = 
				new URLValidatorServiceInjector();
		ValidatorService<String> validator = injector.getValidator();
		if(validator.isValid(dataSource)) {
			rs = new ReadURLFileServiceImpl();
		} else {
			rs = new ReadLocalFileServiceImpl();
		}
	}

	@Override
	public void execute() {
		ReadPOJO rPojo;
		ValidatorService<ReadPOJO> readPOJOValidator = new ReadPOJOValidatorImpl();
		while(rs.hasNext()) {
			rPojo = (ReadPOJO) rs.next(ReadPOJO.class);
			if(readPOJOValidator.isValid(rPojo)) {
				ReadPOJOQueue.add(rPojo);
			}
		}
		hasFinished = true;
	}
	
	public boolean getHasFinished() {
		return hasFinished;
	}

	@Override
	public Boolean call() throws Exception {
		return true;
	}

}
