public class SeatBookingRace {

    static class SeatBooking {

        private int seatsLeft = 5;

        // ==========================================
        // WITHOUT SYNCHRONIZATION
        // ==========================================

        public void bookWithoutSync() {

            if (seatsLeft > 0) {

                // Artificial delay makes the race condition
                // easier to see.
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                seatsLeft--;

                System.out.println(
                        Thread.currentThread().getName()
                                + " successfully booked a seat."
                );

            } else {

                System.out.println(
                        Thread.currentThread().getName()
                                + " could not book a seat."
                );
            }
        }

        // ==========================================
        // WITH SYNCHRONIZATION
        // ==========================================

        public synchronized void bookWithSync() {

            if (seatsLeft > 0) {

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                seatsLeft--;

                System.out.println(
                        Thread.currentThread().getName()
                                + " successfully booked a seat."
                );

            } else {

                System.out.println(
                        Thread.currentThread().getName()
                                + " could not book a seat."
                );
            }
        }

        public int getSeatsLeft() {
            return seatsLeft;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // ==========================================
        // PART 1: WITHOUT SYNCHRONIZATION
        // ==========================================

        SeatBooking booking = new SeatBooking();

        Thread[] threads = new Thread[10];

        System.out.println("=================================");
        System.out.println("WITHOUT SYNCHRONIZATION");
        System.out.println("=================================");

        for (int i = 0; i < 10; i++) {

            final int threadNumber = i + 1;

            threads[i] = new Thread(
                    booking::bookWithoutSync,
                    "Thread-" + threadNumber
            );

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println();
        System.out.println("Seats left: " + booking.getSeatsLeft());

        // ==========================================
        // PART 2: WITH SYNCHRONIZATION
        // ==========================================

        booking = new SeatBooking();

        threads = new Thread[10];

        System.out.println();
        System.out.println("=================================");
        System.out.println("WITH SYNCHRONIZATION");
        System.out.println("=================================");

        for (int i = 0; i < 10; i++) {

            final int threadNumber = i + 1;

            threads[i] = new Thread(
                    booking::bookWithSync,
                    "Thread-" + threadNumber
            );

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println();
        System.out.println("Seats left: " + booking.getSeatsLeft());
    }
}