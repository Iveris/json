package com.warneriveris.jsonArrayToObj.errors;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked")
class ErrorReporterTest {

	@Test
	void testSingleThreadAddMethod() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Thread task = new Thread(() -> {
			String firstMsg = "ERROR!!!";
			String secondMsg = "Another ERROR!!!";
			ErrorReporter.add(firstMsg);
			ErrorReporter.add(secondMsg);
			
			Field field = null;
			try {
				field = ErrorReporter.class.getDeclaredField("q");
				field.setAccessible(true);
				var que = (ConcurrentLinkedQueue<String>) field.get(ErrorReporter.class);
				assertEquals(firstMsg, que.remove());
				assertEquals(secondMsg, que.remove());
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		
		task.start();
	}
	
	@Test
	void testMultiThreadAddMethod() throws InterruptedException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		String thread1First = "I am an error in thread 1";
		String thread1Second = "I am another error in thread 1";
		String thread2First = "I am an error in thread 2";
		String thread2Second = "I am another error in thread 2";

		//list for making comparisons with ErrorReporter ConcurrentLinkedQueue
		var msgList = new ArrayList<String>(); 
		msgList.addAll(Arrays.asList(thread1First, thread1Second, 
				thread2First, thread2Second));
		
		Thread t1 = new Thread(()-> {
			ErrorReporter.add(thread1First);
			ErrorReporter.add(thread1Second);
		});
		
		Thread t2 = new Thread(()-> {
			ErrorReporter.add(thread2First);
			ErrorReporter.add(thread2Second);
		});
		
		t1.start();
		t2.start();
		
		Thread.sleep(10);
		
		var field = ErrorReporter.class.getDeclaredField("q");
		field.setAccessible(true);
		var que = (ConcurrentLinkedQueue<String>) field.get(ErrorReporter.class);
		
		assertTrue(que.containsAll(msgList));
	}
}
