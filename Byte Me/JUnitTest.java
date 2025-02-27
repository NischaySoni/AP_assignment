import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitTest {

    private CartOperations cartOperations;
    private ArrayList<FoodItems> foodItems;
    private ArrayList<CartItems> cart;
    private User user;
    private static final String USER_FILE = "users.txt";

    @BeforeEach
    void setUp() {
        foodItems = new ArrayList<>();
        foodItems.add(new FoodItems("Pizza", 100, 220, 10, true, "Fast Food"));
        foodItems.add(new FoodItems("Momos", 101, 80, 5, true, "Fast Food"));
        foodItems.add(new FoodItems("Biryani", 102, 400, 0, true, "Dinner")); // Out of stock
        user = new User("testUser", "password");
        cart = new ArrayList<>();
        cartOperations = new CartOperations(null, null, null, user, foodItems);
    }

    @Test
    void orderOutOfStockTest1() throws IOException {
        assertEquals(-1, cartOperations.outOfStock("Pizza", 12), "the quantity you provided is not available in the stock");
    }

    @Test
    void orderOutOfStockTest2() throws IOException {
        assertNotEquals(0, cartOperations.outOfStock("Pizza", 12), "the quantity you provided is not available in the stock");
    }

    @Test
    void orderInStockTest1() throws IOException {
        assertEquals(0, cartOperations.outOfStock("Pizza", 8), "the quantity you provided is available in the stock");
    }

    @Test
    void orderInStockTest2() throws IOException {
        assertNotEquals(-1, cartOperations.outOfStock("Pizza", 8), "the quantity you provided is available in the stock");
    }

    @Test
    void invalidUsername() throws IOException {
        String username = "Java";
        assertNotEquals("JavaScript", user.checkUsername(username), "please enter a valid username");
    }

    @Test
    void invalidPassword() throws IOException {
        String password = "class";
        assertNotEquals("html", user.checkPassword(password), "please enter a valid password");
    }
}
