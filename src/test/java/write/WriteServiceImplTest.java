package write;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class WriteServiceImplTest {

	private static WriteService<String, WriteObj> ws;
	private static Map<String, WriteObj> data = new HashMap<>();
	private static String result;
	private static String expected = "{\"apath\":{\"url\":\"www.google.com/json\",\"size\":45},\"bpath\":{\"url\":\"www.spring.io/json\",\"size\":1930}}";
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		//Create Mock data
		data.put("apath", new WriteObj("www.google.com/json", 45));
		data.put("bpath", new WriteObj("www.spring.io/json", 1930));
		
		//Create Mock WriteService
		ws = new WriteService<>() {
			Gson gson = new GsonBuilder().create();
			@Override
			public WriteService<String, WriteObj> getWriter(String filename) {
				return this; //not actually writing to an output file, but to a String
			}
			@Override
			public void write(Object data) { result = gson.toJson(data).toString();}
			@Override
			public void closeWriter() {}
		};
		
	}

	@Test
	void testData() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ws.write(data);
		assertEquals(expected, result);
	}
}
