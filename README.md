


## Thread Management
1. Creating, running, and setting the characteristics of a thread [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch01_sync_util/c01)
2. Interrupting a thread [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch01_sync_util/c02)
3. Controlling the interruption of a thread [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch01_sync_util/c03)
4. Sleeping and resuming a thread [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch01_sync_util/c04)
5. Waiting for the finalization of a thread [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch01_sync_util/c05)
6. Creating and running a daemon thread [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch01_sync_util/c06)
7. Processing uncontrolled exceptions in a thread [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch01_sync_util/c07)
8. Using thread local variables [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch01_sync_util/c08)
9. Grouping threads and processing uncontrolled exceptions in a group of threads. [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch01_sync_util/c09)
   
   Use threadGroup.interrupt() to interrupt a group of threads
10. Creating threads through a factory [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch01_sync_util/c10)

## Basic Thread Synchronization
1. Synchronizing a method - synchronized [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch02_sync_util/c01)
2. Using conditions in synchronized code - Producer and Consumer [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch02_sync_util/c02)
3. Synchronizing a block of code with a lock - ReentrantLock [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch02_sync_util/c03)

   The constructor of the ReentrantLock class admits a boolean parameter named fair;
   this parameter allows you to control its behavior. The false value is the default value and
   it's called the non-fair mode. In this mode, if some threads are waiting for a lock and the
   lock has to select one of these threads to get access to the critical section, it randomly selects
   anyone of them. The true value is called the fair mode. In this mode, if some threads are
   waiting for a lock and the lock has to select one to get access to a critical section, it selects
   the thread that has been waiting for the longest period of time.

   This mechanism presents some advantages, which are as follows:
   
   - It allows you to structure synchronized blocks in a more flexible way. With the
   synchronized keyword, you only have control over a synchronized block of
   code in a structured way. However, the Lock interface allows you to get more
   complex structures to implement your critical section.
   - The Lock interface provides additional functionalities over the synchronized
   keyword. One of the new functionalities is implemented by the tryLock()
   method. This method tries to get control of the lock, and if it can't, because it's
   used by another thread, it returns false. With the synchronized keyword, if
   thread (A) tries to execute a synchronized block of code when thread (B) is
   executing it, thread (A) is suspended until thread (B) finishes the execution of the
   synchronized block. With lock, you can execute the tryLock() method. This
   method returns a Boolean value indicating whether there is another thread
   running the code protected by this lock.
   - The ReadWriteLock interface allows a separation of read and write operations
   with multiple readers and only one modifier.
   - The Lock interface offers better performance than the synchronized keyword

4. Synchronizing data access with read/write locks [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch02_sync_util/c04)
   
   One of the most significant improvements offered by locks is the ReadWriteLock interface
   and the ReentrantReadWriteLock class, the unique class that implements that interface.
  
   This class has two locks: one for read operations and one for write operations. 
   There can be more than one thread using read operations simultaneously, but only one thread can use
   write operations. If a thread is doing a write operation, other threads can't write or read.
   
   When you get the read lock of a Lock interface, you can't modify the value of the variable. 
   It is the responsibility of the programmer to ensure correct use of these locks.
   
5. Using multiple conditions in a lock - lock.newCondition()  [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch02_sync_util/c05)
  
6. Advanced locking with the StampedLock class - Optimistic Read [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch02_sync_util/c06)

   The most important features of StampedLock locks are as follows:
   
   You can obtain control of the lock in three different modes:
  
     - Write: In this mode, you get exclusive access to the lock. No other
     thread can have control of the lock in this mode.
     
     - Read: In this mode, you have non-exclusive access to the lock.
     There can be other threads that have access to the lock in this mode
     or the Optimistic Read mode.
     
     - Optimistic Read: Here, the thread doesn't have control over the
     block. Other threads can get control of the lock in write mode.
     When you get a lock in the Optimistic Read mode and you want to
     access the shared data protected by it, you will have to check
     whether you can access them or not using the validate()
     method. The tryOptimisticRead() method always returns a value. 
     It will be 0 if we are unable to use the lock and a value different 
     from 0 if we can use it. Remember that in this case, we always
     need to use the validate() method to check whether we can really access the data.
  
   All these methods return a long value called stamp that we need to use to work
  with the lock. If a method returns zero, it means it tried to get a lock but it
  couldn't.
   
   A StampedLock lock is not a reentrant lock, such as the Lock and
   ReadWriteLock interfaces. If you call a method that tries to get the lock again, it
   may be blocked and you'll get a deadlock.
   
   It does not have the notion of ownership. They can be acquired by one thread and
   released by another.
   
   Finally, it doesn't have any policy about the next thread that will get control of
   the lock.
   
## Thread Synchronization Utilities
   You will learn how to use high-level mechanisms to synchronize multiple
   threads. These high-level mechanisms are as follows:
   - Semaphores: A semaphore is a counter that controls access to one or more shared
   resources. This mechanism is one of the basic tools of concurrent programming
   and is provided by most programming languages.
   - CountDownLatch: The CountDownLatch class is a mechanism provided by the
   Java language that allows a thread to wait for the finalization of multiple
   operations.
   - CyclicBarrier: The CyclicBarrier class is another mechanism provided by the
   Java language that allows the synchronization of multiple threads at a common
   point.
   - Phaser: The Phaser class is another mechanism provided by the Java language
   that controls the execution of concurrent tasks divided in phases. All the threads
   must finish one phase before they can continue with the next one.
   - Exchanger: The Exchanger class is another mechanism provided by the Java
   language that provides a point of data interchange between two threads.
   - CompletableFuture: The CompletableFuture class provides a mechanism
   where one or more tasks can wait for the finalization of another task that will be
   explicitly completed in an asynchronous way in future. This class was introduced
   in Java 8 and has introduced new methods in Java 9.
   
1. Controlling concurrent access to one or more copies of a resource [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch03_sync_util/c01)
   
   A semaphore is a counter that protects access to one or more shared resources.
   
   The acquire(), acquireUninterruptibly(), tryAcquire(), and release() methods
   have an additional version, which has an int parameter. This parameter represents the
   number of permits the thread that uses them wants to acquire or release, in other words, the
   number of units that this thread wants to delete or add to the internal counter of the
   semaphore.
   
   Fairness - The Semaphore class admits a second parameter in its constructor.
    
2. Waiting for multiple concurrent events - CountDownLatch [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch03_sync_util/c02)

   The CountDownLatch mechanism is not used to protect a shared resource or a
   critical section. It is used to synchronize one or more threads with the execution
   of various tasks.

3. Synchronizing tasks in a common point - CyclicBarrier [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch03_sync_util/c03)

   The CyclicBarrier class is initialized with an integer number, which is the number of
   threads that will be synchronized at a determined point. When one of these threads arrives
   at the determined point, it calls the await() method to wait for the other threads. When the
   thread calls this method, the CyclicBarrier class blocks the thread that is sleeping until
   the other threads arrive. When the last thread calls the await() method of the
   CyclicBarrier object, it wakes up all the threads that were waiting and continues with its
   job.
   
   One interesting advantage of the CyclicBarrier class is that you can pass an additional
   Runnable object as an initialization parameter, and the CyclicBarrier class executes this
   object as a thread when all the threads arrive at the common point. This characteristic
   makes this class adequate for parallelization of tasks using the divide and conquer
   programming technique.
   
   CyclicBarrier object can be reset to its initial state, assigning to its internal counter the
   value with which it was initialized.

4. Running concurrent-phased tasks - Phaser [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch03_sync_util/c04)
   
   This mechanism is useful when we have some concurrent tasks divided into steps. 
   The Phaser class provides us with a mechanism to synchronize threads at the end of each step, 
   so no thread will start with the second step until all the threads have finished the first one.
   
   arriveAndAwaitAdvance() - When a thread calls this
   method, Phaser decreases the number of threads that have to finalize the actual phase and
   puts this thread to sleep until all the remaining threads finish this phase
   
   arriveAndDeregister() - This notifies phaser that the thread has finished the actual phase, 
   but it won't participate in future phases, therefore, phaser won't have to wait for it to continue.
   
   forceTermination() - If you know that your phaser could be terminated,
   you should verify the return value of those methods (awaitAdvance() and
   arriveAndAwaitAdvance()) to know whether phaser has been terminated.
   
5. Controlling phase change in concurrent-phased tasks -  Custom Phaser [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch03_sync_util/c05)
    
    The onAdvance() method returns a Boolean value that indicates whether phaser has
    terminated or not. If phaser returns false, it indicates that it hasn't terminated; if this
    happens, the threads will continue with the execution of other phases. If phaser returns
    true, then phaser still wakes up the pending threads but moves phaser to the terminated
    state. With this, all future calls to any method of phaser will return immediately, and the
    isTerminated() method will return true.
 
 6. Exchanging data between concurrent tasks - Exchanger [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch03_sync_util/c06)
    
    The Exchanger class synchronizes only two threads, you can use it if you have a producer-consumer 
    problem with one producer and one consumer.
    
    the first thread that calls the exchange(buffer) method is put to sleep until the other threads arrive.
  
  7. Completing and linking tasks asynchronously - CompletableFuture [link](https://github.com/amliuyong/java-concurrency/tree/master/srcch03_sync_util/c07)
  
     You can work with a CompletableFuture class in different ways:
     
     - You can create a CompletableFuture object explicitly and use it as a
     synchronization point between tasks. One task will establish the value returned
     by CompletableFuture, using the complete() method, and the other tasks will
     wait for this value, using the get() or join() methods.
     
     - You can use a static method of the CompletableFuture class to execute
     Runnable or Supplier with the runAsync() and supplyAsync() methods.
     These methods will return a CompletableFuture object that will be completed
     when these tasks end their execution. In the second case, the value returned by
     Supplier will be the completion value of CompletableFuture.
     
     - You can specify other tasks to be executed in an asynchronous way after the
     completion of one or more CompletableFuture objects. This task can
     implement the Runnable, Function, Consumer or BiConsumer interfaces.
  