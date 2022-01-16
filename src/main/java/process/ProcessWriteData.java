package process;

import write.WriteObj;
import write.WriteService;
import write.WriteServiceImpl;

public class ProcessWriteData implements ProcessData{

	private WriteService<String, WriteObj> ws = new WriteServiceImpl<>();
	
	@Override
	public void process(String datapath) {
		if(datapath != null && !datapath.isBlank()) {
			ws.getWriter(datapath);
		} else {
			ws.getWriter("output.json");
		}
			
	}

	@Override
	public void execute() {
		
		
	}

}
