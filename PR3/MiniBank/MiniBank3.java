package PR3.MiniBank;
public class MiniBank3 {
    public static void main(String[] args) {
        Account[] accounts = new Account[3];

        accounts[0] = new Account("Rahul", 5000);
        accounts[1] = new Account("Priya", 3000);
        accounts[2] = new Account("Amit", 10000);

        accounts[0].deposit(2000);
        accounts[1].withdraw(500);
        accounts[2].deposit(1000);
        for (Account account : accounts) {
            System.out.println(account);
        }
        System.out.println("\nAre account 1 and account 2 equal? "
                + accounts[0].equals(accounts[1]));
        System.out.println("Is account[0] an Account? "
                + (accounts[0] instanceof Account));
        Customer customer = new Customer(
                "Rahul",
                "rahul@gmail.com",
                "9876543210"
        );
        Customer.Address address =
                new Customer.Address(
                        "123 Main Road",
                        "Ahmedabad",
                        "380001"
                );
        customer.setAddress(address);
        System.out.println("\nCustomer: " + customer.getName());
        System.out.println("City: " + customer.getAddress().getCity());
        System.out.println("Pincode: " + customer.getAddress().getPincode());
        Customer copy = customer.clone();
        System.out.println("Cloned Customer: " + copy.getName());
    }
}
