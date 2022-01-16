package process;

import java.util.concurrent.ConcurrentLinkedQueue;

import read.ReadPOJO;

public class ReadPOJOQueue {

	private static ConcurrentLinkedQueue<ReadPOJO> q = new ConcurrentLinkedQueue<>();
	private static ReadPOJOQueue rpq;
	private ReadPOJOQueue() {}
	
	public static ReadPOJOQueue getInstance() {
		if(rpq == null) {
			rpq = new ReadPOJOQueue();
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
}
