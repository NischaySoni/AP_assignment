import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class BrowseMenu {
    private ArrayList<FoodItems> foodItems;
    private JFrame frame;
    private JTextArea displayArea;

    public BrowseMenu(ArrayList<FoodItems> foodItems) {
        this.foodItems = foodItems;
    }

    public void initializeGUI() {
        // Main Frame
        frame = new JFrame("Browse Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
        JButton menuButton = new JButton("Display Menu Options");
        JButton viewButton = new JButton("View All Items");
        JButton searchButton = new JButton("Search for an Item");
        JButton filterButton = new JButton("Filter by Category");
        JButton sortButton = new JButton("Sort by Prices");

        buttonPanel.add(menuButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(sortButton);

        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Adding components to the frame
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Adding event listeners
        menuButton.addActionListener(e -> displayMenu());
        viewButton.addActionListener(e -> viewItems());
        searchButton.addActionListener(e -> searchItem());
        filterButton.addActionListener(e -> filterByCategory());
        sortButton.addActionListener(e -> sortByPrices());

        frame.setVisible(true);
    }

    public void displayMenu() {
        displayArea.setText("Main Menu Options:\n");
        displayArea.append("1. Display Menu Options\n");
        displayArea.append("2. View All Items\n");
        displayArea.append("3. Search for an Item\n");
        displayArea.append("4. Filter by Category\n");
        displayArea.append("5. Sort by Prices\n");
    }

    public void viewItems() {
        displayArea.setText("Menu Items:\n");
        for (FoodItems item : foodItems) {
            displayArea.append(item.toString() + "\n");
        }
    }

    public void searchItem() {
        String keyword = JOptionPane.showInputDialog(frame, "Enter food item to search:");
        if (keyword != null) {
            ArrayList<FoodItems> results = (ArrayList<FoodItems>) foodItems.stream()
                    .filter(item -> item.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
            if (results.isEmpty()) {
                displayArea.setText("No food items found for: " + keyword);
            } else {
                displayArea.setText("Search Results:\n");
                results.forEach(item -> displayArea.append(item.toString() + "\n"));
            }
        }
    }

    public ArrayList<FoodItems> getMenu() {
        ArrayList<FoodItems> menu = new ArrayList<>();
        for (FoodItems item : foodItems) {
            if (item.isAvailable()) {
                menu.add(item);
            }
        }
        return menu;
    }

    public void filterByCategory() {
        String category = JOptionPane.showInputDialog(frame, "Enter category to filter:");
        if (category != null) {
            ArrayList<FoodItems> results = (ArrayList<FoodItems>) foodItems.stream()
                    .filter(item -> item.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
            if (results.isEmpty()) {
                displayArea.setText("No food items found in category: " + category);
            } else {
                displayArea.setText("Filtered Items:\n");
                results.forEach(item -> displayArea.append(item.toString() + "\n"));
            }
        }
    }

    public void sortByPrices() {
        String[] options = {"Ascending", "Descending"};
        int choice = JOptionPane.showOptionDialog(frame, "Sort by price:", "Sort Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice != -1) {
            Comparator<FoodItems> comparator = Comparator.comparingDouble(FoodItems::getPrice);
            if (choice == 1) {
                comparator = comparator.reversed();
            }
            displayArea.setText("Sorted Items:\n");
            foodItems.stream()
                    .sorted(comparator)
                    .forEach(item -> displayArea.append(item.toString() + "\n"));
        }
    }
}
