package process;

import logger.Summary;
import read.ReadPOJO;
import read.ReadParallelStreamLocalFileService;
import read.ReadService;
import validators.injectors.ReadPOJOValidatorServiceInjector;
import validators.services.ValidatorService;

public class ProcessReadParallelStreamData implements ProcessData {

	private ReadService rs;
	private ValidatorService<ReadPOJO> validatePOJO = 
			new ReadPOJOValidatorServiceInjector().getValidator();

	@Override
	public void process(String datapath) {
		rs = new ReadParallelStreamLocalFileService();
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
	public Boolean call() throws Exception {
		execute();
		rs.closeReader();
		return true;
	}

	@Override
	public void readComplete(boolean complete) {
		//used in concurrent write classes 
	}
}
