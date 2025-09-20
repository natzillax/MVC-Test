package view;
import java.util.List;

import model.Student;
import model.Subject;

public class StudentProfileView {
    public void displayProfile(Student student, List<Subject> registeredSubjects) {
        System.out.println("\n===== [ Student Profile ] =====");
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Name: " + student.getFullName());
        System.out.println("Age: " + student.getAge());
        System.out.println("\n--- Currently Registered Subjects ---");
        if (registeredSubjects.isEmpty()) {
            System.out.println("No subjects registered yet.");
        } else {
            registeredSubjects.forEach(System.out::println);
        }
    }
}