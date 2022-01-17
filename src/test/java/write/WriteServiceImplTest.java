package write;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import errors.ErrorReporter;
import read.ReadService;

class WriteServiceImplTest {

	private static WriteService<String, WriteObj> ws = new WriteServiceImpl<>();
	private static Map<String, WriteObj> data = new HashMap<>();
	private static String actual = "./src/test/resources/data/write/output.json";
	private static String expected = "./src/test/resources/data/write/normalProcessedData.json";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		data.put("itkbt", new WriteObj("http://www.lqe.com/tya", 164));
		data.put("frh", new WriteObj("http://www.lnn.com/usl", 233));
		ws.getWriter(actual);
	}

	@Test
	void testWrite() {
		ws.write(data);
		ws.closeWriter();
		
		Path actualFile = Paths.get(actual);
		Path expectedFile = Paths.get(expected);
		
		long actualSize = 0;
		long expectedSize = 0;
				
		try {
			actualSize = Files.size(actualFile);
			expectedSize = Files.size(expectedFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expectedSize, actualSize);
		
	}
	
	@AfterAll
	static void cleanUp() {
		File actualFile = new File(actual);
		actualFile.delete();
	}
}

class MockReadService implements ReadService {

	private InputStream fis;
	private JsonReader jread;
	private Gson gson = new GsonBuilder().create();
	
	@Override
	public ReadService getReader(String filename){
		File file = new File(filename);
		if(!file.exists()) {
			String msg = "File cannot be found\n";
			ErrorReporter.add(msg);
			System.exit(1);
		}
		try {
			fis = new FileInputStream(file);
			jread = new JsonReader(new InputStreamReader(fis, StandardCharsets.UTF_8));			
//			jread.beginObject();
		} catch(IOException e) {
			String msg = "Error loading file into JsonReader\n";
			ErrorReporter.add(msg + e.getLocalizedMessage());
		}
		return this;
	}

	@Override
	public boolean hasNext() {
		try {
			return jread.hasNext();
		} catch (IOException e) {
			String msg = "Error reading file\n";
			ErrorReporter.add(msg + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Object next(Object type) {
		return gson.fromJson(jread, (java.lang.reflect.Type) type);
	}

	@Override
	public void closeReader() {
		try {
			jread.close();
			fis.close();
		} catch (IOException e) {
			String msg = "Error closing file reader\n";
			ErrorReporter.add(msg + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}
