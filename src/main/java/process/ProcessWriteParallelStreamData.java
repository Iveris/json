package process;

import read.ReadPOJO;
import write.WriteService;
import write.WriteStreamServiceImpl;

public class ProcessWriteParallelStreamData implements ProcessData {
	
	private WriteService ws = new WriteStreamServiceImpl();
	
	@Override
	public void process(String datapath) {
		ws.getWriter(datapath);
	}

	@Override
	public void execute() {
		Thread tWrite = new Thread(() -> ws.write(ReadPOJO.class));
		tWrite.start();
	}
	
	@Override
	public Boolean call() throws Exception {
		execute();
		return true;
	}
}
