package model;
public class Registration {
    private String studentId;
    private String subjectId;

    public Registration(String studentId, String subjectId) {
        this.studentId = studentId;
        this.subjectId = subjectId;
    }

    public String getStudentId() { 
        return studentId;  
    }
    
    public String getSubjectId() { 
        return subjectId; 
    }

}