package ch11_design.c07;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            Task task = new Task();
            Thread thread = new Thread(task);
            thread.start();
        }

    }

}
