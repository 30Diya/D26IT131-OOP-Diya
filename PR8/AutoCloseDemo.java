package PR8;

class MyResource implements AutoCloseable {

    public MyResource() {
        System.out.println("Resource opened.");
    }

    public void use() {
        System.out.println("Resource is being used.");
    }

    @Override
    public void close() {
        System.out.println("Resource closed automatically.");
    }
}

public class AutoCloseDemo {

    public static void main(String[] args) {

        try (MyResource resource = new MyResource()) {

            resource.use();

            System.out.println("Throwing an exception...");
            throw new RuntimeException("Something went wrong!");

        } catch (RuntimeException e) {

            System.out.println(
                "Caught exception: " + e.getMessage()
            );
        }
    }
}