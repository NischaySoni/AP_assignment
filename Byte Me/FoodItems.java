import java.util.ArrayList;

public class FoodItems {

    private String name;
    private int num;
    private double price;
    private int quantity;
    private boolean isAvailable;
    private String category;

    public FoodItems(String name,int num, double price, int quantity, boolean isAvailable, String category) {
        this.name = name;
        this.num = num;
        this.price = price;
        this.quantity = quantity;
        this.isAvailable = isAvailable;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "FoodItems: name=" + name + "\n   num=" + num + "\n   price=" + price + "\n   quantity=" + quantity + "\n   isAvailable=" + isAvailable + "\n   category=" + category;
    }
}
