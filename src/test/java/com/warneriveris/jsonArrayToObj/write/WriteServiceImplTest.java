package com.warneriveris.jsonArrayToObj.write;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.warneriveris.jsonArrayToObj.services.write.WriteObj;
import com.warneriveris.jsonArrayToObj.services.write.WriteService;
import com.warneriveris.jsonArrayToObj.services.write.WriteServiceImpl;

class WriteServiceImplTest {

	private static WriteService ws = new WriteServiceImpl();
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