import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ItemReviews implements Customer {
    private Map<String, List<String>> reviews;

    public ItemReviews() {
        this.reviews = new HashMap<>();
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nItem Reviews Menu");
            System.out.println("1 -> Provide Review");
            System.out.println("2 -> View Reviews");
            System.out.println("3 -> Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    provideReview();
                    break;
                case 2:
                    viewReviews();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting Item Reviews Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void provideReview() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the food item you want to review: ");
        String itemName = scanner.nextLine();

        System.out.print("Enter your review: ");
        String review = scanner.nextLine();

        reviews.computeIfAbsent(itemName, k -> new ArrayList<>()).add(review);
        System.out.println("Thank you for your review!");
    }

    public void viewReviews() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the food item to view reviews: ");
        String itemName = scanner.nextLine();

        List<String> itemReviews = reviews.get(itemName);
        if (itemReviews != null && !itemReviews.isEmpty()) {
            System.out.println("Reviews for " + itemName + ":");
            for (String review : itemReviews) {
                System.out.println("- " + review);
            }
        } else {
            System.out.println("No reviews found for " + itemName);
        }
    }
}
