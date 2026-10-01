public class CounterRace {

    // Shared counter
    static int counter = 0;

    // Number of threads
    static final int NUM_THREADS = 10;

    // Each thread increments this many times
    static final int INCREMENTS = 100000;

    // Unsynchronized increment
    static void incrementWithoutSync() {
        counter++;
    }

    // Synchronized increment
    static synchronized void incrementWithSync() {
        counter++;
    }

    public static void main(String[] args) throws InterruptedException {

        // ==========================================
        // PART 1: WITHOUT SYNCHRONIZATION
        // ==========================================

        counter = 0;

        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {

            threads[i] = new Thread(() -> {

                for (int j = 0; j < INCREMENTS; j++) {
                    incrementWithoutSync();
                }

            });

            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        int expected = NUM_THREADS * INCREMENTS;

        System.out.println("WITHOUT SYNCHRONIZATION");
        System.out.println("Expected count: " + expected);
        System.out.println("Actual count:   " + counter);

        // ==========================================
        // PART 2: WITH SYNCHRONIZATION
        // ==========================================

        counter = 0;

        threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {

            threads[i] = new Thread(() -> {

                for (int j = 0; j < INCREMENTS; j++) {
                    incrementWithSync();
                }

            });

            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println();
        System.out.println("WITH SYNCHRONIZATION");
        System.out.println("Expected count: " + expected);
        System.out.println("Actual count:   " + counter);
    }
}