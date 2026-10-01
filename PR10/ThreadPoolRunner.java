import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner {

    public static void main(String[] args) {
        // Fixed thread pool with 3 threads
        ExecutorService pool = Executors.newFixedThreadPool(3);

        // Submit 10 tasks
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;

            pool.submit(() -> {
                String threadName = Thread.currentThread().getName();

                System.out.println(
                    "Task " + taskId + " started by " + threadName
                );

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println(
                    "Task " + taskId + " finished by " + threadName
                );
            });
        }

        // No more tasks will be submitted
        pool.shutdown();

        try {
            // Wait for all tasks to finish
            if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Tasks did not finish in time.");
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("All tasks completed.");
    }
}