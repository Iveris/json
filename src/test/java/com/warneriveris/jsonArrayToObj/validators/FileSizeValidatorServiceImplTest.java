package com.warneriveris.jsonArrayToObj.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.warneriveris.jsonArrayToObj.validators.injectors.FileSizeValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

class FileSizeValidatorServiceImplTest {

	static ValidatorService<Integer> validator = null;
	static FileSizeValidatorServiceInjector injector = new FileSizeValidatorServiceInjector();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		validator = injector.getValidator();
	}

	@Test
	void successTest() throws Exception {
		assertTrue(validator.isValid(117));
	}

	@Test
	void failTest() throws Exception {
		assertFalse(validator.isValid(-5));
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		injector = null;
		validator = null;
	}

}
