package com.warneriveris.jsonArrayToObj.process;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;

/**
 * SUT: {@link ProcessReadData}
 * 
 * @author Warner Iveris
 *
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProcessReadDataTest {

	private static ProcessData pd;
	private static String inputName = "src/test/resources/data/read/normalUnprocessedData.json";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		pd = new ProcessReadData(inputName);
	}

	// input data is processed by {@link ProcessReadData} in the set up, and the
	// results of that processing confirmed to be correct using the ReadPOJOQueue
	@Test
	void testResultQueue() {
		pd.run();
		ReadPOJO rp1 = new ReadPOJO("http://www.lqe.com/tya", "itkbt", 164);
		ReadPOJO rp2 = new ReadPOJO("http://www.lnn.com/usl", "frh", 233);
		if (ReadPOJOQueue.peek().getSize() == rp1.getSize()) {
			assertEquals(rp1, ReadPOJOQueue.remove());
			assertEquals(rp2, ReadPOJOQueue.remove());
		} else {
			assertEquals(rp2, ReadPOJOQueue.remove());
			assertEquals(rp1, ReadPOJOQueue.remove());
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		pd = null;
	}
}
