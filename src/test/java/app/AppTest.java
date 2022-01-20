package app;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import errors.ErrorReporter;
import logger.Summary;
import process.ProcessData;
import process.ReadPOJOQueue;
import process.injectors.ProcessReadServiceInjector;
import process.injectors.ProcessWriteServiceInjector;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppTest {
	
	private static final String URLInputPath = "https://raw.githubusercontent.com/SuperWarnerMan/JsonDataGenerator/main/";
	private static final String inputPath = "src/test/resources/data/unprocessed/";
	private static final String outputPath = "src/test/resources/data/temporary/";
	private static final String inputFile = "500UnprocessedPretty.json";
	private static final String outputFile = "500ProcessedPretty.json";
	private static final String expected = "src/test/resources/data/write/";
	private static ProcessData processReadData = new ProcessReadServiceInjector().getService(inputPath + inputFile);
	private static ProcessData processWriteData = new ProcessWriteServiceInjector().getService(outputPath + outputFile);
	private static final File f = new File(outputPath + outputFile);
	
	@Test
	@Order(1)
//	@Disabled //can only run one test at a time, so one must be disabled
	void testOutputFileSizeLocal() throws InterruptedException {
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
		
		Path actualFile = Paths.get(outputPath + outputFile);
		Path expectedFile = Paths.get(expected + outputFile);
		long actualSize = 0;
		long expectedSize = 0;
		try {
			actualSize = Files.size(actualFile);
			expectedSize = Files.size(expectedFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(expectedSize, actualSize);
	}
	
	@Test
	@Order(2)
	@Disabled //can only run one test at a time, so one must be disabled
	void testOutputFileSizeRemoteURL() throws InterruptedException {
		processReadData = new ProcessReadServiceInjector().getService(URLInputPath + inputFile);
		processWriteData = new ProcessWriteServiceInjector().getService(outputPath + outputFile);
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
		
		Path actualFile = Paths.get(outputPath + outputFile);
		Path expectedFile = Paths.get(expected + outputFile);
		long actualSize = 0;
		long expectedSize = 0;
		try {
			actualSize = Files.size(actualFile);
			expectedSize = Files.size(expectedFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(expectedSize, actualSize);
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Summary.printSummary();
		ErrorReporter.printErrors();
		f.delete();
	}

}
