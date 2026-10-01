import java.util.Random;

public class ArraySumRace {

    static final int ARRAY_SIZE = 10_000_000;
    static final int NUM_THREADS = 4;

    static long total = 0;

    static void sumWithoutSync(int[] array, int start, int end) {

        for (int i = start; i < end; i++) {

            total += array[i];

        }
    }

    static synchronized void addToTotal(long value) {

        total += value;

    }

    static void sumWithSync(int[] array, int start, int end) {

        long localSum = 0;

        for (int i = start; i < end; i++) {

            localSum += array[i];

        }

        addToTotal(localSum);
    }
    static class Worker extends Thread {

        private int[] array;
        private int start;
        private int end;

        private long result;

        public Worker(int[] array, int start, int end) {

            this.array = array;
            this.start = start;
            this.end = end;

        }

        @Override
        public void run() {

            long sum = 0;

            for (int i = start; i < end; i++) {

                sum += array[i];

            }

            result = sum;
        }

        public long getResult() {
            return result;
        }
    }

    // ==========================================
    // MAIN
    // ==========================================

    public static void main(String[] args) throws InterruptedException {

        // ==========================================
        // CREATE ARRAY
        // ==========================================

        int[] numbers = new int[ARRAY_SIZE];

        Random random = new Random(1);

        for (int i = 0; i < ARRAY_SIZE; i++) {

            numbers[i] = random.nextInt(100) + 1;

        }

        // ==========================================
        // SINGLE-THREADED CORRECT ANSWER
        // ==========================================

        long correctAnswer = 0;

        for (int number : numbers) {

            correctAnswer += number;

        }

        System.out.println("Correct answer: " + correctAnswer);

        // ==========================================
        // METHOD 1: WITHOUT SYNCHRONIZATION
        // ==========================================

        total = 0;

        Thread[] threads = new Thread[NUM_THREADS];

        long startTime = System.nanoTime();

        int chunkSize = ARRAY_SIZE / NUM_THREADS;

        for (int i = 0; i < NUM_THREADS; i++) {

            int start = i * chunkSize;

            int end;

            if (i == NUM_THREADS - 1) {
                end = ARRAY_SIZE;
            } else {
                end = start + chunkSize;
            }

            final int finalStart = start;
            final int finalEnd = end;

            threads[i] = new Thread(() -> {

                sumWithoutSync(
                        numbers,
                        finalStart,
                        finalEnd
                );

            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long endTime = System.nanoTime();

        System.out.println();
        System.out.println("=================================");
        System.out.println("WITHOUT SYNCHRONIZATION");
        System.out.println("=================================");

        System.out.println("Result: " + total);
        System.out.println(
                "Time: " +
                        (endTime - startTime) / 1_000_000 +
                        " ms"
        );

        // ==========================================
        // METHOD 2: SYNCHRONIZED
        // ==========================================

        total = 0;

        threads = new Thread[NUM_THREADS];

        startTime = System.nanoTime();

        for (int i = 0; i < NUM_THREADS; i++) {

            int start = i * chunkSize;

            int end;

            if (i == NUM_THREADS - 1) {
                end = ARRAY_SIZE;
            } else {
                end = start + chunkSize;
            }

            final int finalStart = start;
            final int finalEnd = end;

            threads[i] = new Thread(() -> {

                sumWithSync(
                        numbers,
                        finalStart,
                        finalEnd
                );

            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        endTime = System.nanoTime();

        System.out.println();
        System.out.println("=================================");
        System.out.println("WITH SYNCHRONIZATION");
        System.out.println("=================================");

        System.out.println("Result: " + total);
        System.out.println(
                "Time: " +
                        (endTime - startTime) / 1_000_000 +
                        " ms"
        );

        // ==========================================
        // METHOD 3: LOCAL PARTIAL SUMS
        // ==========================================

        Worker[] workers = new Worker[NUM_THREADS];

        startTime = System.nanoTime();

        for (int i = 0; i < NUM_THREADS; i++) {

            int start = i * chunkSize;

            int end;

            if (i == NUM_THREADS - 1) {
                end = ARRAY_SIZE;
            } else {
                end = start + chunkSize;
            }

            workers[i] = new Worker(
                    numbers,
                    start,
                    end
            );

            workers[i].start();
        }

        for (Worker worker : workers) {
            worker.join();
        }

        long finalTotal = 0;

        for (Worker worker : workers) {

            finalTotal += worker.getResult();

        }

        endTime = System.nanoTime();

        System.out.println();
        System.out.println("=================================");
        System.out.println("LOCAL PARTIAL SUMS");
        System.out.println("=================================");

        System.out.println("Result: " + finalTotal);
        System.out.println(
                "Time: " +
                        (endTime - startTime) / 1_000_000 +
                        " ms"
        );
    }
}