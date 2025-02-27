import java.util.*;

public class MenuManagement implements Admin {


    private ArrayList<FoodItems> foodItems;
    private List<Order> orders;

    public MenuManagement(ArrayList<FoodItems> foodItems) {
        this.foodItems = foodItems;
        this.orders = new ArrayList<>();
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\n MENU MANAGEMENT ");
            System.out.println("1 -> Add Item");
            System.out.println("2 -> Update Item");
            System.out.println("3 -> Remove Item");
            System.out.println("4 -> Modify Price");
            System.out.println("5 -> Update Availability");
            System.out.println("6 -> Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addNewItem(scanner);
                    break;
                case 2:
                    updateItem(scanner);
                    break;
                case 3:
                    removeItem(scanner);
                    break;
                case 4:
                    modifyPrice(scanner);
                    break;
                case 5:
                    updateAvailability(scanner);
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting Menu Management...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    public void addNewItem(Scanner scanner) {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item number: ");
        int num = scanner.nextInt();
        if (num <= 0) {
            System.out.println("Item number must be positive.");
            return;
        }
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        if (price < 0) {
            System.out.println("Price must be non-negative.");
            return;
        }
        scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        FoodItems newItem = new FoodItems(name, num, price, quantity, true, category);
        foodItems.add(newItem);
        System.out.println(name + " added to menu.");
    }

    public void updateItem(Scanner scanner) {
        System.out.print("Enter item number to update: ");
        int num = scanner.nextInt();
        scanner.nextLine();
        FoodItems item = findItemByNumber(num);
        if (item != null) {
            System.out.print("Enter new name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new price: ");
            double price = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter new category: ");
            String category = scanner.nextLine();
            System.out.print("Enter new quantity: ");
            int quantity = scanner.nextInt();
            item.setName(name);
            item.setPrice(price);
            item.setCategory(category);
            item.setQuantity(quantity);
            System.out.println(name + " updated successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public void removeItem(Scanner scanner) {
        System.out.print("Enter item number to remove: ");
        int num = scanner.nextInt();
        FoodItems item = findItemByNumber(num);
        if (item != null) {
            foodItems.remove(item);
            updateOrders(num);
            System.out.println(item.getName() + " removed from menu.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public void modifyPrice(Scanner scanner) {
        System.out.print("Enter item number to modify price: ");
        int num = scanner.nextInt();
        FoodItems item = findItemByNumber(num);
        if (item != null) {
            System.out.print("Enter new price: ");
            double price = scanner.nextDouble();
            item.setPrice(price);
            System.out.println("Price updated.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public void updateAvailability(Scanner scanner) {
        System.out.print("Enter item number to update availability: ");
        int num = scanner.nextInt();
        FoodItems item = findItemByNumber(num);
        if (item != null) {
            System.out.print("Enter availability (true/false): ");
            boolean availability = scanner.nextBoolean();
            item.setAvailable(availability);
            System.out.println("Availability updated.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private FoodItems findItemByNumber(int num) {
        for (FoodItems item : foodItems) {
            if (item.getNum() == num) {
                return item;
            }
        }
        return null;
    }

    public void updateOrders(int itemNum) {
        for (Order order : orders) {
            if (order.getOrderItems().contains(itemNum)) {
                order.setOrderStatus("Denied");
            }
        }
    }
}
