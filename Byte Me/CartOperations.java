import java.util.ArrayList;
import java.util.Scanner;

public class CartOperations implements Customer {
    private ArrayList<CartItems> cart;
    private Scanner scanner;
    private BrowseMenu browseMenu;
    private OrderManager orderManager;
    private CustomerTypes customerTypes;
    private ArrayList<FoodItems> foodItems;
    private User user;

    public CartOperations(BrowseMenu browseMenu, OrderManager orderManager, CustomerTypes customerTypes, User user, ArrayList<FoodItems> foodItems) {
        this.cart = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.browseMenu = browseMenu;
        this.orderManager = orderManager;
        this.customerTypes = customerTypes;
        this.user = user;
        this.foodItems = foodItems;
    }

    public void displayMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n CART OPERATIONS MENU ");
            System.out.println("1 -> Add Items to Cart");
            System.out.println("2 -> Modify Item Quantities");
            System.out.println("3 -> Remove Item from Cart");
            System.out.println("4 -> View Total Price");
            System.out.println("5 -> Checkout");
            System.out.println("6 -> Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addItems();
                    break;
                case 2:
                    modifyQuantities();
                    break;
                case 3:
                    removeItems();
                    break;
                case 4:
                    viewTotal();
                    break;
                case 5:
                    checkoutProcess();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting Cart Operations Menu.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    public short outOfStock(String itemName, int quantity) {
        FoodItems selectedFoodItem = null;
        for (FoodItems item : foodItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                selectedFoodItem = item;
                break;
            }
        }
        if (selectedFoodItem == null) {
            System.out.println("Item not found in the food items list.");
            return 0;
        }
        if (quantity > selectedFoodItem.getQuantity()) {
            return -1;
        }
        return 0;
    }

    public void addItems() {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }

        FoodItems selectedFoodItem = null;
        for (FoodItems item : foodItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                selectedFoodItem = item;
                break;
            }
        }

        if (selectedFoodItem == null) {
            System.out.println("Item not found in the food items list.");
            return;
        }

        if (quantity > selectedFoodItem.getQuantity()) {
            System.out.println("Insufficient stock. Available quantity: " + selectedFoodItem.getQuantity());
            return;
        }

        double price = selectedFoodItem.getPrice();
        boolean itemExists = false;

        for (CartItems cartItem : cart) {
            if (cartItem.getName().equalsIgnoreCase(itemName)) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            cart.add(new CartItems(itemName, price, quantity));
        }

        // Reduce the quantity in stock
        selectedFoodItem.setQuantity(selectedFoodItem.getQuantity() - quantity);

        System.out.println(quantity + " " + itemName + "(s) added to cart.");
        System.out.println("Remaining stock of " + itemName + ": " + selectedFoodItem.getQuantity());
    }


    public void modifyQuantities() {
        System.out.print("Enter item name to modify: ");
        String itemName = scanner.nextLine();
        for (CartItems item : cart) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                System.out.print("Enter new quantity: ");
                int newQuantity = scanner.nextInt();
                scanner.nextLine();

                if (newQuantity <= 0) {
                    System.out.println("Quantity must be greater than zero.");
                    return;
                }

                item.setQuantity(newQuantity);
                System.out.println("Quantity updated for " + itemName + ".");
                return;
            }
        }
        System.out.println("Item not found in cart.");
    }

    public void removeItems() {
        System.out.print("Enter item name to remove: ");
        String itemName = scanner.nextLine();
        boolean removed = cart.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
        if (removed) {
            System.out.println("Removed " + itemName + " from cart.");
        } else {
            System.out.println("Item not found in cart.");
        }
    }

    public void viewTotal() {
        double total = 0;
        System.out.printf("%-15s %-10s %-10s %-10s%n", "Item", "Quantity", "Unit Price", "Total Price");
        for (CartItems item : cart) {
            double itemTotal = item.getPrice() * item.getQuantity();
            total += itemTotal;
            System.out.printf("%-15s %-10d Rs%.2f   Rs%.2f%n", item.getName(), item.getQuantity(), item.getPrice(), itemTotal);
        }
        System.out.printf("Total Price: Rs%.2f%n", total);
    }

    public void checkoutProcess() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Cannot proceed to checkout.");
            return;
        }

        ArrayList<CartItems> items = new ArrayList<>(cart);

        double total = totalAmount();
        String username = user.username();
        String orderId = orderManager.placeOrder(items, username);


        System.out.println("Order ID: " + orderId);
        System.out.println("Total Price: Rs" + total);
        System.out.print("Proceed to checkout? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        customerTypes.setAmount(total);
        if ("yes".equals(confirmation)) {
            cart.clear();
            System.out.println("Checkout completed. Thank you for your order!");

            if (total >= 500) {
                System.out.println("Congratulations! You are now a VIP customer.");
            } else {
                System.out.println("Current customer status remains unchanged.");
            }
        } else {
            System.out.println("Checkout cancelled.");
        }
    }

    public double totalAmount(){
        double total = 0;
        for (CartItems item : cart) {
            double itemTotal = item.getPrice() * item.getQuantity();
            total += itemTotal;
        }
        return total;
    }

    private double getItemPrice(String itemName) {
        for (FoodItems item : browseMenu.getMenu()) {
            if (item.getName().equalsIgnoreCase(itemName) && item.isAvailable()) {
                return item.getPrice();
            }
        }
        return -1;
    }
}
