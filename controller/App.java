package controller;
import java.util.Scanner;

import model.UniversityDB;
import model.User;
import service.SessionManager;

public class App {
    private static UniversityDB db = new UniversityDB();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            if (!SessionManager.isLoggedIn()) {
                showLoginMenu();
            } else {
                User.Role role = SessionManager.getCurrentUser().getRole();
                if (role == User.Role.STUDENT) {
                    showStudentMenu();
                } else if (role == User.Role.ADMIN) {
                    showAdminMenu();
                }
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("\n=== Welcome to Registration System ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        db.findUserByUsername(username).ifPresentOrElse(user -> {
            if (user.getPassword().equals(password)) {
                SessionManager.login(user);
                System.out.println("Login successful! Welcome " + user.getUsername());
            } else {
                System.out.println("Login failed. Incorrect password.");
            }
        }, () -> System.out.println("Login failed. User not found."));
    }

    private static void showStudentMenu() {
        
        System.out.println("\n--- [Student Menu] ---");
        System.out.println("1. View My Profile & Registered Subjects");
        System.out.println("2. Register for a new Subject");
        System.out.println("3. Logout");
        System.out.print("Choose an option: ");
        
        String choice = scanner.nextLine();
        String currentUserId = SessionManager.getCurrentUser().getUsername();

        switch (choice) {
            case "1":
                // จำลองการเรียก Controller
                showStudentProfile(currentUserId);
                break;
            case "2":
                // จำลองการเรียก Controller
                handleRegistration(currentUserId);
                break;
            case "3":
                SessionManager.logout();
                System.out.println("You have been logged out.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void showAdminMenu() {
        System.out.println("\n--- [Admin Menu] ---");
        System.out.println("1. Add Grade for a Student");
        System.out.println("2. Logout");
        System.out.print("Choose an option: ");
        
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                handleAddGrade();
                break;
            case "2":
                SessionManager.logout();
                System.out.println("You have been logged out.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    // Methods simulating controller actions
    private static void showStudentProfile(String studentId) {
        db.findStudentById(studentId).ifPresent(student -> {
            var subjects = db.getRegisteredSubjectsForStudent(studentId);
            // new StudentProfileView().displayProfile(student, subjects);
            System.out.println("\nShowing profile for " + student.getFullName());
            System.out.println("Registered subjects: " + subjects.size());
        });
    }

    private static void handleRegistration(String studentId) {
        System.out.println("\n--- Available Subjects for Registration ---");
        db.getUnregisteredSubjectsForStudent(studentId).forEach(System.out::println);
        System.out.print("Enter Subject ID to register: ");
        String subjectId = scanner.nextLine();
        
        String result = db.enrollStudent(studentId, subjectId);
        System.out.println(">> " + result);

        // Rule: เมื่อลงทะเบียนสำเร็จ ต้องกลับไปหน้าประวัตินักเรียน
        if (result.startsWith("Success")) {
            showStudentProfile(studentId);
        }
    }

    private static void handleAddGrade() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Subject ID: ");
        String subjectId = scanner.nextLine();
        System.out.print("Enter Grade (e.g., A, B, C): ");
        String grade = scanner.nextLine();
        
        String result = db.addGradeForStudent(studentId, subjectId, grade);
        System.out.println(">> " + result);
    }
}