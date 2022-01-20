package validators.implementations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import services.read.ReadPOJO;
import services.read.ReadService;
import services.read.ReadServiceInjector;
import validators.injectors.ReadPOJOValidatorServiceInjector;
import validators.services.ValidatorService;

/**
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
			rp = (ReadPOJO) rs.next(ReadPOJO.class);
			assertTrue(validator.isValid(rp));
		}
	}
	
	@Test
	@Disabled //disable closeReader() method in the rs.next() method before test
	void testMissingValues() {
		String missingValues = "./src/test/resources/data/read/missingValues.json";
		rs.getReader(missingValues);
		while(rs.hasNext()) {
			rp = (ReadPOJO) rs.next(ReadPOJO.class);
			assertFalse(validator.isValid(rp));
		}
	}
	
	@Test
	@Disabled //disable closeReader() method in the rs.next() method before test
	void testMissingFields() {
		String missingFields = "./src/test/resources/data/read/missingFields.json";
		rs.getReader(missingFields);
		while(rs.hasNext()) {
			rp = (ReadPOJO) rs.next(ReadPOJO.class);
			assertFalse(validator.isValid(rp));
		}
	}
	
	@Test
	@Disabled //disable closeReader() method in the rs.next() method before test
	void testCorruptData() {
		String corruptData = "./src/test/resources/data/read/corruptData.json";
		rs.getReader(corruptData);
		while(rs.hasNext()) {
			rp = (ReadPOJO) rs.next(ReadPOJO.class);
			assertFalse(validator.isValid(rp));
		}
	}

}
