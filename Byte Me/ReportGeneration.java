import java.util.*;

public class ReportGeneration implements Admin {
    private ArrayList<Order> orders;
    private OrderManager orderManager;
    public ReportGeneration(OrderManager orderManager) {
        this.orders = new ArrayList<>();
        this.orderManager = orderManager;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\n REPORT GENERATION ");
            System.out.println("1 -> Daily Sales Report");
            System.out.println("2 -> Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    dailySalesReport();
                    break;
                case 2:
                    exit = true;
                    System.out.println("Exiting Report Generation...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    public void dailySalesReport() {
        orders = orderManager.getDailyOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders found for the day.");
            return;
        }

        double totalSales = 0;
        int totalOrders = orders.size();
        Map<String, Integer> itemFrequency = new HashMap<>();

        System.out.println("\nDaily Sales Report: ");
        for (Order order : orders) {
            totalSales += order.getOrderTotal();

            for (CartItems item : order.getOrderItems()) {
                String itemName = item.getName();
                itemFrequency.put(itemName, itemFrequency.getOrDefault(itemName, 0) + 1);
            }

            System.out.println("Order ID: " + order.getOrderId() +
                    ", Total: Rs" + order.getOrderTotal());
        }

        String mostPopularItem = "";
        int maxFrequency = 0;
        for (Map.Entry<String, Integer> entry : itemFrequency.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mostPopularItem = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }

        System.out.println("\nSummary:");
        System.out.println("Total Sales: Rs" + totalSales);
        System.out.println("Total Orders: " + totalOrders);
        System.out.println("Most Popular Item: " + mostPopularItem + " (ordered " + maxFrequency + " times)");
    }
}
