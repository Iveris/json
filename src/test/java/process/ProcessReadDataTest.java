package process;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import write.WriteObj;

class ProcessReadDataTest {

	private static ProcessData pd;
	private static ProcessReadData prd;
	private static String inputName = "src/test/resources/data/read/normalUnprocessedData.json";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		pd = new ProcessReadData();
		prd = new ProcessReadData();
	}
	
	@Test
	void testResultAccuracy() throws Exception {
		prd.process(inputName);
		prd.call();
		Map<String, WriteObj> map = prd.getResults();
		assertEquals(new WriteObj("http://www.lqe.com/tya",164), map.get("itkbt"));
		assertEquals(new WriteObj("http://www.lnn.com/usl",233), map.get("frh"));
	}

	@Test
	void testCallable() throws Exception {
		boolean complete = false;
		pd.process(inputName);
		complete = pd.call();
		assertTrue(complete);
	}

}
