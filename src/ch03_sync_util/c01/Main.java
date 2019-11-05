package ch03_sync_util.c01;

/**
 * Controlling concurrent access to one or more copies of a resource
 */
public class Main {

    /**
     * Main method of the class. Run ten jobs in parallel that
     * send documents to the print queue at the same time.
     */
    public static void main(String args[]) {

        // Creates the print queue
        PrintQueue printQueue = new PrintQueue();

        // Creates twelve Threads
        Thread[] threads = new Thread[12];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Job(printQueue), "Thread " + i);
        }
        // Starts the Threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }

}
