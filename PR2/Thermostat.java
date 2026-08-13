package PR2;

public class Thermostat {
    private String location;
    private int temperature;
    private static final int MIN = 16;
    private static final int MAX = 30;
    private static int activeCount = 0;

    // (b) Constructor(location, startTemp)
    public Thermostat(String location, int startTemp) {
        this.location = location;
        if (startTemp >= MIN && startTemp <= MAX) {
            this.temperature = startTemp;
        } else {
            this.temperature = 22;
        }
        activeCount++;
    }

    // (c) Constructor(location) - chains with this(location, 22)
    public Thermostat(String location) {
        this(location, 22);
    }

    // (d) raise()
    public void raise() {
        if (temperature < MAX) {
            temperature++;
        } else {
            System.out.println("Already at maximum (30)");
        }
    }

    // (e) lower()
    public void lower() {
        if (temperature > MIN) {
            temperature--;
        } else {
            System.out.println("Already at minimum (16)");
        }
    }

    // (f) getters
    public int getTemperature() {
        return temperature;
    }

    public static int getActiveCount() {
        return activeCount;
    }

    // (g) main
    public static void main(String[] args) {
        // out-of-range startTemp falls back to 22
        Thermostat t1 = new Thermostat("Living Room", 50);
        // single-arg constructor chains to startTemp 22
        Thermostat t2 = new Thermostat("Bedroom");

        for (int i = 0; i < 10; i++) {
            t2.raise();
            System.out.println(t2.getTemperature());
        }

        for (int i = 0; i < 20; i++) {
            t2.lower();
            System.out.println(t2.getTemperature());
        }

        System.out.println(Thermostat.getActiveCount());
    }
}
