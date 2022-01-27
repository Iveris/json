package com.warneriveris.jsonArrayToObj.validators.implementations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.warneriveris.jsonArrayToObj.validators.injectors.UniqueKeyValidatorInjector;
import com.warneriveris.jsonArrayToObj.validators.services.ValidatorService;

/**
 * SUT: {@link UniqueKeyValidatorImpl}
 * 
 * @author Warner Iveris
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UniqueKeyValidatorImplTest {

	private static ValidatorService<String> vs;

	@BeforeAll
	static void setup() {
		vs = new UniqueKeyValidatorInjector<String>().getValidator();
	}

	@Test
	@Order(1)
	void testSameString() {
		String test = "path";
		/*
		 * isValid method attempts to add string to the HashSet and returns the set
		 * response true if it was added and false if it was not
		 */
		assertTrue(vs.isValid(test));
		assertFalse(vs.isValid(test));

	}

	@Test
	@Order(2)
	void testDifferentString() {
		assertTrue(vs.isValid("newstring"));
	}

	@Test
	@Order(3)
	void testEquivalentString() {
		String test2 = "path";
		assertFalse(vs.isValid(test2));
	}

	@Test
	@Order(4)
	void testConcurrency() throws InterruptedException {
		Thread t1 = new Thread(() -> {
			vs.isValid("TheTerminator");
			vs.isValid("JohnWick");
			vs.isValid("path");
			vs.isValid("TheBourneIdentity");
		});

		Thread t2 = new Thread(() -> {
			vs.isValid("newstring");
			vs.isValid("T2:JudgementDay");
			vs.isValid("Chapter2");
			vs.isValid("TheBourneSupremacy");
		});
		
		t1.start();
		t2.start();
		Thread.sleep(10);
		
		assertFalse(vs.isValid("TheTerminator"));
		assertFalse(vs.isValid("JohnWick"));
		assertFalse(vs.isValid("path"));
		assertFalse(vs.isValid("TheBourneIdentity"));
		assertFalse(vs.isValid("newstring"));
		assertFalse(vs.isValid("T2:JudgementDay"));
		assertFalse(vs.isValid("Chapter2"));
		assertFalse(vs.isValid("TheBourneSupremacy"));
	}

}
