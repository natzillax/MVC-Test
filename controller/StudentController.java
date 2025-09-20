package controller;

import model.UniversityDB;
import service.SessionManager;
import view.EnrollmentView;
import view.StudentProfileView;

public class StudentController {
    private UniversityDB model;
    private StudentProfileView profileView;
    private EnrollmentView enrollmentView;

    public StudentController(UniversityDB model) {
        this.model = model;
        this.profileView = new StudentProfileView();
        this.enrollmentView = new EnrollmentView();
    }

    public void showProfile() {
        String studentId = SessionManager.getCurrentUser().getUsername();
        model.findStudentById(studentId).ifPresent(student -> {
            var registeredSubjects = model.getRegisteredSubjectsForStudent(studentId);
            profileView.displayProfile(student, registeredSubjects);
        });
    }
    
    // ... methods for showing enrollment page, handling registration ...
    // เมื่อลงทะเบียนสำเร็จ ต้องกลับมาหน้านี้ (showProfile)
}