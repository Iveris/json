package read;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import process.ReadPOJOQueue;
import services.read.ReadPOJO;
import services.read.ReadParallelStreamLocalFileService;
import services.write.WriteObj;

//TODO test this class with ReadLocalFileServiceImplTests as well
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReadParallelStreamLocalFileServiceTest {

	private static ReadParallelStreamLocalFileService rclfs = new ReadParallelStreamLocalFileService();
	private static String inputName = "src/test/resources/data/read/normalUnprocessedData.json";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		rclfs.getReader(inputName);
		ReadPOJO rp;
		while(rclfs.hasNext()) {
			rp = (ReadPOJO) rclfs.next(ReadPOJO.class);
			ReadPOJOQueue.add(rp);
		}
	}

	@Test
	@Order(1)
	void testResultSize() {
		int expected = 2;
		int actual = ReadPOJOQueue.getSize();
		assertEquals(expected, actual);
	}

	@Test
	@Order(2)
	void testResultAccuracy() {
		Map<String, WriteObj> map = new HashMap<>();
		ReadPOJO rp;
		while(ReadPOJOQueue.getSize() > 0) {
			rp = ReadPOJOQueue.remove();
			map.put(rp.getPath(), new WriteObj(rp.getUrl(), rp.getSize()));
		}
		assertEquals(new WriteObj("http://www.lqe.com/tya",164), map.get("itkbt"));
		assertEquals(new WriteObj("http://www.lnn.com/usl",233), map.get("frh"));
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		rclfs.closeReader();
	}
	
}
