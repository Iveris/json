package com.warneriveris.jsonArrayToObj.logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.gson.stream.JsonWriter;
import com.warneriveris.jsonArrayToObj.errors.ErrorReporter;
import com.warneriveris.jsonArrayToObj.process.ProcessData;
import com.warneriveris.jsonArrayToObj.process.ReadPOJOQueue;
import com.warneriveris.jsonArrayToObj.process.injectors.ProcessReadServiceInjector;
import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;
import com.warneriveris.jsonArrayToObj.services.write.WriteService;

class SummaryTest {

	private static final String input = "src/test/resources/data/logging/25UnprocessedPretty3RepeatedPaths.json";
	private static final String output = "src/test/resources/data/temporary/25UnprocessedPretty3RepeatedPaths.json";

	private static ProcessData processReadData;
	private static WriteService writeData;

	@BeforeAll
	static void setup() {
		processReadData = new ProcessReadServiceInjector().getService(input);
		writeData = new MockWriteServiceImpl().getWriter(output);
	}

	@Test
	void testSkippedAndProcessedMethods() throws InterruptedException, IOException {
		Thread read = new Thread(processReadData);
		read.start();
		writeData.write();
		Thread.sleep(10);
		
		assertEquals(2, Summary.getSkipped());
		assertEquals(23, Summary.getProcessed());
		
		
		writeData.closeWriter();
		Path out = Paths.get(output);
		Files.delete(out);
	}

	@AfterAll
	static void cleanup() throws IOException {
	}

}

/* Exact copy of write service implementation except writer.close() is
 * not called upon completion which allows testing the finished data
 * in Summary.class. Calls to print the results of ErrorReporter and 
 * Summary have also been omitted.
 */
class MockWriteServiceImpl implements WriteService {
	private JsonWriter writer;
	@Override
	public WriteService getWriter(String filename) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename, true);
		} catch (FileNotFoundException e) {
			ErrorReporter.add("Unable to access/create write file");
			LogManager.getLogger().error(e);
			System.exit(1);
		}
		writer = new JsonWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
		writer.setIndent("    "); //makes the output pretty
		try {
			writer.beginObject();
		} catch (IOException e) {
			writeFileError(e);
		}
		return this;
	}
	@Override
	public void write() {
		while(ReadPOJOQueue.getIsReceivingInput() || ReadPOJOQueue.getSize() > 0) {
			writeEntries();
		}
	}
	private void writeEntries() {
		while(ReadPOJOQueue.getSize() > 0) {
			ReadPOJO rpojo = ReadPOJOQueue.remove();
			try {
				writer.name(rpojo.getPath());
				writer.beginObject();
				writer.name("url").value(rpojo.getUrl());
				writer.name("size").value(rpojo.getSize());
				writer.endObject();
			} catch (IOException e) {
				writeFileError(e);
			}
		}		
	}
	@Override
	public void closeWriter() {
		try {
			writer.endObject();
			writer.close();
		} catch (IOException e) {
			ErrorReporter.add("Error closing file writer\n" + e.getLocalizedMessage());
			LogManager.getLogger().error(e);
			System.exit(1);
		}
	}
	private void writeFileError(Exception e) {
		ErrorReporter.add("Error writing to file");
		LogManager.getLogger().error(e);
		closeWriter();
	}
}
