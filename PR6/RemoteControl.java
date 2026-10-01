package PR6;


interface Switchable {
    void on();
    void off();

    default void toggle() {
        on();
    }
}

class Fan implements Switchable {
    public void on() {
        System.out.println("Fan is ON");
    }

    public void off() {
        System.out.println("Fan is OFF");
    }
}

class Light implements Switchable {
    public void on() {
        System.out.println("Light is ON");
    }

    public void off() {
        System.out.println("Light is OFF");
    }
}

@FunctionalInterface
interface SwitchPolicy {
    boolean maySwitchOn(Switchable device, int hour);
}

public class RemoteControl {
    public static void main(String[] args) {

        Switchable[] devices = {
            new Fan(),
            new Light()
        };

        // Toggle every device
        for (Switchable device : devices) {
            device.toggle();
        }

        // Anonymous class
        SwitchPolicy dayPolicy = new SwitchPolicy() {
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };

        // Lambda
        SwitchPolicy eveningPolicy =
                (device, hour) -> hour >= 18 && hour <= 22;

        int hour = 20;

        System.out.println("Hour: " + hour);

        for (Switchable device : devices) {
            System.out.println(
                "Day policy: " +
                dayPolicy.maySwitchOn(device, hour)
            );

            System.out.println(
                "Evening policy: " +
                eveningPolicy.maySwitchOn(device, hour)
            );
        }
    }
}

