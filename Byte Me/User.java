import java.io.*;

record User(String username, String password) {
    User {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Username and password must not be empty.");
        }
    }

    @Override
    public String toString() {
        return username + "," + password;
    }

    // Register user
    public static void registerUser(User user, String filePath) {
        try {
            if (isUsernameTaken(user.username(), filePath)) {
                System.out.println("Username already exists. Please choose a different one.");
                return;
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
                bw.write(user.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error while registering user: " + e.getMessage());
        }
    }

    // Check if username is already taken
    public static boolean isUsernameTaken(String username, String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username)) {
                    return true; // Username exists
                }
            }
        }
        return false;
    }

    public static User authenticateUser(String username, String password, String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username) && data[1].equals(password)) {
                    return new User(data[0], data[1]);
                }
            }
        }
        return null;
    }

    public String checkUsername(String username){
        return username;
    }

    public String checkPassword(String password){
        return password;
    }

    public String getUsername(){
        return username;
    }
}
