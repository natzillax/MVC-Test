import java.util.List;

import model.Student;
import model.Subject;
import model.UniversityDB;
import view.EnrollmentView;
import view.SubjectDetailView;

public class RegistrationController {
    private UniversityDB model;
    private EnrollmentView enrollmentView;
    private SubjectDetailView subjectDetailView;

    public RegistrationController(UniversityDB model, EnrollmentView enrollmentView, SubjectDetailView subjectDetailView) {
        this.model = model;
        this.enrollmentView = enrollmentView;
        this.subjectDetailView = subjectDetailView;
    }

    public void showEnrollmentPage(Student student) {
        List<Subject> availableSubjects = model.getUnregisteredSubjectsForStudent(student.getStudentId());
        enrollmentView.displayAvailableSubjects(student.getFullName(), availableSubjects);
    }

    public void showSubjectDetails(String subjectId) {
        Subject subject = model.findSubjectById(subjectId).orElse(null);
        subjectDetailView.displaySubjectDetails(subject);
    }
    
    public void registerSubject(String studentId, String subjectId) {
        String resultMessage = model.enrollStudent(studentId, subjectId);
        subjectDetailView.displayMessage(resultMessage); // 
    }
}