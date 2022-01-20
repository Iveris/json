package com.warneriveris.jsonArrayToObj.process;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;

public class ReadPOJOQueue {

	private static ConcurrentLinkedQueue<ReadPOJO> q = new ConcurrentLinkedQueue<>();
	private static ReadPOJOQueue rpq;
	private static volatile boolean isReceivingInput;
	private ReadPOJOQueue() {}
	
	public static ReadPOJOQueue getInstance() {
		if(rpq == null) {
			rpq = new ReadPOJOQueue();
			isReceivingInput = true;
		}
		return rpq;
	}
	public static boolean add(ReadPOJO pojo) {
		return q.add(pojo);
	}
	public static ReadPOJO remove() {
		return q.poll();
	}
	public static ReadPOJO peek() {
		return q.peek();
	}
	public static int getSize() {
		return q.size();
	}

	public static boolean getIsReceivingInput() {
		return isReceivingInput;
	}

	public static void setIsReceivingInput(boolean moreReadInput) {
		ReadPOJOQueue.isReceivingInput = moreReadInput;
	}
}
