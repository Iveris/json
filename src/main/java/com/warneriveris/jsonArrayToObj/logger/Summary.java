package com.warneriveris.jsonArrayToObj.logger;
//TODO test this class
public class Summary {
	
	private static Summary summary;
	private static int success = 0;
	private static int skipped = 0;
	
	private Summary() {}
	
	public static Summary getInstance() {
		if(summary == null) { 
			summary = new Summary(); 
		}
		return summary;
	}
	
	public static int getSuccess() {
		return success;
	}
	public static int getSkipped() {
		return skipped;
	}
	public static void addSuccess() {
		++success;
	}
	public static void addSkipped() {
		++skipped;
	}
	
	@Override
	public String toString() {
		return "Processed: " + getSuccess() +"\tSkipped: " + getSkipped() + "\n";
	}
	
	public static void printSummary() {
		System.out.printf("Processed: %d\tSkipped: %d%n", getSuccess(), getSkipped());
	}
}
