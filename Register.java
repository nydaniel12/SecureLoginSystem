import java.util.HashMap;
import java.util.Scanner;

public class Register {

    static Scanner scanner = new Scanner(System.in);
    static HashMap<String, User> userDatabase = new HashMap<>();

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

        int hashedPassword = simpleHash(password);

        System.out.print("Set a security question (e.g., What is your pet's name?): ");
        String question = scanner.nextLine();

        System.out.print("Answer to your security question: ");
        String answer = scanner.nextLine();

        userDatabase.put(username, new User(username, String.valueOf(hashedPassword), question, answer));
        System.out.println("Account created successfully!");
    }

    static boolean isStrongPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    }

    static int simpleHash(String input) {
        return input.hashCode();
    }

    static class User {
        String username;
        String hashedPassword;
        String securityQuestion;
        String answer;

        public User(String username, String hashedPassword, String securityQuestion, String answer) {
            this.username = username;
            this.hashedPassword = hashedPassword;
            this.securityQuestion = securityQuestion;
            this.answer = answer;
        }
    }
}
