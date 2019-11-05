package ch02_basic_sync.c06;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * The tryOptimisticRead() method always returns a value. It will be 0 if we are unable to use
 * the lock and a value different from 0 if we can use it.
 *
 * Remember that in this case, we always need to use the validate() method to check whether we can really access the data.
 *
 */
public class OptimisticReader implements Runnable {

    private final Position position;
    private final StampedLock lock;

    public OptimisticReader(Position position, StampedLock lock) {
        this.position = position;
        this.lock = lock;
    }

    @Override
    public void run() {
        long stamp;
        for (int i = 0; i < 100; i++) {
            try {
                stamp = lock.tryOptimisticRead();
                int x = position.getX();
                int y = position.getY();
                if (lock.validate(stamp)) {
                    System.out.printf("OptmisticReader: %d - (%d,%d)\n", stamp, x, y);
                } else {
                    System.out.printf("OptmisticReader: %d - Not Free\n", stamp);
                }
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
