package read;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.input.CharSequenceInputStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import write.WriteObj;

class ReadLocalFileServiceImplTest {

	//MockReadService is defined below
	private static ReadService rs = new MockReadService();
	private static String normalData;
	private static String xtraData;
	private static String missingData; //TODO fix in process class
	private static String corruptData; //TODO fix in process class
	private static ReadPOJO rJson; //class to put data read from ReadService into
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {		
		//Create Mock Data: 1) normal, 2) extra fields, 3) corrupt
		normalData = "[{\"url\":\"http://www.kicy.com/fpqt\",\"path\":\"oioev\",\"size\":586},{\"url\":\"http://www.dq.com/bf\",\"path\":\"jcf\",\"size\":299}]";
		xtraData = "[{\"url\":\"http://www.kicy.com/fpqt\",\"path\":\"oioev\",\"size\":586,\"status\":\"OK\"},{\"url\":\"http://www.dq.com/bf\",\"path\":\"jcf\",\"dq\":805,\"size\":299}]";
		missingData = "[{\"url\":\"http://www.kicy.com/fpqt\",\"path\":\"\",\"size\":586},{\"url\":\"http://www.dq.com/bf\"}]";
		corruptData = "[{\"url\":\"http://www.kicy.com/fpqt\",\"path\":\"����\",\"size\":5�6},{\"url\":\"http://www.dq.com/bf\",\"path\":\"jcf\",\"size\":299}]";
	}

	@Test
	void testNormalData() throws IOException {
		rs.getReader(normalData);
		Map<String, WriteObj> pojoList = new HashMap<>();
		while(rs.hasNext()) {
			rJson = (ReadPOJO) rs.next(ReadPOJO.class);
			pojoList.put(rJson.getPath(), new WriteObj(rJson.getUrl(), rJson.getSize()));
		}
		assertEquals(pojoList.get("oioev"), new WriteObj("http://www.kicy.com/fpqt",586));
		assertEquals(pojoList.get("jcf"), new WriteObj("http://www.dq.com/bf",299));
	}
	
	@Test
	void testDataWithExtraFields() throws IOException{
		//ignores extra fields and only enters predefined fields
		rs.getReader(xtraData);
		Map<String, WriteObj> pojoList = new HashMap<>();
		while(rs.hasNext()) {
			rJson = (ReadPOJO) rs.next(ReadPOJO.class);
			pojoList.put(rJson.getPath(), new WriteObj(rJson.getUrl(), rJson.getSize()));
		}		
		assertEquals(pojoList.get("oioev"), new WriteObj("http://www.kicy.com/fpqt",586));
		assertEquals(pojoList.get("jcf"), new WriteObj("http://www.dq.com/bf",299));
	}
	
	@Test
	//TODO This should omit entry, not fill in with null
	void testMissingData() {
		Map<String, WriteObj> pojoList = new HashMap<>();
		rs.getReader(missingData);
		while(rs.hasNext()) {
			rJson = (ReadPOJO) rs.next(ReadPOJO.class);
			pojoList.put(rJson.getPath(), new WriteObj(rJson.getUrl(), rJson.getSize()));
		}
		System.out.println(pojoList.keySet());
		System.out.println(pojoList.get(""));
		
	}
	
	@Test
	void testCorruptData() {
		Map<String, WriteObj> pojoList = new HashMap<>();
		rs.getReader(corruptData);
		while(rs.hasNext()) {
			rJson = (ReadPOJO) rs.next(ReadPOJO.class);
			pojoList.put(rJson.getPath(), new WriteObj(rJson.getUrl(), rJson.getSize()));
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		rs.closeReader();
	}
}

//All the same functionality of ReadService but reads from a String
//rather than a file.
class MockReadService implements ReadService{
	private InputStream fis;
	private JsonReader jread;
	private Gson gson = new GsonBuilder().create();
	
	@Override
	public ReadService getReader(String filename){
		fis = new CharSequenceInputStream(filename, StandardCharsets.UTF_8);
		jread = new JsonReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
		try {
			jread.beginArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public boolean hasNext() {
		try {
			return jread.hasNext();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
