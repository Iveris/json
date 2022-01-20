package write;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.warneriveris.jsonArrayToObj.process.ReadPOJOQueue;
import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;
import com.warneriveris.jsonArrayToObj.services.write.WriteService;
import com.warneriveris.jsonArrayToObj.services.write.WriteStreamServiceImpl;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WriteStreamServiceImplTest {

	private static WriteService ws = new WriteStreamServiceImpl();
	private static String actual = "src/test/resources/data/temporary/actual.json";
	private static String expected = "src/test/resources/data/write/processedDataTest.json";
	private static Thread t = new Thread(() -> { ws.write(ReadPOJO.class); });
	private static File outputFile = new File(actual);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ReadPOJO rp = new ReadPOJO("http://www.lnn.com/usl", "frh", 233);
		ReadPOJOQueue.add(rp);
		rp = new ReadPOJO("http://www.lqe.com/tya", "itkbt", 164);
		ReadPOJOQueue.add(rp);
		ws.getWriter(actual);
		t.start();
		ReadPOJOQueue.setIsReceivingInput(false);
	}
	
	@Test
	void testFileSize() throws InterruptedException {
		Thread task = new Thread(()-> {
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
		});
		task.join(100);
	}
	
	

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		outputFile.delete();
	}
}
