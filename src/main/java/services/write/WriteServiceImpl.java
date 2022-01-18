package services.write;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import errors.ErrorReporter;

public class WriteServiceImpl implements WriteService {

	private Gson gson = new GsonBuilder().create();
	private Writer writer;
	
	@Override
	public WriteService getWriter(String filename) {
		try {
			writer = new FileWriter(filename, true);
		} catch(IOException e) {
			String msg = "Unable to open/create output file for writer";
			ErrorReporter.add(msg + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public void write(Object data) {
		gson.toJson(data, writer);
		
	}

	@Override
	public void closeWriter() {
		try {
			writer.close();
		} catch (IOException e) {
			ErrorReporter.add("Error closing file writer" + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

}