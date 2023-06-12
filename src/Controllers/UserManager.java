package Controllers;

import Models.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private List<String> users;
    private User currentUser; // Track the currently logged-in user


    public UserManager() {
        users = SaveData.loadUsers();
    }

    public boolean login(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();


        if (users.contains(username)) {
            currentUser = new User(username); // Create a User object for the current user
            createFiles();
            return true; // User found
        }
        return false; // User not found
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String getCurrentUsername() {
        return currentUser.getUsername();
    }

    public void createUser(Scanner scanner) {
        System.out.print("Enter a unique username: ");
        String username = scanner.nextLine();

        if (users.contains(username)) {
            System.out.println("Username already exists. Please try again.");
        } else {
            users.add(username);
            currentUser = new User(username); // Create a User object for the newly created user
            System.out.println("User created successfully!");
        }
        SaveData.saveUsers(users); // DONT FORGET THIS OTHERWISE IT WONT SAVE
    }

    public boolean userLogin(Scanner scanner) {
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("Hello! Please choose from the selection below");
            System.out.println("1. Log in");
            System.out.println("2. Create a New User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    loggedIn = login(scanner);
                    if (!loggedIn) {
                        System.out.println("User not found. Please try again.");
                    }
                    break;
                case 2:
                    createUser(scanner);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        return loggedIn;
    }



    public void createFiles () {
        String username = getCurrentUsername();
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("src/Viewer/" + username + "_calorie_intake.txt", true));
            PrintWriter writer2 = new PrintWriter(new FileWriter("src/Viewer/" + username + "_exercise.txt", true));
            PrintWriter writer3 = new PrintWriter(new FileWriter("src/Viewer/" + username + "_sleep_records.txt", true));
            System.out.println("Printed!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the exercise activity.");
            e.printStackTrace();
        }
    }

    public String getUserCalorieIntakeFile(String username) {
        return "src/calories/" + username + "_calorie_intake.txt";
    }
}


