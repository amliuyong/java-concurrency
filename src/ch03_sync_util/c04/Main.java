package ch03_sync_util.c04;

import java.util.concurrent.Phaser;

/**
 * Running concurrent-phased tasks
 */
public class Main {

    /**
     * Main method of the example
	 *
	 * In this recipe, you will learn how to use the Phaser class to synchronize three concurrent
	 * tasks. The three tasks look for files with the extension .log modified in the last 24 hours in
	 * three different folders and their subfolders. This task is divided into three steps:
	 *
	 * 1. Get a list of the files with the extension .log in the assigned folder and its
	 * subfolders.
	 * 2. Filter the list created in the first step by deleting the files modified more than 24
	 * hours ago.
	 * 3. Print the results in the console.
	 *
     */
    public static void main(String[] args) {

        // Creates a Phaser with three participants
        Phaser phaser = new Phaser(3);

        // Creates 3 FileSearch objects. Each of them search in different directory
        FileSearch system = new FileSearch("/tmp/", "log", phaser);
        FileSearch document = new FileSearch("/Users/yongliu/Documents/github2/", "java", phaser);
        FileSearch desktop = new FileSearch("/Users/yongliu/Desktop/", "pdf", phaser);

        // Creates a thread to run the system FileSearch and starts it
        Thread systemThread = new Thread(system, "tmp");
        systemThread.start();

        // Creates a thread to run the apps FileSearch and starts it
        Thread docThread = new Thread(document, "Documents");
		docThread.start();

        // Creates a thread to run the documents  FileSearch and starts it
        Thread desktopThread = new Thread(desktop, "Desktop");
		desktopThread.start();
        try {
            systemThread.join();
			docThread.join();
			desktopThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Terminated: %s\n", phaser.isTerminated());

    }

}
