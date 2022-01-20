package app;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import errors.ErrorReporter;
import logger.Summary;
import process.ProcessData;
import process.ReadPOJOQueue;
import process.injectors.ProcessReadServiceInjector;
import process.injectors.ProcessWriteServiceInjector;

class AppTest {
	
	private static final String urlInputPath = "https://raw.githubusercontent.com/SuperWarnerMan/JsonDataGenerator/main/";
	private static final String inputPath = "src/test/resources/data/unprocessed/";
	private static final String outputPath = "src/test/resources/data/temporary/";
	private static final String inputFile = "500UnprocessedPretty.json";
	private static final String outputFile = "500ProcessedPretty.json";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ProcessData processReadData = new ProcessReadServiceInjector()
				.getService(inputPath + inputFile);
		ProcessData processWriteData = new ProcessWriteServiceInjector()
				.getService(outputPath + outputFile);
		
		Thread read = new Thread(processReadData);
		Thread write = new Thread(processWriteData);
		ReadPOJOQueue.getInstance();
		read.start();
		write.start();
		
		/*	without Thread.sleep() the main thread will 
		 *	interrupt the read and write
		 *	threads resulting in incomplete files.
		 */
		Thread.sleep(100);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Summary.printSummary();
		ErrorReporter.printErrors();
		File f = new File(outputPath + outputFile);
		f.delete();
	}

}
