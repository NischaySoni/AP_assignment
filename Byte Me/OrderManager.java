import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class OrderManager{
    private Map<Integer, Order> orders;
    private int orderCounter;
    private OrderManagement orderManagement;
    private User user;

    public OrderManager(OrderManagement orderManagement, User user) {
        orders = new HashMap<>();
        orderCounter = 0;
        this.orderManagement = orderManagement;
        this.user = user;
    }

    public String placeOrder(ArrayList<CartItems> items, String username) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Cannot place order.");
        }
        orderCounter++;
        String orderId = String.valueOf(orderCounter);
        double totalPrice = items.stream().mapToDouble(CartItems::getTotalPrice).sum();
        Order newOrder = new Order(Integer.parseInt(orderId), items, totalPrice, LocalDate.now());
        orders.put(Integer.valueOf(orderId), newOrder);
        orderManagement.addPendingOrder(newOrder);
        try {
            FileHandler.saveOrderHistory(username, newOrder);
        } catch (IOException e) {
            System.err.println("Failed to save order history: " + e.getMessage());
        }
        return orderId;
    }


    public ArrayList<String> getOrderHistory(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        try {
            return FileHandler.getOrderHistory(username);
        } catch (IOException e) {
            System.err.println("Failed to retrieve order history for user " + username + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Order getOrder(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order ID " + orderId + " not found");
        }
        return order;
    }

    public boolean isEmpty(){
        return orders.isEmpty();
    }

    public Map<Integer, Order> getOrders() {
        return orders;
    }

    public ArrayList<Order> getDailyOrders() {
        LocalDate today = LocalDate.now();
        ArrayList<Order> dailyOrders = new ArrayList<>();

        for (Order order : orders.values()) {
            if (order.getOrderDate().equals(today)) {
                dailyOrders.add(order);
            }
        }

        return dailyOrders;
    }
}
