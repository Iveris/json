package com.warneriveris.jsonArrayToObj.logger;
public class Summary {
	
	private static Summary summary = new Summary();
	private static volatile int processed = 0;
	private static volatile int skipped = 0;
	
	private Summary() {}
	
	public static Summary getInstance() {
		return summary;
	}
	
	public static int getProcessed() {
		return processed;
	}
	public static int getSkipped() {
		return skipped;
	}
	public static synchronized void addProcessed() {
		++processed;
	}
	public static synchronized void addSkipped() {
		++skipped;
	}
	
	@Override
	public String toString() {
		return "Processed: " + getProcessed() +"\tSkipped: " + getSkipped() + "\n";
	}
	
	public static void printSummary() {
		System.out.printf("Processed: %d\tSkipped: %d%n", getProcessed(), getSkipped());
	}
}
