package ch11_design.c04;

import java.util.concurrent.locks.Lock;

public class Task2 implements Runnable {

    private final Lock lock;

    public Task2(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        Operations.readData();
        lock.unlock();
        Operations.processData();
        lock.lock();
        Operations.writeData();
        lock.unlock();
    }
}
