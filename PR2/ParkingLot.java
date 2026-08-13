package PR2;

import java.util.Scanner;

public class ParkingLot {
    private int twoWheelers;
    private int fourWheelers;
    private final int twoCap;
    private final int fourCap;
    private static long revenue = 0;

    public ParkingLot(int twoCap, int fourCap) {
        this.twoCap = twoCap;
        this.fourCap = fourCap;
        this.twoWheelers = 0;
        this.fourWheelers = 0;
    }

    // (b) park(String type)
    public void park(String type) {
        if (type.equals("two")) {
            if (twoWheelers < twoCap) {
                twoWheelers++;
                revenue += 20;
                System.out.println("Parked two-wheeler");
            } else {
                System.out.println("Full");
            }
        } else if (type.equals("four")) {
            if (fourWheelers < fourCap) {
                fourWheelers++;
                revenue += 40;
                System.out.println("Parked four-wheeler");
            } else {
                System.out.println("Full");
            }
        } else {
            System.out.println("Unknown vehicle type: " + type);
        }
    }

    // (c) leave(String type)
    public void leave(String type) {
        if (type.equals("two")) {
            if (twoWheelers > 0) {
                twoWheelers--;
            }
            System.out.println("Left two-wheeler");
        } else if (type.equals("four")) {
            if (fourWheelers > 0) {
                fourWheelers--;
            }
            System.out.println("Left four-wheeler");
        } else {
            System.out.println("Unknown vehicle type: " + type);
        }
    }

    public int getTwoWheelers() {
        return twoWheelers;
    }

    public int getFourWheelers() {
        return fourWheelers;
    }

    public static long getRevenue() {
        return revenue;
    }

    // (d) main - now interactive: the user types each event, and the
    // outcome is printed (returned) right after every entry.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingLot lot = new ParkingLot(2, 1);

        System.out.println("Parking lot ready (twoCap=2, fourCap=1).");
        System.out.println("Enter an event as: park two | park four | leave two | leave four");
        System.out.println("Type 'exit' to stop.");

        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("quit")) {
                break;
            }
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Please enter in the form: park two | leave four");
                continue;
            }

            String action = parts[0].toLowerCase();
            String type = parts[1].toLowerCase();

            if (action.equals("park")) {
                lot.park(type);
            } else if (action.equals("leave")) {
                lot.leave(type);
            } else {
                System.out.println("Unknown action: " + action);
                continue;
            }

            System.out.println("Two-wheelers now: " + lot.getTwoWheelers()
                + " | Four-wheelers now: " + lot.getFourWheelers()
                + " | Revenue so far: " + ParkingLot.getRevenue());
        }

        System.out.println();
        System.out.println("Final occupancy - Two-wheelers: " + lot.getTwoWheelers()
            + ", Four-wheelers: " + lot.getFourWheelers());
        System.out.println("Final revenue: " + ParkingLot.getRevenue());

        scanner.close();
    }
}