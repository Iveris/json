package process;

import services.read.ReadPOJO;
import services.write.WriteService;
import services.write.WriteServiceInjector;

public class ProcessWriteData implements ProcessData {
	
	private WriteService ws = new WriteServiceInjector().getService();
	
	public ProcessWriteData(String datapath) {
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
