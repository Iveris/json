package com.warneriveris.jsonArrayToObj.app;

import com.warneriveris.jsonArrayToObj.errors.ErrorReporter;
import com.warneriveris.jsonArrayToObj.logger.Summary;
import com.warneriveris.jsonArrayToObj.process.ProcessData;
import com.warneriveris.jsonArrayToObj.process.ReadPOJOQueue;
import com.warneriveris.jsonArrayToObj.process.injectors.ProcessReadServiceInjector;
import com.warneriveris.jsonArrayToObj.process.injectors.ProcessWriteServiceInjector;

/**
 * 
 * @author Warner Iveris
 * Command line program to process json files
 */

//TODO fix summary printing problem
public class App {

	private static final String DEFAULT_OUTPUT = "output.json";
	public static void main(String...args) {
		if(args.length == 0) {
			System.out.println("Must provide an input file or url");
			System.exit(1);
		}
		
		String inputFile = args[0];
		String outputFile;
		if(args.length > 1) {
			outputFile = args[1];
			process(inputFile, outputFile);
		} else {
			process(inputFile);
		}
	}
	
	public static void process(String input) {
		process(input, DEFAULT_OUTPUT);
	}
	public static void process(String input, String output) {
		
		ProcessData processReadData = new ProcessReadServiceInjector().getService(input);
		ProcessData processWriteData = new ProcessWriteServiceInjector().getService(output);
		
		Thread read = new Thread(processReadData);
		Thread write = new Thread(processWriteData);
		ReadPOJOQueue.getInstance();
		read.start();
		write.start();
		
		Summary.printSummary();
		ErrorReporter.printErrors();
	}
}
