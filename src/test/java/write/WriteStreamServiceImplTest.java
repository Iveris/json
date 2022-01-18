package write;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import process.ReadPOJOQueue;
import services.read.ReadPOJO;
import services.write.WriteService;
import services.write.WriteStreamServiceImpl;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WriteStreamServiceImplTest {

	private static WriteService ws = new WriteStreamServiceImpl();
	private static String outputFileName = "src/test/resources/data/temporary/processedDataTest.json";
	private static Thread t = new Thread(() -> { ws.write(ReadPOJO.class); });
	private static File outputFile = new File(outputFileName);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ReadPOJO rp = new ReadPOJO("http://www.lnn.com/usl", "frh", 233);
		ReadPOJOQueue.add(rp);
		rp = new ReadPOJO("http://www.lqe.com/tya", "itkbt", 164);
		ReadPOJOQueue.add(rp);
		ws.getWriter(outputFileName);
		t.start();
		ReadPOJOQueue.setIsReceivingInput(false);
	}

	@Test
	@Order(1)
	void testFileSize() {
		long expectedSize = 166;
		assertEquals(expectedSize, outputFile.length());
	}

	@AfterAll
	@Order(2) //file was deleting before testFileSize() was complete, so had to use @Order
	static void tearDownAfterClass() throws Exception {
		outputFile.delete();
	}
}
