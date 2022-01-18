package app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import errors.ErrorReporter;
import logger.Summary;
import process.ProcessData;
import process.ReadPOJOQueue;
import process.injectors.ProcessParallelReadInjector;
import process.injectors.ProcessParallelWriteInjector;

class AppTest {

	private static final String inputPath = "src/test/resources/data/unprocessed/";
	private static final String outputPath = "src/test/resources/data/temporary/";
	private static final String inputFile = "500UnprocessedPretty.json";
	private static final String outputFile = "500ProcessedPretty.json";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ProcessData processReadData = new ProcessParallelReadInjector()
				.getService(inputPath + inputFile);
		ProcessData processWriteData = new ProcessParallelWriteInjector()
				.getService(outputPath + outputFile);
		
		Thread read = new Thread(processReadData);
		Thread write = new Thread(processWriteData);
		ReadPOJOQueue.getInstance();
		read.start();
		write.start();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Summary.printSummary();
		ErrorReporter.printErrors();
	}

	@Test
	void test() {
	}

}
