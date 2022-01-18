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

	public ProcessReadParallelStreamData(String datapath) {
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
	public void run() {
		execute();
		rs.closeReader();
	}
}
