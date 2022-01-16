package read;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import errors.ErrorReporter;

/**
 * This class wraps around the ReadLocalFileSerivceImpl.class.
 * It downloads the file at the given URL and then passes it to
 * an instance of the ReadLocalFileServiceImpl.class.
 * 
 * @author Warner Iveris
 *
 */

public class ReadURLFileServiceImpl implements ReadService{
	
	private int CONNECTION_TIMEOUT = 9000;
	private int READ_TIMEOUT = 9000;
	private String tempFileName = "tempdata.txt";
	private File tempData = new File(tempFileName);
	private ReadService rs = new ReadLocalFileServiceImpl();

	@Override
	public ReadService getReader(String filename) {
		URL url;
		try {
			url = new URL(filename);
			FileUtils.copyURLToFile(url, tempData, CONNECTION_TIMEOUT, READ_TIMEOUT);
			rs.getReader(tempFileName);
		} catch (MalformedURLException e) {
			String msg = "Unable to open URL: " + filename + "\n";
			ErrorReporter.add(msg + e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IOException e) {
			String msg = "Error opening connection and loading file into reader\n";
			ErrorReporter.add(msg + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public void closeReader(){
		rs.closeReader();
		if(!tempData.delete()) {
			ErrorReporter.add("Unable to delete tempdata.txt file");
		}
	}

	@Override
	public boolean hasNext() {
		return rs.hasNext();
	}

	@Override
	public Object next(Object type) {
		return rs.next(type);
	}
}
