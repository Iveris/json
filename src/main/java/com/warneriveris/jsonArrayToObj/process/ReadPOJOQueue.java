package com.warneriveris.jsonArrayToObj.process;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.warneriveris.jsonArrayToObj.services.read.ReadPOJO;

/**
 * Class that enables the concurrent exchange of data between read and write
 * service classes. The Concurrent Linked Queue stores ReadPOJO objects that
 * contain all the values from read services, which will then be consumed by
 * write services.
 * 
 * This queue is a non-blocking and allows the simultaneous removal of the head
 * node and addition to the tail node by separate threads. Because read services
 * will only add to the tail of the queue, and write services will only remove
 * the head of the queue, the two services can run in parallel without the need
 * for synchronization methods which would slow down the data transfer between
 * threads.
 * 
 * The boolean variable "isReceivingInput" is what coordinates the read and
 * write services to end the application. Once the read service has finished
 * reading the input file, it sets the variable to false, which is then read by
 * the write service. The write service then finishes writing all the remaining
 * objects in the queue to the output file and closes the writer.
 * 
 * @author Warner Iveris
 *
 */
public class ReadPOJOQueue {

	// Data structure that allows non-blocking concurrent reading and writing
	private static ConcurrentLinkedQueue<ReadPOJO> q = new ConcurrentLinkedQueue<>();
	private static ReadPOJOQueue rpq;

	// Variable which signals the write services to terminate
	private static volatile boolean isReceivingInput;

	private ReadPOJOQueue() {
	}

	public static ReadPOJOQueue getInstance() {
		if (rpq == null) {
			rpq = new ReadPOJOQueue();
			isReceivingInput = true;
		}
		return rpq;
	}

	/**
	 * Method only used by read services to add data read from the input file.
	 * Returns a boolean indicating the success or failure to add data to the tail
	 * of the queue.
	 * 
	 * @param pojo a class which the read service creates to contain data read from
	 *             the input file
	 * @return a boolean that signals the success or failure to add a new ReadPOJO
	 *         object to the queue by returning true or false respectively.
	 */
	public static boolean add(ReadPOJO pojo) {
		return q.add(pojo);
	}

	/**
	 * Method only used by write services to obtain data to enter into the output
	 * file. Returns the ReadPOJO object from the head of the queue and removes it
	 * from the queue so that only data that has not already been written remains in
	 * the queue.
	 * 
	 * @return ReadPOJO object which is removed from the head of the queue and
	 *         consumed by write services.
	 */
	public static ReadPOJO remove() {
		return q.poll();
	}

	/**
	 * Method to return a ReadPOJO object from the head of the queue without
	 * removing that object from the queue. In the application's current state, this
	 * method is only used in testing, but could also be useful in other read and
	 * write service implementations.
	 * 
	 * @return ReadPOJO object from the head of the queue without removing it from
	 *         the queue.
	 */
	public static ReadPOJO peek() {
		return q.peek();
	}

	/**
	 * Method used by write services only to help determine when to terminate the
	 * writing process. If the queue still holds data, then write services will
	 * continue writing to the output file, but if the queue does not hold any data
	 * and read services has changed the isReceivingInput variable to false, then
	 * writing services will terminate.
	 * 
	 * According to the Concurrent Linked List documentation, this is a potential
	 * sticking point in the application as this method requires a traversal of the
	 * entire queue to determine its size. Furthermore, the accuracy of this value
	 * is not guaranteed as values can be added or removed during the process of
	 * this traversal.
	 * 
	 * As this method is only used by write services and write services continues
	 * checking the size of the queue until read services have terminated, this
	 * should not be a problem in this implementation, but could be problem for
	 * other read and write implementations in the future.
	 * 
	 * @return integer indicating the number of objects held in the queue at any
	 *         given moment.
	 */
	public static int getSize() {
		return q.size();
	}

	/**
	 * The value returned is only used by write services to signal that no more data
	 * will be added to the queue.
	 * 
	 * @return boolean value which returns true of the read service is continuing to
	 *         read data from the input file, and false if the read service has
	 *         concluded reading data.
	 */
	public static boolean getIsReceivingInput() {
		return isReceivingInput;
	}

	/**
	 * This setter method is used only by the read service to signal the conclusion
	 * of data entry.
	 * 
	 * @param moreReadInput a true value signifies that read services are continuing
	 *                      to add data to the queue. A false value indicates the
	 *                      conclusion of data entry to the queue by read services.
	 */
	public static void setIsReceivingInput(boolean moreReadInput) {
		ReadPOJOQueue.isReceivingInput = moreReadInput;
	}
}
