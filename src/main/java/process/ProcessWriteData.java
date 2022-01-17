package process;

import java.util.Map;

import write.WriteObj;
import write.WriteService;
import write.WriteServiceImpl;

public class ProcessWriteData implements ProcessData{

	private WriteService ws = new WriteServiceImpl();
	private Map<String, WriteObj> data;
	
	@Override
	public void process(String datatarget) {
		if(datatarget != null && !datatarget.isBlank()) {
			ws.getWriter(datatarget);
		} else {
			ws.getWriter("output.json");
		}
		data = getData();
		execute();
	}

	@Override
	public void execute() {
		ws.write(data);
		ws.closeWriter();
	}
	
	public Map<String, WriteObj> getData() {
		return data;
	}
	public void setData(Map<String, WriteObj> readResults) {
		data = readResults;
	}

	@Override
	public Boolean call() throws Exception {
		return true;
	}

	@Override
	public void readComplete(boolean complete) {
		//used in concurrent classes 
	}

}
