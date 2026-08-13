package PR2;

import java.util.Scanner;

public class CinemaShow {
    private String screen;
    private int seatsAvailable;
    private final int capacity;

    private static int totalBooked = 0;

    // Constructor
    CinemaShow(String screen, int capacity) {
        this.screen = screen;
        this.capacity = capacity;
        this.seatsAvailable = capacity;
    }

    // Default capacity = 100
    CinemaShow(String screen) {
        this(screen, 100);
    }

    // Book seats
    boolean book(int n) {
        if (n <= seatsAvailable) {
            seatsAvailable -= n;
            totalBooked += n;
            return true;
        }
        return false;
    }

    // Cancel seats
    void cancel(int n) {
        seatsAvailable += n;

        if (seatsAvailable > capacity)
            seatsAvailable = capacity;
    }

    int getSeatsAvailable() {
        return seatsAvailable;
    }

    static int getTotalBooked() {
        return totalBooked;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("1. Screen 1 - Capacity 50");
        System.out.println("2. Screen 2 - Capacity 100");
        System.out.println("3. Screen 3 - Capacity 150");

        System.out.print("Choose screen: ");
        int choice = sc.nextInt();

        String screen;
        int capacity;

        if (choice == 1) {
            screen = "Screen 1";
            capacity = 50;
        } else if (choice == 2) {
            screen = "Screen 2";
            capacity = 100;
        } else {
            screen = "Screen 3";
            capacity = 150;
        }

        CinemaShow cinema = new CinemaShow(screen, capacity);

        System.out.println("\nYou selected: " + screen);
        System.out.println("Total seats: " + capacity);

        System.out.print("Enter seats to book: ");
        int n = sc.nextInt();

        if (cinema.book(n))
            System.out.println("Booking successful!");
        else
            System.out.println("There is no space available.");

        System.out.println("Seats available: " + cinema.getSeatsAvailable());

        System.out.print("\nEnter seats to book again: ");
        n = sc.nextInt();

        if (cinema.book(n))
            System.out.println("Booking successful!");
        else
            System.out.println("There is no space available.");

        System.out.println("Seats available: " + cinema.getSeatsAvailable());

        System.out.print("\nEnter seats to cancel: ");
        n = sc.nextInt();

        cinema.cancel(n);

        System.out.println("Seats available after cancellation: "
                + cinema.getSeatsAvailable());

        System.out.println("Total booked: " + CinemaShow.getTotalBooked());

        sc.close();
    }
}