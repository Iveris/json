package process;

import logger.Summary;
import services.read.ReadPOJO;
import services.read.ReadService;
import services.read.ReadServiceInjector;
import validators.injectors.ReadPOJOValidatorServiceInjector;
import validators.services.ValidatorService;

public class ProcessReadData implements ProcessData {

	private ReadService rs;
	private ValidatorService<ReadPOJO> validatePOJO = 
			new ReadPOJOValidatorServiceInjector().getValidator();

	public ProcessReadData(String datapath) {
		rs = new ReadServiceInjector().getService();
		rs.getReader(datapath);
	}

	@Override
	public void execute() {
		while(rs.hasNext()) {
			ReadPOJO rp = (ReadPOJO) rs.next(ReadPOJO.class);
			if(validatePOJO.isValid(rp)) {
				ReadPOJOQueue.add(rp);
				Summary.addSuccess();
			} else {
				Summary.addSkipped();
			}
		}
	}

	@Override
	public void run() {
		execute();
		rs.closeReader();
	}
}
