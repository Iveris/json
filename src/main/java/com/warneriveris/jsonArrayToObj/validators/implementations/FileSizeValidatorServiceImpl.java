package com.warneriveris.jsonArrayToObj.validators.implementations;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;

import java.net.SocketTimeoutException;

import com.warneriveris.jsonArrayToObj.errors.ErrorReporter;
import com.warneriveris.jsonArrayToObj.validators.injectors.URLValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.services.FileValidatorService;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

/**
 * Returns true if and only if the file size passed as an integer matches the
 * file size of the file at the specified URL. This is done using a request for
 * header information in order to cut down on the amount of data that needs to
 * be transfered.
 * 
 * @author Warner Iveris
 * @param urlPath a string representing the URL to a remote file
 *
 */

public class FileSizeValidatorServiceImpl implements FileValidatorService<Integer, String> {

	private final int CONNECTION_TIMEOUT = 10000; // time in milliseconds
	private final int READ_TIMEOUT = 20000; // time in milliseconds
	private final ValidatorService<String> validate = new URLValidatorServiceInjector().getValidator();
	// the value returned by the getContentLengthLong method if the file size cannot
	// be determined
	private final int UNDETERMINED = -1;

	/**
	 * First the file size and URL string are tested to ensure they are valid constructions.
	 * Then, a connection to the file at the URL is established and an request
	 * for the content length is sent. The response to that request is matched
	 * against the file size and the result of that match is returned as a boolean.
	 * If the file size given in the input argument matches the file size returned
	 * from the URL connection, true is returned. Otherwise, if they do not match,
	 * or if and exception occurs, false is returned. If an exception does occur,
	 * the user is informed in an error message printed at the end of the program
	 * and the exception stack trace is recorded in the log file.
	 */
	public boolean isValid(Integer filesize, String urlPath) {
		if (filesize < 0) {
			return false;
		}
		if (!validate.isValid(urlPath)) {
			return false;
		}

		// placeholder value that is will not match a valid file size and is
		// distinct from the undetermined return value
		long size = -2L;
		try {
			URL url = new URL(urlPath);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			conn.setConnectTimeout(CONNECTION_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);
			conn.connect();
			
			size = conn.getContentLengthLong();

			conn.disconnect();
		} catch (SocketTimeoutException e) {
			String msg = "The size of the file at " + urlPath + " could not be "
					+ "validated due to long server response time.";
			reportError(msg, e);
			return false;
		} catch (MalformedURLException e) {
			String msg = "Error malformed URL: " + urlPath;
			reportError(msg, e);
			return false;
		} catch (ProtocolException e) {
			String msg = "Error setting up URL connection";
			reportError(msg, e);
			return false;
		} catch (IOException e) {
			String msg = "Error opening connection to " + urlPath;
			reportError(msg, e);
			return false;
		} catch (Exception e) {
			String msg = "Error validating URL or file size";
			reportError(msg, e);
			return false;
		}

		if (size == UNDETERMINED) {
			ErrorReporter.add("The size of the file at " + urlPath + " could not be determined.");
			return false;
		}

		return filesize == size;
	}
	
	/**
	 * This method saves a few lines of code for each potential exception that
	 * can occur, while allowing each error to be reported to the user and 
	 * recorded in the log file.
	 * 
	 * @param msg	a string containing a non-technical explanation of what went
	 * 				wrong in the normal processing of data. This will be printed for the user.
	 * @param e		an exception which was caught and used to record a stack trace
	 * 				to the log file for potential developer usage.
	 */
	private void reportError(String msg, Exception e) {
		ErrorReporter.add(msg);
		LogManager.getLogger().error(msg, e);
	}
}
