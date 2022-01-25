package com.warneriveris.jsonArrayToObj.logger;

/**
 * Records the number of JSON objects that have been successfully parsed by the
 * read service, as well as any objects that had to be skipped due to parsing
 * problems or by failing data validation tests.
 * 
 * @author Warner Iveris
 */

public class Summary {
	
	private static Summary summary = new Summary();
	
	// variable holding the number of JSON objects successfully read and parsed
	private static volatile int processed = 0;
	
	// variable holding the number of JSON objects that could note be read or validated
	private static volatile int skipped = 0;
	
	private Summary() {}
	
	public static Summary getInstance() {
		return summary;
	}
	
	/**
	 * Returns an integer representing the number of JSON objects successfully
	 * read and parsed by the read service classes.
	 * 
	 * @return	value of private processed variable
	 */
	public static int getProcessed() {
		return processed;
	}
	
	/**
	 * Returns an integer representing the number of JSON objects that had to be 
	 * skipped by the read and write service classes due to problems parsing or
	 * validating the data from the input file.
	 * 
	 * @return	value of private processed variable
	 */
	public static int getSkipped() {
		return skipped;
	}
	
	/**
	 * Increments the processed variable by one. Method is synchronized to ensure
	 * data integrity across multiple threads
	 */
	public static synchronized void addProcessed() {
		++processed;
	}
	
	/**
	 * Increments the skipped variable by one. Method is synchronized to ensure
	 * data integrity across multiple threads
	 */
	public static synchronized void addSkipped() {
		++skipped;
	}
	
	/**
	 * String method that records the values of the two class variables and 
	 * provides context for the user.
	 */
	@Override
	public String toString() {
		return "Processed: " + getProcessed() +"\tSkipped: " + getSkipped() + "\n";
	}
	
	/**
	 * Prints a summary of how many JSON objects were read or skipped to the 
	 * user at the end of the program.
	 */
	public static void printSummary() {
		System.out.printf("Processed: %d\tSkipped: %d%n", getProcessed(), getSkipped());
	}
}
