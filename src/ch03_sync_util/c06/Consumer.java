package ch03_sync_util.c06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * This class implements the consumer of the example
 */
public class Consumer implements Runnable {

    /**
     * Exchager to synchronize with the consumer
     */
    private final Exchanger<List<String>> exchanger;
    /**
     * Buffer to save the events produced
     */
    private List<String> buffer;

    /**
     * Constructor of the class. Initializes its attributes
     *
     * @param exchanger Exchanger to syncrhonize with the consumer
     */
    public Consumer(Exchanger<List<String>> exchanger) {
        this.buffer = new ArrayList<>();
        this.exchanger = exchanger;
    }

    /**
     * Main method of the producer. It consumes all the events produced by the Producer. After
     * processes ten events, it uses the exchanger object to synchronize with
     * the producer. It sends to the producer an empty buffer and receives a buffer with ten events
     */
    @Override
    public void run() {

        for (int cycle = 1; cycle <= 10; cycle++) {
            System.out.printf("Consumer: Cycle %d\n", cycle);

            try {
                // Wait for the produced data and send the empty buffer to the producer
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("Consumer: %d\n", buffer.size());

            for (int j = 0; j < 10; j++) {
                String message = buffer.get(0);
                System.out.printf("Consumer: %s\n", message);
                buffer.remove(0);
            }

        }

    }

}
