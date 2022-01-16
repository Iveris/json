package validators;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import validators.injectors.URLValidatorServiceInjector;
import validators.services.ValidatorService;
import validators.injectors.ValidatorServiceInjector;


class URLValidatorServiceImplTest {

	static ValidatorService<String> validator = null;
	static ValidatorServiceInjector<String> injector = null;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		injector = new URLValidatorServiceInjector();
		validator = injector.getValidator();
	}

	@Test
	void testGoodURLs() {
		String url = "https://www.illumina.com/";
		assertTrue(validator.isValid(url));
		url = "https://www.warneriveris.com/NameThatNoteApp/NameThatNote.html";
		assertTrue(validator.isValid(url));
		url = "https://cwiki.apache.org/confluence/display/COMMONS/FrontPage";
		assertTrue(validator.isValid(url));
		url = "http://www.newegg.com/p/pl?Submit=ENE&IsNodeId=1&N=100006740%20600414170";
		assertTrue(validator.isValid(url));
		url = "http://github.com/TEAMMATES/teammates/blob/master/src/client/java/teammates/client/connector/DatastoreClient.java";
		assertTrue(validator.isValid(url));
	}
	
	@Test
	void testBadURLs() {
		String badUrl = "http://www.illumina.m/";
		assertFalse(validator.isValid(badUrl));
		badUrl = "htp://www.warneriveris.com/NameThatNoteApp/NameThatNote.html";
		assertFalse(validator.isValid(badUrl));
		badUrl = "https://cwiki.apache.orgconfluencedisplayCOMMONSFrontPage";
		assertFalse(validator.isValid(badUrl));
		badUrl = "newegg.com/p/pl?Submit=ENE&IsNodeId=1&N=100006740%20600414170";
		assertFalse(validator.isValid(badUrl));
		badUrl = "DatastoreClient.java";
		assertFalse(validator.isValid(badUrl));
		badUrl = "file:///localhost/";
		assertFalse(validator.isValid(badUrl));
		badUrl = "Users/warner/scripts/trig.sh";
		assertFalse(validator.isValid(badUrl));
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		validator = null;
		injector = null;
	}

}
