package ch09_testing.c10;

import edu.umd.cs.mtc.TestFramework;

/**
 * Main class of the example. Executes the test of the LinkedTransferQueue
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws Throwable {

        /*
         * Create a Test object
         */
        ProducerConsumerTest test = new ProducerConsumerTest();

        /*
         * Execute the test
         */
        System.out.printf("Main: Starting the test\n");
        TestFramework.runOnce(test);
        System.out.printf("Main: The test has finished\n");

    }

}
