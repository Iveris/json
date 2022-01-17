package logger;

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
		success++;
	}
//	public static void addSuccess(int num) {
//		success += num;
//	}
	
	public static void addSkipped() {
		skipped++;
	}
//	public static void addSkipped(int num) {
//		skipped += num;
//	}
	
	@Override
	public String toString() {
		return "Processed: " + getSuccess() +"\tSkipped: " + getSkipped() + "\n";
	}
	
	public static void printSummary() {
		System.out.printf("Processed: %d\tSkipped: %d%n", getSuccess(), getSkipped());
	}
}
