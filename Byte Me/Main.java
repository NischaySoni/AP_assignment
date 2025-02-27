import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Main {
    private static ArrayList<FoodItems> foodItems;
    private static ArrayList<CartItems> cart;
    private static OrderManager orderManager;
    private static CustomerTypes customerTypes;
    private static final String USER_FILE = "users.txt";
    private static User currentUser;

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        handleUserAuthentication(scanner);

        foodItems = new ArrayList<>();
        cart = new ArrayList<>();
        BrowseMenu browseMenu = new BrowseMenu(foodItems);
        orderManager = new OrderManager(new OrderManagement(null), currentUser);
        foodItems.add(new FoodItems("Pizza", 100, 220, 5, true, "Fast Food"));
        foodItems.add(new FoodItems("Momos", 101, 80, 3, true, "Fast Food"));
        foodItems.add(new FoodItems("Biryani", 102, 400, 2, true, "Dinner"));
        CartOperations cartOperations = new CartOperations(browseMenu, orderManager, customerTypes, currentUser, foodItems);
        OrderManagement orderManagement = new OrderManagement(orderManager);
        orderManager = new OrderManager(orderManagement, currentUser);
        customerTypes = new CustomerTypes(orderManager, cartOperations);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nWelcome to BYTE ME");
            System.out.println("1 -> Customer");
            System.out.println("2 -> Admin");
            System.out.println("3 -> Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    handleCustomer(scanner);
                    break;
                case 2:
                    handleAdmin(scanner);
                    break;
                case 3:
                    exit = true;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void handleUserAuthentication(Scanner scanner) throws IOException {
        while (true) {
            System.out.println("1 -> Register");
            System.out.println("2 -> Login");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                while (true) {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    currentUser = new User(username, password);

                    if (!User.isUsernameTaken(username, USER_FILE)) {
                        User newUser = new User(username, password);
                        User.registerUser(newUser, USER_FILE);
                        System.out.println("User registered successfully!");
                        return;
                    } else {
                        System.out.println("Username already exists. Please choose a different one.");
                    }
                }
            } else if (choice == 2) {
                while (true) {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    currentUser = new User(username, password);

                    User user = User.authenticateUser(username, password, USER_FILE);
                    if (user != null) {
                        System.out.println("Login successful! Welcome " + user.username());
                        return;
                    } else {
                        System.out.println("Login failed. Please try again.");
                    }
                }
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }
    }

    public static void handleCustomer(Scanner scanner) {
        BrowseMenu browseMenu = new BrowseMenu(foodItems);
        CartOperations cartOperations = new CartOperations(browseMenu, orderManager, customerTypes, currentUser, foodItems);
        OrderTracking orderTracking = new OrderTracking(cart, orderManager);
        ItemReviews itemReviews = new ItemReviews();
        CustomerTypes customerTypes = new CustomerTypes(orderManager, cartOperations);

        boolean exit = false;
        while (!exit) {
            System.out.println("\nCustomer Functionalities");
            System.out.println("1 -> Browse Menu");
            System.out.println("2 -> Cart Operations");
            System.out.println("3 -> Order Tracking");
            System.out.println("4 -> Item Reviews");
            System.out.println("5 -> Customer Types");
            System.out.println("6 -> Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    browseMenu.initializeGUI();
                    break;
                case 2:
                    cartOperations.displayMenu();
                    break;
                case 3:
                    orderTracking.displayMenu();
                    break;
                case 4:
                    itemReviews.displayMenu();
                    break;
                case 5:
                    customerTypes.displayMenu();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void handleAdmin(Scanner scanner) {
        MenuManagement menuManagement = new MenuManagement(foodItems);
        OrderManagement orderManagement = new OrderManagement(orderManager);
        ReportGeneration reportGeneration = new ReportGeneration(orderManager);

        boolean exit = false;
        while (!exit) {
            System.out.println("\nAdmin Functionalities");
            System.out.println("1 -> Menu Management");
            System.out.println("2 -> Order Management");
            System.out.println("3 -> Report Generation");
            System.out.println("4 -> Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    menuManagement.displayMenu();
                    break;
                case 2:
                    orderManagement.displayMenu();
                    break;
                case 3:
                    reportGeneration.displayMenu();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
