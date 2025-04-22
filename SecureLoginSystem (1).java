import java.util.*; // Import the entire utility package (for collections like HashMap)
import java.util.regex.*; // Import the regular expressions package for password validation

public class SecureLoginSystem { // Declare the class
    static Scanner scanner = new Scanner(System.in); // Create a scanner object to read user input
    static Map<String, User> userDatabase = new HashMap<>(); // Create a map to store user data (key = username, value = User object)

    public static void main(String[] args) { // Main method where the program starts
        while (true) { // Infinite loop to display the menu repeatedly
            System.out.println("\n=== Secure Login System ==="); // Print the menu title
            System.out.println("1. Create Account"); // Option to create an account
            System.out.println("2. Login"); // Option to log in to an account
            System.out.println("3. Exit"); // Option to exit the program
            System.out.print("Choose an option: "); // Prompt the user to choose an option
            int choice = scanner.nextInt(); // Read the user's choice as an integer
            scanner.nextLine(); // Consume the newline character left by nextInt()

            switch (choice) { // Switch statement to handle the user's choice
                case 1 -> createAccount(); // Call the createAccount method for option 1
                case 2 -> login(); // Call the login method for option 2
                case 3 -> { // Case 3 exits the program
                    System.out.println("Goodbye!"); // Print a goodbye message
                    return; // Exit the program
                }
                default -> System.out.println("Invalid option."); // Print error message for invalid options
            }
        }
    }

    static void createAccount() { // Method to create a new user account
        System.out.print("Enter username: "); // Prompt for the username
        String username = scanner.nextLine(); // Read the username input from the user

        if (userDatabase.containsKey(username)) { // Check if the username already exists in the database
            System.out.println("Username already exists."); // Inform the user if the username is taken
            return; // Exit the method if the username is already taken
        }

        String password; // Declare a variable to store the password
        while (true) { // Start a loop to repeatedly ask for a strong password
            System.out.print("Enter strong password: "); // Prompt for the password
            password = scanner.nextLine(); // Read the password input

            if (isStrongPassword(password)) // Check if the password is strong using regex
                break; // Exit the loop if the password is valid
            else
                System.out.println("Password must be at least 8 characters long, contain uppercase, lowercase, digit, and special character."); // Inform the user if the password is weak
        }

        int hashedPassword = simpleHash(password); // Hash the password using the simpleHash method

        System.out.print("Set a security question (e.g., What is your pet's name?): "); // Prompt for a security question
        String question = scanner.nextLine(); // Read the security question

        System.out.print("Answer to your security question: "); // Prompt for the answer to the security question
        String answer = scanner.nextLine(); // Read the answer to the security question

        // Store the user information in the userDatabase map
        userDatabase.put(username, new User(username, String.valueOf(hashedPassword), question, answer));
        System.out.println("Account created successfully!"); // Inform the user that the account has been created
    }

    static void login() { // Method for user login
        System.out.print("Enter username: "); // Prompt for the username
        String username = scanner.nextLine(); // Read the username input

        if (!userDatabase.containsKey(username)) { // Check if the username exists in the database
            System.out.println("Account does not exist."); // Inform the user if the account doesn't exist
            return; // Exit the method if the account does not exist
        }

        User user = userDatabase.get(username); // Retrieve the user object for the given username

        System.out.print("Enter password: "); // Prompt for the password
        String password = scanner.nextLine(); // Read the password input

        int hashedInput = simpleHash(password); // Hash the entered password
        if (!user.hashedPassword.equals(String.valueOf(hashedInput))) { // Check if the hashed password matches the stored hash
            System.out.println("Incorrect password."); // Inform the user if the password is incorrect
            return; // Exit the method if the password is wrong
        }

        System.out.println("Security Question: " + user.securityQuestion); // Print the security question
        System.out.print("Answer: "); // Prompt for the answer to the security question
        String answer = scanner.nextLine(); // Read the answer

        if (!user.answer.equalsIgnoreCase(answer)) { // Check if the answer matches the stored answer (case-insensitive)
            System.out.println("Security answer incorrect."); // Inform the user if the answer is incorrect
            return; // Exit the method if the answer is wrong
        }

        System.out.println("Login successful! Welcome, " + username + "."); // If login is successful, print a welcome message
    }

    static boolean isStrongPassword(String password) { // Method to check if the password is strong
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$"; // Regular expression to define a strong password
        return Pattern.matches(pattern, password); // Check if the password matches the pattern
    }

    static int simpleHash(String input) { // Method to hash the password (simple hash function)
        return input.hashCode(); // Use Java's built-in hashCode() method to hash the input string
    }

    static class User { // Inner class to represent a user
        String username; // Variable to store the username
        String hashedPassword; // Variable to store the hashed password
        String securityQuestion; // Variable to store the security question
        String answer; // Variable to store the answer to the security question

        // Constructor to initialize a User object
        User(String username, String hashedPassword, String securityQuestion, String answer) {
            this.username = username;
            this.hashedPassword = hashedPassword;
            this.securityQuestion = securityQuestion;
            this.answer = answer;
        }
    }
}