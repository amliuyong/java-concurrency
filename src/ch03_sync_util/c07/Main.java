package ch03_sync_util.c07;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * First, we're going to create a task that will generate a seed. Using this seed, the next task will
 * generate a list of random numbers. Then, we will execute three parallel tasks:
 * 
 * 1. Step 1 will calculate the nearest number to 1,000, in the list.
 * 2. Step 2 will calculate the biggest number in the list.
 * 3. Step 3 will calculate the average number between the largest and smallest numbers in the list.
 *
 */
public class Main {

    public static void main(String[] args) {

        System.out.printf("Main: Start\n");

        CompletableFuture<Integer> seedFuture = new CompletableFuture<>();
        Thread seedThread = new Thread(new SeedGenerator(seedFuture));
        seedThread.start();
        System.out.printf("Main: Getting the seed\n");
        int seed = 0;
        try {
            seed = seedFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.printf("Main: The seed is: %d\n", seed);


        System.out.printf("Main: Launching the list of numbers generator\n");
        NumberListGenerator task = new NumberListGenerator(seed);
        CompletableFuture<List<Long>> startFuture = CompletableFuture.supplyAsync(task);

        System.out.printf("Main: Launching step 1\n");
        // Step 1 will calculate the nearest number to 1,000, in the list
        CompletableFuture<Long> step1Future = startFuture.thenApplyAsync(list -> {
            System.out.printf("%s: Step 1: Start\n", Thread.currentThread().getName());
            long selected = 0;
            long selectedDistance = Long.MAX_VALUE;
            long distance;
            for (Long number : list) {
                distance = Math.abs(number - 1000);
                if (distance < selectedDistance) {
                    selected = number;
                    selectedDistance = distance;
                }
            }
            System.out.printf("%s: Step 1: Result - %d\n", Thread.currentThread().getName(), selected);
            return selected;
        });


        System.out.printf("Main: Launching step 2\n");
        //Step 2 will calculate the biggest number and print
        CompletableFuture<Long> step2Future = startFuture
                .thenApplyAsync(list -> list.stream().max(Long::compare).get());

        CompletableFuture<Void> write2Future = step2Future.thenAccept(selected -> {
            System.out.printf("%s: Step 2: Result - %d\n", Thread.currentThread().getName(), selected);
        });

        System.out.printf("Main: Launching step 3\n");

        // Step 3 will calculate the average number between the largest and smallest numbers
        NumberSelector numberSelector = new NumberSelector();
        CompletableFuture<Long> step3Future = startFuture.thenApplyAsync(numberSelector);


        System.out.printf("Main: Waiting for the end of the three steps\n");


        CompletableFuture<Void> waitFuture = CompletableFuture.allOf(step1Future, write2Future, step3Future);

        CompletableFuture<Void> finalFuture = waitFuture.thenAcceptAsync((param) -> {
            System.out.printf("Main: The CompletableFuture example has been completed.");
        });

        finalFuture.join();
    }

}
