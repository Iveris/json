package com.warneriveris.jsonArrayToObj.services.write;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;

import com.google.gson.stream.JsonWriter;
import com.warneriveris.jsonArrayToObj.errors.ErrorReporter;
import com.warneriveris.jsonArrayToObj.logger.Summary;
import com.warneriveris.jsonArrayToObj.process.ReadPOJOQueue;
import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;

/**
 * Uses JsonWriter from the Gson stream library to create a file containing a
 * single JSON object that contains path variables as fields with corresponding
 * URL and size values.
 * 
 * @author Warner Iveris
 * 
 */
public class WriteStreamServiceImpl implements WriteService {

	private JsonWriter writer;

	/**
	 * @param indent a string containing spaces that is used in formating the
	 *               distance between inner and outer values, brackets, etc.
	 */
	private String indent = "    ";

	/**
	 * Initializes writer. Creates a file stream based on user input or the default
	 * value "output.json." This file stream is then used to create an
	 * OutputStreamWriter which is consumed by the JsonWriter instance. Before
	 * returning itself to the caller, it writes the opening brackets for the JSON
	 * file being created.
	 * 
	 * @param filename a string with a filename and potentially a path for a desired
	 *                 output location.
	 */
	@Override
	public WriteService getWriter(String filename) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename, true);
		} catch (FileNotFoundException e) {
			String msg = "Unable to access/create write file";
			ErrorReporter.add(msg);
			LogManager.getLogger().error(msg, e);
			System.exit(1);
		}
		writer = new JsonWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
		writer.setIndent(indent); // makes the output pretty
		try {
			writer.beginObject(); // creates the opening { for the file
		} catch (IOException e) {
			writeFileError(e);
		}
		return this;
	}

	/**
	 * Determines when the writer can stop writing and close. The read service sets
	 * a boolean value in the ReadPOJOQueue object to false once it has completed
	 * reading the input file. This boolean value, along with the number of entries
	 * in ReadPOJOQueue determines when the writer will close;
	 */
	@Override
	public void write() {
		// writes to file while read service fills the ReadPOJOQueue
		while (ReadPOJOQueue.getIsReceivingInput() || ReadPOJOQueue.getSize() > 0) {
			writeEntries();
		}
		closeWriter();
	}

	/**
	 * Writes a single data entry from ReadPOJOQueue to the output file while the
	 * read service writes data to ReadPOJOQueue. Because ReadPOJOQueue is
	 * implemented with a ConcurrentLinkedQueue, read and write operations can
	 * happen simultaneously without blocking and still maintaining data integrity.
	 */
	private void writeEntries() {
		while (ReadPOJOQueue.getSize() > 0) {
			ReadPOJO rpojo = ReadPOJOQueue.remove();
			try {
				writer.name(rpojo.getPath());
				writer.beginObject();
				writer.name("url").value(rpojo.getUrl());
				writer.name("size").value(rpojo.getSize());
				writer.endObject();
			} catch (IOException e) {
				writeFileError(e);
			}
		}
	}

	/**
	 * Several important aspects of the program are set in motion just before
	 * closing the writer. Out of necessity, the Summary and ErrorReporter classes
	 * have to be called while in the thread that runs the write service.
	 * 
	 * The final write is the closing bracket for the JSON object, without which the
	 * file would not contain valid JSON.
	 */
	@Override
	public void closeWriter() {
		try {
			Summary.printSummary();
			ErrorReporter.printErrors();
			writer.endObject(); // creates the closing } for the file
			writer.close();
		} catch (IOException e) {
			String msg = "Error closing file";
			ErrorReporter.add(msg);
			LogManager.getLogger().error(msg, e);
			System.exit(1);
		}
	}

	/**
	 * Simplifies reporting common write exception errors to user while also
	 * recording the error's stack trace in the log file for use by developers.
	 * 
	 * @param e an exception throwable used to provide a stack trace for developers
	 * 
	 */
	private void writeFileError(Exception e) {
		String msg = "Error writing to file";
		ErrorReporter.add(msg);
		LogManager.getLogger().error(msg, e);
		closeWriter();
	}

}
