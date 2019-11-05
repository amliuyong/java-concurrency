package ch02_basic_sync.c03;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class simulates a print queue.
 *
 */
public class PrintQueue {

	private Lock queueLock;

	/**
	 * Creates a lock to control the access to the queue. With the boolean
	 * attribute, we control the fairness of the Lock
	 *
	 * The constructor of the ReentrantLock class admits a boolean parameter named fair;
	 * this parameter allows you to control its behavior. The false value is the default value and
	 * it's called the non-fair mode. In this mode, if some threads are waiting for a lock and the
	 * lock has to select one of these threads to get access to the critical section, it randomly selects
	 * anyone of them. The true value is called the fair mode. In this mode, if some threads are
	 * waiting for a lock and the lock has to select one to get access to a critical section, it selects
	 * the thread that has been waiting for the longest period of time.
	 *
	 */
	public PrintQueue(boolean fairMode) {
		queueLock = new ReentrantLock(fairMode);
	}

	/**
	 * Method that prints the Job. The printing is divided in two phase two show
	 * how the fairness attribute affects the election of the thread who has the
	 * control of the lock
	 * 
	 * @param document
	 *            The document to print
	 */
	public void printJob(Object document) {
		queueLock.lock();

		try {
			Long duration = (long) (Math.random() * 10000);
			System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n", Thread.currentThread().getName(),
					(duration / 1000));
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			queueLock.unlock();
		}

		queueLock.lock();
		try {
			Long duration = (long) (Math.random() * 10000);
			System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n", Thread.currentThread().getName(),
					(duration / 1000));
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			queueLock.unlock();
		}
	}
}
