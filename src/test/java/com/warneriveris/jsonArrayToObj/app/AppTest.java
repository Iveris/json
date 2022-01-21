package com.warneriveris.jsonArrayToObj.app;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.warneriveris.jsonArrayToObj.errors.ErrorReporter;
import com.warneriveris.jsonArrayToObj.logger.Summary;
import com.warneriveris.jsonArrayToObj.process.ProcessData;
import com.warneriveris.jsonArrayToObj.process.ReadPOJOQueue;
import com.warneriveris.jsonArrayToObj.process.injectors.ProcessReadServiceInjector;
import com.warneriveris.jsonArrayToObj.process.injectors.ProcessWriteServiceInjector;

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
	void testOutputFileSizeLocal() throws InterruptedException {
		Thread task = new Thread(()-> {
			Thread read = new Thread(processReadData);
			Thread write = new Thread(processWriteData);
			ReadPOJOQueue.getInstance();
			read.start();
			write.start();
			
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
		});
		task.join(100);
	}
	
	@Test
	void testOutputFileSizeRemoteURL() throws InterruptedException {
		Thread task = new Thread(()-> {
			processReadData = new ProcessReadServiceInjector().getService(URLInputPath + inputFile);
			processWriteData = new ProcessWriteServiceInjector().getService(outputPath + outputFile);
			Thread read = new Thread(processReadData);
			Thread write = new Thread(processWriteData);
			ReadPOJOQueue.getInstance();
			read.start();
			write.start();
			
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
		});
		task.join(100);
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Summary.printSummary();
		ErrorReporter.printErrors();
		f.delete();
	}

}
