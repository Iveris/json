package read;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import errors.ErrorReporter;

public class ReadLocalFileServiceImpl implements ReadService {

	private InputStream fis;
	private JsonReader jread;
	private Gson gson = new GsonBuilder().create();
	
	@Override
	public ReadService getReader(String filename){
		File file = new File(filename);
		if(!file.exists()) {
			String msg = "File cannot be found\n";
			ErrorReporter.add(msg);
			System.exit(1);
		}
		try {
			fis = new FileInputStream(file);
			jread = new JsonReader(new InputStreamReader(fis, StandardCharsets.UTF_8));			
			jread.beginArray();
		} catch(IOException e) {
			String msg = "Error loading file into JsonReader\n";
			ErrorReporter.add(msg + e.getLocalizedMessage());
		}
		return this;
	}

	@Override
	public boolean hasNext() {
		try {
			return jread.hasNext();
		} catch (IOException e) {
			String msg = "Error reading file\n";
			ErrorReporter.add(msg + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Object next(Object type) {
		return gson.fromJson(jread, (java.lang.reflect.Type) type);
	}

	@Override
	public void closeReader() {
		try {
			jread.close();
			fis.close();
		} catch (IOException e) {
			String msg = "Error closing file reader\n";
			ErrorReporter.add(msg + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}
