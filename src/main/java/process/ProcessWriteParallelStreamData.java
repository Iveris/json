package process;

import read.ReadPOJO;
import write.WriteService;
import write.WriteStreamServiceImpl;

public class ProcessWriteParallelStreamData implements ProcessData {
	
	private WriteService ws = new WriteStreamServiceImpl();
	
	public ProcessWriteParallelStreamData(String datapath) {
		ws.getWriter(datapath);
	}

	@Override
	public void execute() {
		Thread tWrite = new Thread(() -> ws.write(ReadPOJO.class));
		tWrite.start();
	}
	
	@Override
	public void run() {
		execute();
	}
}
