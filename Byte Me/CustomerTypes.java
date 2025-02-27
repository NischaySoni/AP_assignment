import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CustomerTypes implements Customer {

    private boolean isVIP;
    private double amount;
    private final OrderManager orderManager;
    private CartOperations cartOperations;

    public CustomerTypes(OrderManager orderManager, CartOperations cartOperations) {
        this.cartOperations = cartOperations;
        this.amount = 500;
        this.isVIP = false;
        Map<Integer, Order> orders = new HashMap<>();
        this.orderManager = orderManager;
    }

    public boolean isVIP(double amount) {
        if (this.amount <= amount) {
            return true;
        };
        return false;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void VIP(int orderId) {
        Order order = orderManager.getOrder(orderId);
        if (order != null) {
            double orderAmount = order.getOrderTotal();
            if (isVIP(orderAmount)) {
                System.out.println("VIP Customer");
            } else {
                System.out.println("Not a VIP Customer");
            }
        }
        else {
            System.out.println("Order does not exist");
        }
    }

    public void regular(int orderId) {
        Order order = orderManager.getOrder(orderId);
        if (order != null) {
            double orderAmount = order.getOrderTotal();
            if (!isVIP(orderAmount)) {
                System.out.println("Regular Customer");
            }
            else {
                System.out.println("VIP Customer");
            }
        }
        else {
            System.out.println("Order does not exist");
        }
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nCustomer Types Menu");
            System.out.println("1 -> Check VIP Status");
            System.out.println("2 -> Set Purchase Amount");
            System.out.println("3 -> Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1:
                    System.out.print("Enter Order ID to view status: ");
                    int orderIdStatus = scanner.nextInt();
                    VIP(orderIdStatus);
                    break;
                case 2:
                    System.out.print("Enter new amount: ");
                    double newAmount = scanner.nextDouble();
                    setAmount(newAmount);
                    System.out.println("Amount updated. Current Status:");
                    System.out.print("Enter Order ID to view status: ");
                    int orderIdUpdate = scanner.nextInt();
                    VIP(orderIdUpdate);
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting Customer Types Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
