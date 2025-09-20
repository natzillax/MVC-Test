package model;
public class Subject {
    private String subjectId; 
    private String subjectName;
    private int credits;    //หน่วยกิต
    private String teacherName;
    private String prerequisiteSubjectId; // รหัสวิชาบังคับก่อน 
    private int maxCapacity; 
    private int currentEnrollment;

    public Subject(String subjectId, String subjectName, int credits, String teacherName, String prerequisiteSubjectId, int maxCapacity) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credits = credits;
        this.teacherName = teacherName;
        this.prerequisiteSubjectId = prerequisiteSubjectId;
        this.maxCapacity = maxCapacity;
        this.currentEnrollment = 0; // เริ่มต้นที่ 0
    }

    public String getSubjectId() { 
        return subjectId;  
    }

    public String getSubjectName() {
         return subjectName; 
    }

    public int getCredits() { 
        return credits; 
    }

    public String getTeacherName() {
         return teacherName; 
    }

    public int getMaxCapacity() { 
        return maxCapacity; 
    }  
     
    public int getCurrentEnrollment() {
         return currentEnrollment; 
    }

    public String getPrerequisiteSubjectId() {
        return this.prerequisiteSubjectId;
    }


    public boolean isFull() {
        if (maxCapacity == -1 || maxCapacity == 0) {
            return false; 
        }
        return currentEnrollment >= maxCapacity;
    }

    public void incrementEnrollment() {
        this.currentEnrollment++;
    }

    @Override
    public String toString() {
        String capacity = maxCapacity == -1 ? "Unlimited" : String.valueOf(maxCapacity);
        return String.format("ID: %s, Name: %s, Credits: %d, Enrolled: %d/%s",
                subjectId, subjectName, credits, currentEnrollment, capacity);
    }
}