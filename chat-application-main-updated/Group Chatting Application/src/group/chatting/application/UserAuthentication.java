package group.chatting.application;

import java.util.HashMap;
import java.util.Map;


/**
 * This class provides a simple authentication mechanism for users.
 * It uses a static map to store predefined username and password combinations.
 */
public class UserAuthentication {

    // A map to store user credentials with usernames as keys and passwords as values
    private static Map<String, String> userCredentials = new HashMap<>();

    // Static initializer block to populate the user credentials map
    static {
        // Add some sample user credentials
        userCredentials.put("user1", "password1");
        userCredentials.put("user2", "password2");
        userCredentials.put("user3", "password3");
    }

    /**
     * Authenticates a user based on the provided username and password.
     *
     * @param username The username of the user trying to authenticate.
     * @param password The password provided by the user for authentication.
     * @return true if the username exists and the password matches the stored password, false otherwise.
     */
    public static boolean authenticate(String username, String password) {
        // Retrieve the password associated with the provided username
        String storedPassword = userCredentials.get(username);

        // Check if the stored password is not null and matches the provided password
        return storedPassword != null && storedPassword.equals(password);
    }
}