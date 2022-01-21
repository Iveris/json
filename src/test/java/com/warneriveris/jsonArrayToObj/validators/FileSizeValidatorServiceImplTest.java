package com.warneriveris.jsonArrayToObj.validators;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.warneriveris.jsonArrayToObj.validators.injectors.FileSizeValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.services.FileValidatorService;

class FileSizeValidatorServiceImplTest {

	static URL dummyData = null; 
	static FileValidatorService<Integer, URL> validator = null;
	static FileSizeValidatorServiceInjector injector = new FileSizeValidatorServiceInjector();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dummyData = new URL("https://raw.githubusercontent.com/"
				+ "SuperWarnerMan/JsonDataGenerator/main/src/main/"
				+ "resources/jsonData.txt");
		validator = injector.getValidator();
	}

	@Test
	void successTest() throws Exception {
		assertTrue(validator.isValid(201, dummyData));
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
