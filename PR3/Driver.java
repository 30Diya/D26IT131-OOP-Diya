package PR3;

public class Driver {

    public static void main(String[] args) {

        // Array containing 5 points with 2 repeated coordinates
        Point[] points = {
            new Point(1, 2),
            new Point(3, 4),
            new Point(1, 2), // Repeat
            new Point(5, 6),
            new Point(3, 4)  // Repeat
        };

        int distinct = 0;

        // Count distinct points
        for (int i = 0; i < points.length; i++) {

            boolean alreadySeen = false;

            // Check only previous points
            for (int j = 0; j < i; j++) {

                if (points[i].equals(points[j])) {
                    alreadySeen = true;
                    break;
                }
            }

            if (!alreadySeen) {
                distinct++;
            }
        }

        // Print all points
        System.out.println("Points:");
        for (Point p : points) {
            System.out.println(p);
        }

        // Print number of distinct points
        System.out.println("Distinct: " + distinct);
    }
}