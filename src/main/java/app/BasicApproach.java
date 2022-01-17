package app;

import errors.ErrorReporter;
import process.ProcessReadData;
import process.ProcessWriteData;

public class BasicApproach {

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
	//currently set up to process non-concurrently
	public static void process(String input) {
		ProcessReadData prd = new ProcessReadData();
		prd.process(input);
		boolean complete = false;
		try {
			complete = prd.call();
		} catch (Exception e) {
			ErrorReporter.add("Runtime exception in read thread" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		if(complete) {
			ProcessWriteData pwd = new ProcessWriteData();
			pwd.setData(prd.getResults());
			pwd.process("output.json");
		}
	}
	public static void process(String input, String output) {
		System.out.println("processing: " + input + " into " + output);
	}
	
	public static void cleanUp() {
		ErrorReporter.printErrors();
	}
}
