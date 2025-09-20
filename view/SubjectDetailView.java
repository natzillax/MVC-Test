package view;
import model.Subject;

public class SubjectDetailView {
    public void displaySubjectDetails(Subject subject) {
        if (subject == null) {
            System.out.println("This subject was not found in the system.");
            return;
        }
        System.out.println("\n---[ Subject details ]---");
        System.out.println("SubjectID " + subject.getSubjectId());
        System.out.println("SubjectName: " + subject.getSubjectName());
        System.out.println("Credits: " + subject.getCredits());
        System.out.println("TeacherName: " + subject.getTeacherName());

        String capacity = subject.getMaxCapacity() == -1 ? "Unlimited" : String.valueOf(subject.getMaxCapacity());
        System.out.println("Max: " + capacity);
        System.out.println("Number already registered: " + subject.getCurrentEnrollment());
    }
    
    public void displayMessage(String message) {
        System.out.println(">> " + message);
    }
}