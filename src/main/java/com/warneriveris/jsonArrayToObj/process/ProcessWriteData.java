package com.warneriveris.jsonArrayToObj.process;

import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;
import com.warneriveris.jsonArrayToObj.services.write.WriteService;
import com.warneriveris.jsonArrayToObj.services.write.WriteServiceInjector;

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
