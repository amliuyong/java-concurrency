package ch04_executors.c09;

import java.util.concurrent.*;

/**
 * Separating the launching of tasks and the processing of their results in an executor
 */
public class Main {

    public static void main(String[] args) {
        // Create the executor and three CompletionService using that executor
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletionService<String> service = new ExecutorCompletionService<>(executor);

        // Crete two ReportRequest objects and two Threads to execute them
        ReportRequest faceRequest = new ReportRequest("Face", service);
        ReportRequest onlineRequest = new ReportRequest("Online", service);
        Thread faceThread = new Thread(faceRequest);
        Thread onlineThread = new Thread(onlineRequest);

        // Create a ReportSender object and a Thread to execute  it
        ReportProcessor processor = new ReportProcessor(service);
        Thread senderThread = new Thread(processor);

        // Start the Threads
        System.out.printf("Main: Starting the Threads\n");
        faceThread.start();
        onlineThread.start();
        senderThread.start();

        // Wait for the end of the ReportGenerator tasks
        try {
            System.out.printf("Main: Waiting for the report generators.\n");
            faceThread.join();
            onlineThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Shutdown the executor
        System.out.printf("Main: Shuting down the executor.\n");
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // End the execution of the ReportSender
        processor.stopProcessing();
        System.out.printf("Main: Ends\n");

    }

}
