import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrderTracking implements Customer {
    private ArrayList<CartItems> foodItems;
    private OrderManager orderManager;

    public OrderTracking(ArrayList<CartItems> foodItems, OrderManager orderManager) {
        this.foodItems = foodItems;
        this.orderManager = orderManager;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nOrder Tracking Menu");
            System.out.println("1 -> View Order Status");
            System.out.println("2 -> Cancel Order");
            System.out.println("3 -> View Order History");
            System.out.println("4 -> Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Order ID to view status: ");
                        int orderIdStatus = scanner.nextInt();
                        viewOrderStatus(orderIdStatus);
                        break;

                    case 2:
                        System.out.print("Enter Order ID to cancel: ");
                        int orderIdCancel = scanner.nextInt();
                        cancelOrder(orderIdCancel);
                        break;

                    case 3:
                        System.out.print("Enter your username to view order history: ");
                        String username = scanner.nextLine();
                        orderHistory(username);
                        break;

                    case 4:
                        exit = true;
                        System.out.println("Exiting Order Tracking Menu...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    public void viewOrderStatus(int orderId) {
        Order order = orderManager.getOrder(orderId);
        if (order != null) {
            System.out.println("Order ID: " + orderId);
            System.out.println("Status: " + order.getOrderStatus());
        } else {
            System.out.println("Order not found.");
        }
    }

    public void cancelOrder(int orderId) {
        Order order = orderManager.getOrder(orderId);
        if (order != null) {
            if (!order.isPrepared() && !order.isProcessed()) {
                order.setOrderStatus("Cancelled");
                System.out.println("Order ID: " + orderId + " has been cancelled.");
            } else {
                System.out.println("Order cannot be cancelled as it is already prepared or processed.");
            }
        } else {
            System.out.println("Order not found.");
        }
    }

    public void orderHistory(String username) {
        ArrayList<String> history = orderManager.getOrderHistory(username);

        if (history.isEmpty()) {
            System.out.println("No order history found for user: " + username);
            return;
        }

        System.out.println("Order History for user: " + username);
        for (String record : history) {
            System.out.println(record);
        }
    }
}
