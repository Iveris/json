package write;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import errors.ErrorReporter;
import process.ReadPOJOQueue;
import read.ReadPOJO;

public class WriteStreamServiceImpl implements WriteService<String, WriteObj> {
	
	private Gson gson = new GsonBuilder().create();
	private JsonWriter writer;
	
	@Override
	public WriteService<String, WriteObj> getWriter(String filename) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename, true);
		} catch (FileNotFoundException e) {
			ErrorReporter.add("Unable to access/create write file" + 
								e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(1);
		}
		writer = new JsonWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
		try {
			writer.beginObject();
		} catch (IOException e) {
			ErrorReporter.add("Error writing to file\n" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public void write(Object data) {
		boolean readFinished = (Boolean) data;
		Map<String, WriteObj> map = new HashMap<>();
		while(ReadPOJOQueue.getSize() > 0) {
			ReadPOJO rpojo = ReadPOJOQueue.remove();
			map.put(rpojo.getPath(), new WriteObj(rpojo.getUrl(), rpojo.getSize()));
		}
		gson.toJson(map);
		if(readFinished) {
			closeWriter();
		}
	}

	@Override
	public void closeWriter() {
		try {
			writer.endObject();
			writer.close();
		} catch (IOException e) {
			ErrorReporter.add("Error closing file writer\n" + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

}
