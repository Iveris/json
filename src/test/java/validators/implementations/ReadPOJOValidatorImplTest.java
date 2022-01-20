package validators.implementations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import services.read.ReadPOJO;
import services.read.ReadService;
import services.read.ReadServiceInjector;
import validators.injectors.ReadPOJOValidatorServiceInjector;
import validators.services.ValidatorService;

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
	//reader will automatically shutdown program, so must disable
	//close reader in rs.next() method
	void testMissingValues() {
		String missingValues = "./src/test/resources/data/read/missingValues.json";
		rs.getReader(missingValues);
		while(rs.hasNext()) {
			rp = (ReadPOJO) rs.next(ReadPOJO.class);
			assertFalse(validator.isValid(rp));
		}
	}
	
	@Test
	void testMissingFields() {
		String missingFields = "./src/test/resources/data/read/missingFields.json";
		rs.getReader(missingFields);
		while(rs.hasNext()) {
			rp = (ReadPOJO) rs.next(ReadPOJO.class);
			assertFalse(validator.isValid(rp));
		}
	}
	
	@Test
	void testCorruptData() {
		String corruptData = "./src/test/resources/data/read/corruptData.json";
		rs.getReader(corruptData);
		while(rs.hasNext()) {
			rp = (ReadPOJO) rs.next(ReadPOJO.class);
			assertFalse(validator.isValid(rp));
		}
		
	}

}
