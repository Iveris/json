package app;

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
		
	}
	
	public static void process(String input) {
		System.out.println("processing: " + input);
	}
	public static void process(String input, String output) {
		System.out.println("processing: " + input + " into " + output);
		System.out.println(System.getProperty("user.dir"));
		
	}
	
	public static void cleanUp() {
		
	}
}
