package process;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import read.ReadPOJO;

class ProcessWriteParallelStreamDataTest {

	private static ProcessData pd;
	private static String outputFileName = "src/test/resources/data/temporary/processedDataTest.json";
	private static File file;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ReadPOJO rp = new ReadPOJO("http://www.lqe.com/tya", "itkbt", 164);
		ReadPOJOQueue.add(rp);
		rp = new ReadPOJO("http://www.lnn.com/usl", "frh", 233);
		ReadPOJOQueue.add(rp);
		
		pd = new ProcessWriteParallelStreamData(outputFileName);
		Thread t = new Thread(pd);
		t.start();
		ReadPOJOQueue.setIsReceivingInput(false);
	}

	@Test
	void testFileSizes() throws Exception {
		long expectedSize = 103;
		file = new File(outputFileName);
		assertEquals(expectedSize, file.length());
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		file.delete();
	}
}
