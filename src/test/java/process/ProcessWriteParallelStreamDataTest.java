package process;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import read.ReadPOJO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProcessWriteParallelStreamDataTest {

	private static ProcessData pd;
	private static String outputFileName = "src/test/resources/data/temporary/processedDataTest.json";
	private static File file = new File(outputFileName);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ReadPOJO rp = new ReadPOJO("http://www.lqe.com/tya", "itkbt", 164);
		ReadPOJOQueue.add(rp);
		rp = new ReadPOJO("http://www.lnn.com/usl", "frh", 233);
		ReadPOJOQueue.add(rp);
		
		pd = new ProcessWriteParallelStreamData();
		pd.process(outputFileName);
	}

	@Test
	@Order(1)
	void testCallable() throws Exception {
		CompletableFuture.supplyAsync(() -> {
			try {
				return pd.call();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		});
		ReadPOJOQueue.setIsReceivingInput(false);
	}

	@Test
	@Order(2)
	void testFileSizes() throws Exception {
		long expectedSize = 103;
		assertEquals(expectedSize, file.length());
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		file.delete();
	}
}
