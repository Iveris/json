package write;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import process.ReadPOJOQueue;
import read.ReadPOJO;

class WriteStreamServiceImplTest {

	private static WriteService<String, WriteObj> ws = new WriteStreamServiceImpl();
	private static String outputFileName = "src/test/resources/data/temporary/processedDataTest.json";;
	private static String expectedFileName = "./src/test/resources/data/write/normalProcessedData.json";
	private static Thread t = new Thread(() -> { ws.write(new ReadPOJO("a", "b", 3)); });
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ReadPOJO rp = new ReadPOJO("http://www.lqe.com/tya", "itkbt", 164);
		ReadPOJOQueue.add(rp);
		rp = new ReadPOJO("http://www.lnn.com/usl", "frh", 233);
		ReadPOJOQueue.add(rp);
		ws.getWriter(outputFileName);
		t.start();
	}


	@Test
	void testFileSize() {
		ws.readComplete(true);
		Path actual = Paths.get(outputFileName);
		Path expected = Paths.get(expectedFileName);
		long actualSize = 0;
		long expectedSize = 0;
				
		try {
			actualSize = Files.size(actual);
			expectedSize = Files.size(expected);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expectedSize, actualSize);
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File outputFile = new File(outputFileName);
		outputFile.delete();
	}
}
