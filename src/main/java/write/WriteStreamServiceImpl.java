package write;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import com.google.gson.stream.JsonWriter;

import errors.ErrorReporter;
import process.ReadPOJOQueue;
import read.ReadPOJO;
/**
 * 
 * @author Warner Iveris
 * This is a parallel stream implementation where the read stream
 * writes to the ReadPOJOQueue and the write stream shares that
 * object to grab entries. 
 */
public class WriteStreamServiceImpl implements WriteService {
	
	private JsonWriter writer;
	private volatile boolean readHasFinished = false;
	
	@Override
	public WriteService getWriter(String filename) {
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
//		writer.setIndent("    "); //makes the output pretty
		try {
			writer.beginObject();
		} catch (IOException e) {
			ErrorReporter.add("Error writing to file\n" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return this;
	}

	@Override
	//data object could be used to create custom write streams with some reworking in the future
	public void write(Object data) {
		//write to file while read is writing to ReadPOJOQueue
		while(!readHasFinished) {
			writeEntries();
		}
		//finish writing any remaining entries
		if(ReadPOJOQueue.getSize() > 0) {
			writeEntries();
		}
			closeWriter();
	}
	
	private void writeEntries() {
		while(ReadPOJOQueue.getSize() > 0) {
			ReadPOJO rpojo = ReadPOJOQueue.remove();
			try {
				writer.name(rpojo.getPath());
				writer.beginObject();
				writer.name("url").value(rpojo.getUrl());
				writer.name("size").value(rpojo.getSize());
				writer.endObject();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	@Override
	public void readComplete(boolean complete) {
		readHasFinished = complete;
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
