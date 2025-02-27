import javax.swing.*;
import java.awt.*;
import java.util.*;

public class OrderManagement implements Admin {
    private Map<Integer, Order> orderMap;
    private PriorityQueue<Order> pendingOrders;
    private OrderManager orderManager;

    public OrderManagement(OrderManager orderManager) {
        this.orderMap = new HashMap<>();
        this.pendingOrders = new PriorityQueue<>(Comparator.comparing(Order::isVIP).reversed());
        this.orderManager = orderManager;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\nORDER MANAGEMENT");
            System.out.println("1 -> View Pending Orders");
            System.out.println("2 -> Update Order Status");
            System.out.println("3 -> Process Refund");
            System.out.println("4 -> Handle Special Requests");
            System.out.println("5 -> Order Priority");
            System.out.println("6 -> Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    viewPendingOrders();
                    break;
                case 2:
                    System.out.print("Enter order ID to update: ");
                    int updateOrderId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new status (Pending, Done, Refunded): ");
                    String status = scanner.nextLine();
                    updateOrderStatus(updateOrderId, status);
                    break;
                case 3:
                    System.out.print("Enter order ID to process refund: ");
                    int refundOrderId = scanner.nextInt();
                    processRefund(refundOrderId);
                    break;
                case 4:
                    System.out.print("Enter order ID for request: ");
                    int requestOrderId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter request details: ");
                    String request = scanner.nextLine();
                    handleSpecialRequest(requestOrderId, request);
                    break;
                case 5:
                    orderPriority();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting Order Management.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    public void addPendingOrder(Order order) {
        if ("Pending".equalsIgnoreCase(order.getOrderStatus())) {
            pendingOrders.add(order);
        }
    }

    public void viewPendingOrders() {
        JFrame frame = new JFrame("Pending Orders");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        if (pendingOrders.isEmpty()) {
            textArea.append("No pending orders.");
        } else {
            for (Order order : pendingOrders) {
                textArea.append(order.toString() + "\n");
            }
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void updateOrderStatus(int orderId, String status) {
        Order order = orderManager.getOrder(orderId);
        if (order != null) {
            order.setOrderStatus(status);
            if ("Pending".equalsIgnoreCase(status)) {
                pendingOrders.add(order);
            } else {
                pendingOrders.remove(order);
            }
            System.out.println("Order " + orderId + " updated to " + status);
        } else {
            System.out.println("Order " + orderId + " not found.");
        }
    }

    public void processRefund(int orderId) {
        Order order = orderManager.getOrder(orderId);
        if (order != null && "Done".equalsIgnoreCase(order.getOrderStatus())) {
            order.setOrderStatus("Refunded");
            pendingOrders.remove(order);
            System.out.println("Order " + orderId + " has been refunded.");
        } else {
            System.out.println("Order " + orderId + " not found or not eligible for refund.");
        }
    }

    public void handleSpecialRequest(int orderId, String request) {
        Order order = orderManager.getOrder(orderId);
        if (order != null) {
            order.setSpecialRequest("Special Request for Order " + orderId + " -> " + request);
            System.out.println("Request added for Order " + orderId);
        } else {
            System.out.println("Order " + orderId + " not found.");
        }
    }

    public void orderPriority() {
        System.out.println("Orders sorted by priority (VIP first):");
        while (!pendingOrders.isEmpty()) {
            Order order = pendingOrders.poll();
            System.out.println(order);
        }
        System.out.println("All prioritized orders have been processed.");
    }
}
