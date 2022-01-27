package com.warneriveris.jsonArrayToObj.errors;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Provides a non-technical summary of errors encountered in the process of
 * running the application for the user. Uses a Concurrent Linked Queue so that
 * errors occurring in read or write services can both be recorded without loss.
 * The summary is printed to the console at the very end of the program if and
 * only if any errors are held in the queue.
 * 
 * @author Warner Iveris
 *
 */

public class ErrorReporter {
	// Queue to hold all error messages
	private static ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();

	private static ErrorReporter er;

	private ErrorReporter() {
	}

	public static ErrorReporter getInstance() {
		if (er == null) {
			er = new ErrorReporter();
		}
		return er;
	}

	/**
	 * Adds an error message to the queue. If the message is successfully added, the
	 * method returns true. If for any reason the message could not be added, then
	 * the method returns false.
	 * 
	 * @param error a string containing a message to be printed to the user about
	 *              the kind of error that occurred.
	 * @return boolean signals that the error has been successfully added.
	 */
	public static boolean add(String error) {
		return q.add(error);
	}

	/**
	 * Prints a user-readable summary of any errors encountered in the course of
	 * processing the data provided by the user.
	 */
	public static void printErrors() {
		if (!q.isEmpty()) {
			Iterator<String> iterator = q.iterator();

			System.out.println("*****\tERRORS\t*****");
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}
		}
	}
}
