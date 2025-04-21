import java.util.*;
import java.util.regex.*;

public class SecureLoginSystem {
    static Scanner scanner = new Scanner(System.in);
    static Map<String, User> userDatabase = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Secure Login System ===");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

   static void createAccount() {
    System.out.print("Enter username: ");
    String username = scanner.nextLine();

    if (userDatabase.containsKey(username)) {
        System.out.println("Username already exists.");
        return;
    }

    String password;
    while (true) {
        System.out.print("Enter strong password: ");
        password = scanner.nextLine();
        if (isStrongPassword(password)) break;
        else System.out.println("Password must be at least 8 characters long, contain uppercase, lowercase, digit, and special character.");
    }

    int hashedPassword = simpleHash(password); // <- your part: hash the password

    System.out.print("Set a security question (e.g., What is your pet's name?): ");
    String question = scanner.nextLine();

    System.out.print("Answer to your security question: ");
    String answer = scanner.nextLine();

    userDatabase.put(username, new User(username, String.valueOf(hashedPassword), question, answer));
    System.out.println("Account created successfully!");
}


    static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (!userDatabase.containsKey(username)) {
            System.out.println("Account does not exist.");
            return;
        }

        User user = userDatabase.get(username);

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (!user.password.equals(password)) {
            System.out.println("Incorrect password.");
            return;
        }

        System.out.println("Security Question: " + user.securityQuestion);
        System.out.print("Answer: ");
        String answer = scanner.nextLine();

        if (!user.securityAnswer.equalsIgnoreCase(answer)) {
            System.out.println("Security answer incorrect.");
            return;
        }

        System.out.println("Login successful! Welcome, " + username + ".");
    }

    static boolean isStrongPassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(pattern, password);
    }

    static class User {
        String username;
        String password;
        String securityQuestion;
        String securityAnswer;

        User(String username, String password, String securityQuestion, String securityAnswer) {
            this.username = username;
            this.password = password;
            this.securityQuestion = securityQuestion;
            this.securityAnswer = securityAnswer;
        }
    }
}
