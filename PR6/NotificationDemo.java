package PR6;


@FunctionalInterface
interface Notifier {
    void send(String message);
}

// Marker interface
interface Urgent {
}

class UrgentNotifier implements Notifier, Urgent {

    private Notifier notifier;

    UrgentNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    public void send(String message) {
        notifier.send(message);
    }
}

public class NotificationDemo {

    public static void main(String[] args) {

        // Email sender using lambda
        Notifier email = message ->
                System.out.println("Email: " + message);

        // SMS sender using lambda
        Notifier sms = message ->
                System.out.println("SMS: " + message);

        // Urgent email sender
        Notifier urgentEmail = new UrgentNotifier(
                message ->
                        System.out.println("Urgent Email: " + message)
        );

        Notifier[] senders = {
            email,
            sms,
            urgentEmail
        };

        String message = "Lab practical starts at 2 PM.";

        // Normal broadcast
        System.out.println("Normal notification:");

        for (Notifier sender : senders) {
            sender.send(message);
        }

        // Urgent sender sends twice
        System.out.println("\nUrgent notification:");

        for (Notifier sender : senders) {

            if (sender instanceof Urgent) {
                sender.send(message);
                sender.send(message);
            }
        }
    }
}

