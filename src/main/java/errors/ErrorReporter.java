package errors;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ErrorReporter {
	private static ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
	private static ErrorReporter er;
	private ErrorReporter() {}
	
	public static ErrorReporter getInstance() {
		if(er == null) { er = new ErrorReporter(); }
		return er;
	}
	
	public static boolean add(String error) {
		getInstance(); //ensures instantiation
		return q.add(error);
	}
	
	public static void printErrors() {
		getInstance();
		if(!q.isEmpty()) {
			Iterator<String> iterator = q.iterator();
			
			System.out.println("*****\tERRORS\t*****");
			while(iterator.hasNext()) {
				System.out.println(iterator.next());
			}
		}
	}
}
