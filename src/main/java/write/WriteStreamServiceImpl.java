package write;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import errors.ErrorReporter;

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
		// TODO Auto-generated method stub
		
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
