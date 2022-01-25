package com.warneriveris.jsonArrayToObj.services.read;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;

import com.google.gson.stream.JsonReader;
import com.warneriveris.jsonArrayToObj.errors.ErrorReporter;
import com.warneriveris.jsonArrayToObj.process.ReadPOJOQueue;
import com.warneriveris.jsonArrayToObj.validators.injectors.URLValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

/**
 * 
 * @author Warner Iveris
 *
 * Uses JsonReader class from the Gson library to parse an array of JSON objects
 * from an input file that is passed to the program by the user. These objects
 * are then returned individually to the method caller.
 */

public class ReadStreamServiceImpl implements ReadService {

	
	private JsonReader reader;
	
	/**
	 * Validates URL expressions using the Apache Commons routines validator.
	 */
	private ValidatorService<String> isURL = new URLValidatorServiceInjector().getValidator();
	
	
	/**
	 * Initializes reader with file name and path provided by the user. A URL
	 * validator is used to determine if the file path is to a local file or to
	 * a remote one. Once that is determined the appropriate stream is created to
	 * read the file. Because the files contain an array of objects, the opening
	 * array bracket [ must be accounted for, or there will be a read error.
	 * 
	 * @param filename	string containing the path and name of the file to be read
	 */
	@Override
	public ReadService getReader(String filename) {
		if(isURL.isValid(filename)) {
			createURLStream(filename);
		} else {
			createFileStream(filename);
		}
		try {
			reader.beginArray(); // parses the [ at the beginning of the array
		} catch (IOException e) {
			readFileError(e);
		}
		return this;
	}


	/**
	 * Creates a JsonReader for reading the input file. Begins by creating a 
	 * FileInputStream to a local file, then uses that stream to create an 
	 * InputStreamReader which is consumed in the creation of the JsonReader object.
	 * Returns the initialized JsonReader.
	 * 
	 * @param filename	string containing the path and name of the file to be read
	 */
	private void createFileStream(String filename) {
		FileInputStream inputFile = null;
		try { inputFile = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			openFileError(e);
			System.exit(1);
		}
		reader = new JsonReader(new InputStreamReader(inputFile, StandardCharsets.UTF_8));
	}

	/**
	 * Creates a JsonReader for reading the remote input file. Begins by creating 
	 * an InputStream to a file located at the URL string passed by the user. Then 
	 * uses that stream to create an InputStreamReader which is consumed in the 
	 * creation of the JsonReader object. Returns initialized JsonReader.
	 * 
	 * @param url	string containing the path and name of the remote file to be read
	 */
	private void createURLStream(String url) {
		InputStream in = null;
		try {
			in = new URL(url).openStream();
		} catch (MalformedURLException e) {
			openFileError(e);
		} catch (IOException e) {
			openFileError(e);
		}
		reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));
	}


	/**
	 * Determines if there are more JSON objects to read from the input file and
	 * returns true if there are more objects left to read, and false if the end
	 * of the file has been reached.
	 */
	@Override
	public boolean hasNext() {
		boolean more = false;
		try {
			more = reader.hasNext();
		} catch (IOException e) {
			readFileError(e);
		}
		return more;
	}

	/**
	 * Reads the next JSON object in the input file and records the values of each
	 * field in a POJO. This POJO is then returned to the caller.
	 */
	@Override
	public ReadPOJO next() {
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
			readFileError(e);
			closeReader();
		}
		return new ReadPOJO(URL, path, size);
	}

	/**
	 * Before closing the reader, the end of the array ] must be accounted for.
	 * Once the reader is closed, a boolean value is set to false in the shared
	 * ReadPOJOQueue object that signals no more read data will be entered.
	 */
	@Override
	public void closeReader() {
		try {
			reader.endArray();
			reader.close();
			ReadPOJOQueue.setIsReceivingInput(false);
		} catch (IOException e) {
			String msg = "Error closing input file reader";
			ErrorReporter.add(msg);
			LogManager.getLogger().error(msg, e);
			System.exit(1);
		} catch (IllegalStateException e) {
			String msg = "Error closing input file reader";
			ErrorReporter.add(msg);
			LogManager.getLogger().error(msg, e);
			System.exit(1);
		}
	}
	
	/**
	 * Simplifies reporting common read file errors to the user and recording 
	 * the stack trace information to a log for developers.
	 * 
	 * @param e	an exception used to provide a stack trace for the log file
	 */
	private void readFileError(Exception e) {
		String msg = "Error reading file";
		ErrorReporter.add(msg);
		LogManager.getLogger().error(msg, e);
	}
	
	/**
	 * Simplifies reporting common read file errors to the user and recording 
	 * the stack trace information to a log for developers.
	 * 
	 * @param e	an exception used to provide a stack trace for the log file
	 */
	private void openFileError(Exception e) {
		String msg = "Error opening input source";
		ErrorReporter.add(msg);
		LogManager.getLogger().error(msg, e);
	}
}
