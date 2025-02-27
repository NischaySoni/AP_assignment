import java.io.*;
import java.util.ArrayList;

class FileHandler {
    private static final String DIRECTORY = "OrderHistories/";

    static {
        File dir = new File(DIRECTORY);
        if (!dir.exists() && !dir.mkdir()) {
            throw new RuntimeException("Could not create directory for order histories");
        }
    }

    public static void saveOrderHistory(String username, Order order) throws IOException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: Username cannot be null or empty");
        }
        System.out.println("Saving order for username: " + username);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DIRECTORY + username + ".txt", true))) {
            String entry = "[" + java.time.LocalDateTime.now() + "] " + order;
            bw.write(entry);
            bw.newLine();
        }
    }

    public static ArrayList<String> getOrderHistory(String username) throws IOException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: Username cannot be null or empty");
        }
        System.out.println("Retrieving order history for username: " + username);
        ArrayList<String> history = new ArrayList<>();
        File file = new File(DIRECTORY + username + ".txt");
        if (!file.exists()) {
            System.out.println("No order history found.");
            return history;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                history.add(line);
            }
        }
        return history;
    }
}
