package ch03_sync_util.c06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * This class implements the producer
 */
public class Producer implements Runnable {

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
    public Producer(Exchanger<List<String>> exchanger) {
        this.buffer = new ArrayList<>();
        this.exchanger = exchanger;
    }

    /**
     * Main method of the producer. It produces 100 events. 10 cicles of 10 events.
     * After produce 10 events, it uses the exchanger object to synchronize with
     * the consumer. The producer sends to the consumer the buffer with ten events and
     * receives from the consumer an empty buffer
     */
    @Override
    public void run() {

        for (int cycle = 1; cycle <= 10; cycle++) {
            System.out.printf("Producer: Cycle %d\n", cycle);

            for (int j = 0; j < 10; j++) {
                String message = "Event " + (((cycle - 1) * 10) + j);
                System.out.printf("Producer: %s\n", message);
                buffer.add(message);
            }

            try {
                /*
                 * Change the data buffer with the consumer
                 */
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("Producer: %d\n", buffer.size());

        }
    }
}
