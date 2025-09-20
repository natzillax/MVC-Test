package view;
import java.util.List;

import model.Subject;

public class EnrollmentView {
    public void displayAvailableSubjects(String studentName, List<Subject> subjects) {
        System.out.println("  Courses open for registration " + studentName);
        if (subjects.isEmpty()) {
            System.out.println("You have registered for all subjects.");
        } else {
            for (Subject subject : subjects) {
                String capacity = subject.getMaxCapacity() == -1 ? "Unlimited" : String.valueOf(subject.getMaxCapacity());
                System.out.printf("ID: %s | SubjectName: %-30s | Credits: %d | CurrentEnrollment: %d/%s\n",
                        subject.getSubjectId(),
                        subject.getSubjectName(),
                        subject.getCredits(),
                        subject.getCurrentEnrollment(),
                        capacity);
            }
        }
    }
}