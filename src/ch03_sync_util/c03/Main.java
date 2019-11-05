package ch03_sync_util.c03;

import java.util.concurrent.CyclicBarrier;

/**
 * Synchronizing tasks in a common point
 * <p>
 * In the example, you will look for a number in a matrix of numbers.
 * The matrix will be divided into subsets (using the divide and conquer
 * technique), so each thread will look for the number in one subset. Once all the threads have
 * finished their respective jobs, a final task will unify their results.
 */
public class Main {

    /**
     * Main method of the example
     *
     * @param args
     */
    public static void main(String[] args) {

        /*
         * Initializes the bi-dimensional array of data
         * 		10000 rows
         * 		1000 numbers in each row
         * 		Looking for number 5
         */
        final int ROWS = 10000;
        final int NUMBERS = 1000;
        final int SEARCH = 5;
        final int PARTICIPANTS = 5;
        final int LINES_PARTICIPANT = 2000;
        MatrixMock mock = new MatrixMock(ROWS, NUMBERS, SEARCH);

        // Initializes the object for the results
        Results results = new Results(ROWS);

        // Creates an Grouper object
        Grouper grouper = new Grouper(results);

        // Creates the CyclicBarrier object. It has 5 participants and, when
        // they finish, the CyclicBarrier will execute the grouper object
        CyclicBarrier barrier = new CyclicBarrier(PARTICIPANTS, grouper);

        // Creates, initializes and starts 5 Searcher objects
        Searcher searchers[] = new Searcher[PARTICIPANTS];
        for (int i = 0; i < PARTICIPANTS; i++) {

            searchers[i] = new Searcher(i * LINES_PARTICIPANT, (i * LINES_PARTICIPANT) + LINES_PARTICIPANT, mock,
					results, 5, barrier);

            Thread thread = new Thread(searchers[i]);
            thread.start();
        }
        System.out.printf("Main: The main thread has finished.\n");
    }

}
