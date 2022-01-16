package validators.implementations;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import validators.services.FileValidatorService;

/**
 * Returns true if and only if the file size passed as an integer
 * matches the file size of the file at the specified URL
 * 
 * @author 			Warner Iveris
 * @param url 		Assumes a valid url
 *
 */

public class FileSizeValidatorServiceImpl implements FileValidatorService<Integer, URL>{

	public boolean isValid(Integer filesize, URL url) throws IOException {
		//Opens a connection to the file at the URL passed as an argument
		//Uses header metadata to confirm that the size of the file
		//at the URL matches the file size passed to isValid method.
		HttpURLConnection conn = 
				(HttpURLConnection) url.openConnection();
		conn.setRequestMethod("HEAD");
		long size = conn.getContentLengthLong();
		conn.disconnect();
		return filesize ==  size;
	}

}
