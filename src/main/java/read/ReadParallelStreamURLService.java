package read;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.stream.JsonReader;

public class ReadParallelStreamURLService implements ReadService{

	private JsonReader reader;
	
	@Override
	public ReadService getReader(String filename) {
		InputStream in = null;
		try {
			in = new URL(filename).openStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));
		try {
			reader.beginArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public void closeReader() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object next(Object type) {
		// TODO Auto-generated method stub
		return null;
	}

}
