package com.warneriveris.jsonArrayToObj.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.warneriveris.jsonArrayToObj.validators.injectors.FileSizeValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.services.FileValidatorService;

class FileSizeValidatorServiceImplTest {

	static String dummyData = "https://raw.githubusercontent.com/SuperWarnerMan/"
			+ "JsonDataGenerator/main/src/main/resources/jsonData.txt";
	static String largerDataFile = "https://raw.githubusercontent.com/SuperWarnerMan/"
			+ "json/main/src/test/resources/data/unprocessed/50kUnprocessedPretty.json";
	static FileValidatorService<Integer, String> validator = null;
	static FileSizeValidatorServiceInjector injector = new FileSizeValidatorServiceInjector();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		validator = injector.getValidator();
	}

	@Test
	void successTest() throws Exception {
		assertTrue(validator.isValid(201, dummyData));
		assertTrue(validator.isValid(4421202, largerDataFile));
	}

	@Test
	void failTest() throws Exception {
		assertFalse(validator.isValid(200, dummyData));
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		injector = null;
		validator = null;
	}

}
