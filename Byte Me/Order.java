import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int orderId;
    private int customerId;
    private String customerName;
    private String customerAddress = "India";
    private String customerPhone = "9199119923";
    private ArrayList<CartItems> orderItems;
    private String orderStatus;
    private double orderTotal;
    private String specialRequest;
    private boolean isVIP;
    private boolean prepared;
    private boolean processed;
    private CustomerTypes customerType;
    private LocalDate orderDate;

    public Order(int orderId, ArrayList<CartItems> items, double totalPrice, LocalDate orderDate) {
        this.orderId = orderId;
        this.orderItems = items;
        this.orderTotal = totalPrice;
        this.orderDate = orderDate;
    }

    public Order(int orderId, ArrayList<CartItems> orderItems) {
        this.orderId = orderId;
        this.orderItems = new ArrayList<>(orderItems);
        this.orderStatus = "Order Recieved";
        this.prepared = false;;
        this.processed = false;
        this.specialRequest = "";
        this.isVIP = false;
    }

    public Order(int orderId, ArrayList<CartItems> items, double totalPrice) {
        this.orderId = orderId;
        this.orderItems = items;
        this.orderTotal = totalPrice;
        this.orderStatus = "Pending";
    }

    public Order(int orderId, int customerId, String customerName, String customerAddress, String customerPhone, ArrayList<CartItems> orderItems, String orderStatus, double orderTotal) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.orderTotal = orderTotal;
        this.specialRequest = "";
    }

    public Order(int orderId, String orderStatus, String specialRequest, boolean isVip) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.specialRequest = specialRequest;
        this.isVIP = isVip;
    }

    public Order(int orderId, String orderStatus, String specialRequest) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.specialRequest = specialRequest;
    }

    public Order(int orderId, int customerId, double orderTotal, List<CartItems> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderTotal = orderTotal;
        this.orderItems = new ArrayList<>(orderItems);
    }

    public LocalDate getOrderDate(){
        return orderDate;
    }

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerAddress() {
        return customerAddress;
    }
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    public String getCustomerPhone() {
        return customerPhone;
    }
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    public ArrayList<CartItems> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(ArrayList<CartItems> orderItems) {
        this.orderItems = orderItems;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public double getOrderTotal() {
        return orderTotal;
    }
    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }
    public String getSpecialRequest() {
        return specialRequest;
    }
    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }
    public boolean isVIP() {
        return isVIP;
    }
    public void setVIP(boolean isVIP) {
        this.isVIP = isVIP;
    }
    public boolean isPrepared() {
        return prepared;
    }
    public void setPrepared(boolean prepared) {
        this.prepared = prepared;
    }
    public boolean isProcessed() {
        return processed;
    }
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
    public CartItems getItem(int index) {
        if (index >= 0 && index < orderItems.size()) {
            return orderItems.get(index);
        } else {
            return null;
        }
    }
    public ArrayList<CartItems> getItems() {
        return orderItems;
    }
    public void setItems(ArrayList<CartItems> orderItems) {
        this.orderItems = orderItems;
    }
    public CustomerTypes getCustomerType() {
        return customerType;
    }
    public void setCustomerType(CustomerTypes customerType) {
        this.customerType = customerType;
    }
    @Override
    public String toString() {
        return "OrderId: " + orderId + " \n CustomerName: " + customerName + " \n CustomerAddress: " + customerAddress + " \n CustomerPhone: " + customerPhone+ " \n OrderItems: " + orderItems + " \n OrderStatus: " + orderStatus + " \n OrderTotal: " + orderTotal + " \n SpecialRequest: " + specialRequest;
    }
}
