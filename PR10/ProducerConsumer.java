import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {

    private static final int MAX_SIZE = 3;
    private static final int TOTAL_ITEMS = 10;

    private final Queue<Integer> buffer = new LinkedList<>();

    // Producer adds items
    public synchronized void produce(int item) throws InterruptedException {

        while (buffer.size() == MAX_SIZE) {
            wait();
        }

        buffer.add(item);

        System.out.println("Produced: " + item);

        // Notify consumer
        notify();
    }

    // Consumer removes items
    public synchronized int consume() throws InterruptedException {

        while (buffer.isEmpty()) {
            wait();
        }

        int item = buffer.remove();

        System.out.println("Consumed: " + item);

        // Notify producer
        notify();

        return item;
    }

    public static void main(String[] args) {

        ProducerConsumer pc = new ProducerConsumer();

        Thread producer = new Thread(() -> {

            try {
                for (int i = 1; i <= TOTAL_ITEMS; i++) {
                    pc.produce(i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }, "Producer");

        Thread consumer = new Thread(() -> {

            try {
                for (int i = 1; i <= TOTAL_ITEMS; i++) {
                    pc.consume();
                    Thread.sleep(400);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }, "Consumer");

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Production and consumption completed.");
    }
}