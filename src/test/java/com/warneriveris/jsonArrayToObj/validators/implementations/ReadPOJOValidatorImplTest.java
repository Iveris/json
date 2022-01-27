package com.warneriveris.jsonArrayToObj.validators.implementations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;
import com.warneriveris.jsonArrayToObj.services.read.ReadService;
import com.warneriveris.jsonArrayToObj.services.read.ReadServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.injectors.ReadPOJOValidatorServiceInjector;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

/**
 * SUT: {@link ReadPOJOValidatorImpl}
 * 
 * @author Warner Iveris
 *
 * reader class will automatically shutdown program, 
 * so before running any test, disable closeReader() method
 * in the rs.next() method
 */

class ReadPOJOValidatorImplTest {

	private ValidatorService<ReadPOJO> validator = new ReadPOJOValidatorServiceInjector().getValidator();
	private ReadService rs = new ReadServiceInjector().getService();
	private ReadPOJO rp;
	
	@Test
	void testNormalData() {
		rs.getReader("src/test/resources/data/read/normalUnprocessedData.json");
		while(rs.hasNext()) {
			rp = rs.next();
			assertTrue(validator.isValid(rp));
		}
	}
	
	@Test
	@Disabled //disable closeReader() method in the rs.next() method before test
	void testMissingValues() {
		String missingValues = "./src/test/resources/data/read/missingValues.json";
		rs.getReader(missingValues);
		while(rs.hasNext()) {
			rp = rs.next();
			assertFalse(validator.isValid(rp));
		}
	}
	
	@Test
	@Disabled //disable closeReader() method in the rs.next() method before test
	void testMissingFields() {
		String missingFields = "./src/test/resources/data/read/missingFields.json";
		rs.getReader(missingFields);
		while(rs.hasNext()) {
			rp = rs.next();
			assertFalse(validator.isValid(rp));
		}
	}
	
	@Test
	@Disabled //disable closeReader() method in the rs.next() method before test
	void testCorruptData() {
		String corruptData = "./src/test/resources/data/read/corruptData.json";
		rs.getReader(corruptData);
		while(rs.hasNext()) {
			rp = rs.next();
			assertFalse(validator.isValid(rp));
		}
	}
	
	@Test
	void testNullValue() {
		assertFalse(validator.isValid(null));
	}

	@Test
	void testBadURL() {
		rp = new ReadPOJO("http\\www.com", "path", 0);
		assertFalse(validator.isValid(rp));
	}
	
	@Test
	void testBadPath() {
		rp = new ReadPOJO("http://google.com", "¿?*^#@", 0);
		assertFalse(validator.isValid(rp));
	}
	@Test
	void testBadSize() {
		rp = new ReadPOJO("http://google.com", "¿?*^#@", -1);
		assertFalse(validator.isValid(rp));
	}
}
