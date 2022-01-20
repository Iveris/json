package read;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonSyntaxException;

import services.read.ReadLocalFileServiceImpl;
import services.read.ReadPOJO;
import services.read.ReadService;
import services.write.WriteObj;

class ReadLocalFileServiceImplTest {

	//MockReadService is defined below
	private static ReadService rs = new ReadLocalFileServiceImpl();
	private static ReadPOJO rp; //class to put data read from ReadService into
	private static String normalData = "./src/test/resources/data/read/normalUnprocessedData.json";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {		
		
	}

	@Test
	void testNormalData() throws IOException {
		rs.getReader(normalData);
		Map<String, WriteObj> pojoList = new HashMap<>();
		while(rs.hasNext()) {
			rp = (ReadPOJO) rs.next(ReadPOJO.class);
			pojoList.put(rp.getPath(), new WriteObj(rp.getUrl(), rp.getSize()));
		}
		assertEquals(new WriteObj("http://www.lqe.com/tya",164), pojoList.get("itkbt"));
		assertEquals(new WriteObj("http://www.lnn.com/usl",233), pojoList.get("frh"));
	}
	
	@Test
	void testDataWithExtraFields() throws IOException{
		String xtraData = "./src/test/resources/data/read/xtraFields.json";
		//ignores extra fields and only enters predefined fields
		rs.getReader(xtraData);
		Map<String, WriteObj> pojoList = new HashMap<>();
		while(rs.hasNext()) {
			rp = (ReadPOJO) rs.next(ReadPOJO.class);
			pojoList.put(rp.getPath(), new WriteObj(rp.getUrl(), rp.getSize()));
		}		
		assertEquals(new WriteObj("http://www.lqe.com/tya",164), pojoList.get("itkbt"));
		assertEquals(new WriteObj("http://www.lnn.com/usl",233), pojoList.get("frh"));
	}
	
	@Test
	void testMissingValues() {
		String missingValues = "./src/test/resources/data/read/missingValues.json";
		Assertions.assertThrows(JsonSyntaxException.class, ()-> {
			Map<String, WriteObj> pojoList = new HashMap<>();
			rs.getReader(missingValues);
			while(rs.hasNext()) {
				rp = (ReadPOJO) rs.next(ReadPOJO.class);
				pojoList.put(rp.getPath(), new WriteObj(rp.getUrl(), rp.getSize()));
			}
		});
	}
	@Test
	void testMissingFields() {
		String missingFields = "./src/test/resources/data/read/missingFields.json";
		Assertions.assertThrows(JsonSyntaxException.class, ()-> {
			Map<String, WriteObj> pojoList = new HashMap<>();
			rs.getReader(missingFields);
			while(rs.hasNext()) {
				rp = (ReadPOJO) rs.next(ReadPOJO.class);
				pojoList.put(rp.getPath(), new WriteObj(rp.getUrl(), rp.getSize()));
			}
		});
	}
	
	@Test
	void testCorruptData() {
		String corruptData = "./src/test/resources/data/read/corruptData.json";
		Assertions.assertThrows(JsonSyntaxException.class, () -> {
			Map<String, WriteObj> pojoList = new HashMap<>();
			rs.getReader(corruptData);
			while(rs.hasNext()) {
				rp = (ReadPOJO) rs.next(ReadPOJO.class);
				pojoList.put(rp.getPath(), new WriteObj(rp.getUrl(), rp.getSize()));
			}
		});
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		rs.closeReader();
	}
}
