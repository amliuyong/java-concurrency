package ch03_sync_util.c06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Exchanging data between concurrent tasks
 */
public class Main {

    /**
     * Main method of the example
     *
     * @param args
     */
    public static void main(String[] args) {


        // Creates the exchanger
        Exchanger<List<String>> exchanger = new Exchanger<>();

        // Creates the producer
        Producer producer = new Producer(exchanger);
        // Creates the consumer
        Consumer consumer = new Consumer(exchanger);

        // Creates and starts the threads
        Thread threadProducer = new Thread(producer);
        Thread threadConsumer = new Thread(consumer);

        threadProducer.start();
        threadConsumer.start();

    }

}
