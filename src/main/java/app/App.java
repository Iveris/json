package app;

import errors.ErrorReporter;
import logger.Summary;
import process.ProcessData;
import process.ReadPOJOQueue;
import process.injectors.ProcessParallelReadInjector;
import process.injectors.ProcessParallelWriteInjector;

/**
 * 
 * @author Warner Iveris
 * Command line program to process json files
 */

public class App {

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
		cleanUp();
	}
	
	public static void process(String input) {
		ProcessData processReadData = new ProcessParallelReadInjector().getService(input);
		ProcessData processWriteData = new ProcessParallelWriteInjector().getService("output.json");
		
		Thread read = new Thread(processReadData);
		Thread write = new Thread(processWriteData);
		ReadPOJOQueue.getInstance();
		read.start();
		write.start();
		
	}
	public static void process(String input, String output) {
	}
	
	public static void cleanUp() {
		Summary.printSummary();
		ErrorReporter.printErrors();
	}
}
