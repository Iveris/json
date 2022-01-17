package read;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.google.gson.stream.JsonReader;

import errors.ErrorReporter;
import process.ReadPOJOQueue;

public class ReadParallelStreamLocalFileService implements ReadService {

	private JsonReader reader;
	
	@Override
	public ReadService getReader(String filename) {
		FileInputStream inputFile = null;
		try {
			inputFile = new FileInputStream(filename);
			reader = new JsonReader(new InputStreamReader(inputFile, StandardCharsets.UTF_8));
			reader.beginArray();
		} catch (FileNotFoundException e) {
			ErrorReporter.add("Input file not found" + e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			ErrorReporter.add("Error reading input file" + e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(1);
		}
		return this;
	}


	@Override
	public boolean hasNext() {
		boolean more = false;
		try {
			more = reader.hasNext();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return more;
	}

	@Override
	public Object next(Object type) {
		String URL = null;
		String path = null;
		int size = 0;
		try {
			reader.beginObject();
			while(reader.hasNext()) {
				String field = reader.nextName().toLowerCase();
				switch(field) 
				{
				case "url":
					URL = reader.nextString();
					break;
				case "path":
					path = reader.nextString();
					break;
				case "size":
					size = reader.nextInt();
					break;
				default:
					reader.skipValue();
				}
			}
			reader.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ReadPOJO(URL, path, size);
	}

	@Override
	public void closeReader() {
		try {
			reader.endArray();
			reader.close();
			ReadPOJOQueue.setIsReceivingInput(false);
		} catch (IOException e) {
			ErrorReporter.add("Error closing input file reader" + e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}
