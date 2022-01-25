package com.warneriveris.jsonArrayToObj.app;

import com.warneriveris.jsonArrayToObj.process.ProcessData;
import com.warneriveris.jsonArrayToObj.process.injectors.ProcessReadServiceInjector;
import com.warneriveris.jsonArrayToObj.process.injectors.ProcessWriteServiceInjector;

/**
 * 
 * @author Warner Iveris
 * 
 *         Command line program to process an input file containing an array of
 *         JSON objects into an output file containing a single JSON object that
 *         contains all of the key value pairs in the input file, but organized
 *         by path field values.
 */

public class App {

	/**
	 * Default file for output in the case that one is not specified by the user.
	 */
	private static final String DEFAULT_OUTPUT = "output.json";

	/**
	 * Initializes application from user input. The user passes the program an input
	 * path as a string and optionally passes an output path as well. If no input
	 * file is passed by the user, the program prints instructions.
	 * 
	 * @param args array of strings containing a path to the input file and
	 *             optionally a path to an output file.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Must provide an input file or url path");
			System.exit(1);
		}

		String inputFile = args[0];
		String outputFile;
		if (args.length > 1) {
			outputFile = args[1];
			process(inputFile, outputFile);
		} else {
			process(inputFile);
		}
	}

	/**
	 * If a single argument is passed by the user, then the default output argument
	 * is used
	 * 
	 * @param input a string provided by the user for the program to find the input
	 *              source.
	 */

	public static void process(String input) {
		process(input, DEFAULT_OUTPUT);
	}

	/**
	 * Initializes the read and write services that will process the data into two
	 * separate threads which will run simultaneously.
	 * 
	 * @param input  a string provided by the user for the program to find the input
	 *               source.
	 * @param output a string provided by the user or by the program for the
	 *               application output.
	 */
	public static void process(String input, String output) {
		ProcessData processReadData = new ProcessReadServiceInjector().getService(input);
		ProcessData processWriteData = new ProcessWriteServiceInjector().getService(output);

		Thread read = new Thread(processReadData);
		Thread write = new Thread(processWriteData);
		read.start();
		write.start();
	}
}
