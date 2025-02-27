import java.util.ArrayList;

public class CartItems {
    private String name;
    private double price;
    private int quantity;
    private ArrayList<CartItems> items;;

    public CartItems(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public CartItems(ArrayList<CartItems> items) {
        this.items = new ArrayList<>(items);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ArrayList<CartItems> getItems() {
        return new ArrayList<>(items);
    }
    public void addItem(CartItems item) {
        items.add(item);
        System.out.println(item + " added to the cart.");
    }

    public void viewCart() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            System.out.println("Items in cart: " + items);
        }
    }
    public void clearCart() {
        items.clear();
        System.out.println("Cart has been cleared.");
    }

    public double getTotalPrice() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return String.format("%s\t%d\tRs%.2f\tRs%.2f", name, quantity, price, getTotalPrice());
    }
}
