package PR8;

import java.util.HashMap;
import java.util.Map;

// User-defined exception
class OutOfStockException extends Exception {
    private int shortfall;

    public OutOfStockException(String message, int shortfall) {
        super(message);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

// User-defined exception
class InvalidQuantityException extends Exception {

    public InvalidQuantityException(String message) {
        super(message);
    }
}

// User-defined Warehouse class
class Warehouse {
    private Map<String, Integer> stock = new HashMap<>();

    public Warehouse() {
        stock.put("Laptop", 5);
        stock.put("Mouse", 10);
        stock.put("Keyboard", 3);
    }

    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException(
                "Quantity must be greater than 0"
            );
        }

        int available = stock.getOrDefault(item, 0);

        if (qty > available) {
            int shortfall = qty - available;

            throw new OutOfStockException(
                "Not enough stock for " + item,
                shortfall
            );
        }

        stock.put(item, available - qty);

        System.out.println(
            qty + " " + item + "(s) issued successfully."
        );
    }
}

// Main class
public class WarehouseTest {

    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse();

        String[][] requests = {
            {"Laptop", "2"},
            {"Mouse", "20"},
            {"Keyboard", "0"},
            {"Keyboard", "1"}
        };

        for (String[] request : requests) {

            String item = request[0];
            int qty = Integer.parseInt(request[1]);

            try {
                warehouse.issue(item, qty);

            } catch (OutOfStockException e) {

                System.out.println("Out of Stock: " + e.getMessage());
                System.out.println("Shortfall: " + e.getShortfall());

            } catch (InvalidQuantityException e) {

                System.out.println(
                    "Invalid Quantity: " + e.getMessage()
                );
            }

            System.out.println("--------------------");
        }

        System.out.println("All requests processed.");
    }
}