package read;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import org.junit.jupiter.api.MethodOrderer;

import process.ReadPOJOQueue;
import services.read.ReadPOJO;
import services.read.ReadParallelStreamLocalFileService;
import services.write.WriteObj;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReadParallelStreamLocalFileServiceTest {

	private static ReadParallelStreamLocalFileService rs = new ReadParallelStreamLocalFileService();
	private static String inputName = "src/test/resources/data/read/normalUnprocessedData.json";
	private static ReadPOJO rp;
	private static Map<String, WriteObj> POJOMap = new HashMap<>();

	@Test
	@Order(1)
	void testNormalDataResultFileSize() {
		rs.getReader(inputName);
		ReadPOJO rp;
		while(rs.hasNext()) {
			rp = (ReadPOJO) rs.next(ReadPOJO.class);
			ReadPOJOQueue.add(rp);
		}
		int expected = 2;
		int actual = ReadPOJOQueue.getSize();
		assertEquals(expected, actual);
	}

	@Test
	@Order(2)
	void testNormalDataResultAccuracy() {
		while(ReadPOJOQueue.getSize() > 0) {
			rp = ReadPOJOQueue.remove();
			POJOMap.put(rp.getPath(), new WriteObj(rp.getUrl(), rp.getSize()));
		}
		assertEquals(new WriteObj("http://www.lqe.com/tya",164), POJOMap.get("itkbt"));
		assertEquals(new WriteObj("http://www.lnn.com/usl",233), POJOMap.get("frh"));
	}
	
	@Test
	@Order(3)
	void testDataWithExtraFields() throws IOException{
		String xtraData = "./src/test/resources/data/read/xtraFields.json";
		//ignores extra fields and only enters predefined fields
		rs.getReader(xtraData);
		while(rs.hasNext()) {
			rp = (ReadPOJO) rs.next(ReadPOJO.class);
			POJOMap.put(rp.getPath(), new WriteObj(rp.getUrl(), rp.getSize()));
		}		
		assertEquals(new WriteObj("http://www.lqe.com/tya",164), POJOMap.get("itkbt"));
		assertEquals(new WriteObj("http://www.lnn.com/usl",233), POJOMap.get("frh"));
		POJOMap.clear();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		rs.closeReader();
	}
	
}
