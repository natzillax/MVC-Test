package model;
import java.time.LocalDate;
import java.time.Period;

public class Student {
    private String studentId; 
    private String prefix;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String currentSchool;
    private String contactEmail;
    private int getAge;

    public Student(String studentId, String prefix, String firstName, String lastName, LocalDate dateOfBirth, String currentSchool, String contactEmail) {
        this.studentId = studentId;
        this.prefix = prefix;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.currentSchool = currentSchool;
        this.contactEmail = contactEmail;
    }

    public String getStudentId() {
         return studentId;
    }

    public String getFullName() { 
        return prefix + firstName + " " + lastName; 
    }

    public int getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + ", Name: " + getFullName();
    }
}