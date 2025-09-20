package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UniversityDB {
    private List<Student> students = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();
    private List<Registration> registrations = new ArrayList<>();
    private List<CompletedCourse> completedCourses = new ArrayList<>();

    public UniversityDB() {
        // สร้างข้อมูลนักเรียนและวิชาจำลอง
        initializeData();
    }

    public String enrollStudent(String studentId, String subjectId) {
        Student student = findStudentById(studentId).orElse(null);
        Subject subject = findSubjectById(subjectId).orElse(null);

        if (student == null || subject == null) {
            return "Error: Invalid student or subject ID.";
        }

        if (student.getAge() < 15) {
            return "Error: Student must be at least 15 years old to register.";
        }

        if (subject.isFull()) {
            return "Error: The subject '" + subject.getSubjectName() + "' is full.";
        }

        // ตรวจสอบวิชาบังคับก่อน
        if (subject.getPrerequisiteSubjectId() != null) {
            boolean hasPassedPrerequisite = completedCourses.stream()
                    .anyMatch(c -> c.getStudentId().equals(studentId)
                            && c.getSubjectId().equals(subject.getPrerequisiteSubjectId())
                            && !c.getGrade().equalsIgnoreCase("F")); // ตรวจสอบว่าเกรดไม่เป็น F
            if (!hasPassedPrerequisite) {
                Subject prereqSubject = findSubjectById(subject.getPrerequisiteSubjectId()).orElse(null);
                String prereqName = (prereqSubject != null) ? prereqSubject.getSubjectName() : "Unknown";
                return "Error: You must complete the prerequisite subject '" + prereqName + "' first.";
            }
        }

        // ตรวจสอบการลงทะเบียนซ้ำ
        boolean alreadyEnrolled = registrations.stream()
                .anyMatch(r -> r.getStudentId().equals(studentId) && r.getSubjectId().equals(subjectId));
        if (alreadyEnrolled) {
            return "Error: You have already enrolled in this subject.";
        }

        // ตรวจสอบการลงทะเบียนในกรณีมีจำนวนคนสูงสุด
        if (subject.getMaxCapacity() != -1) {
            if (subject.isFull()) {
                return "Error: The subject '" + subject.getSubjectName() + "' is full.";
            }
        }

        subject.incrementEnrollment();
        registrations.add(new Registration(studentId, subjectId));
        return "Success! Enrolled in '" + subject.getSubjectName() + "'.";
    }

    public String addGradeForStudent(String studentId, String subjectId, String grade) {
        if (findStudentById(studentId).isEmpty() || findSubjectById(subjectId).isEmpty()) {
            return "Error: Invalid student or subject ID.";
        }
        // ลบข้อมูลเก่าถ้ามี แล้วเพิ่มใหม่ (เผื่อกรอกผิด)
        completedCourses.removeIf(c -> c.getStudentId().equals(studentId) && c.getSubjectId().equals(subjectId));
        completedCourses.add(new CompletedCourse(studentId, subjectId, grade));
        return "Successfully added grade '" + grade + "' for student " + studentId + " in subject " + subjectId;
    }

    public Optional<User> findUserByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    public Optional<Student> findStudentById(String studentId) {
        return students.stream().filter(s -> s.getStudentId().equals(studentId)).findFirst();
    }

    public Optional<Subject> findSubjectById(String subjectId) {
        return subjects.stream()
                .filter(s -> s.getSubjectId().equals(subjectId))
                .findFirst();
    }

    public List<Subject> getRegisteredSubjectsForStudent(String studentId) {
        List<String> registeredIds = registrations.stream()
                .filter(r -> r.getStudentId().equals(studentId))
                .map(Registration::getSubjectId).collect(Collectors.toList());
        return subjects.stream().filter(s -> registeredIds.contains(s.getSubjectId())).collect(Collectors.toList());
    }

    public List<Subject> getUnregisteredSubjectsForStudent(String studentId) {
        // 1. ค้นหารหัสวิชาทั้งหมดที่นักเรียนคนนี้ลงทะเบียนไปแล้ว
        List<String> registeredSubjectIds = registrations.stream()
                .filter(r -> r.getStudentId().equals(studentId))
                .map(Registration::getSubjectId)
                .collect(Collectors.toList());

        // 2. คืนค่ารายวิชาทั้งหมดที่ "ไม่มี" อยู่ในลิสต์ข้างบน
        return subjects.stream()
                .filter(s -> !registeredSubjectIds.contains(s.getSubjectId()))
                .collect(Collectors.toList());
    }

    private void initializeData() {

        for (int i = 1; i <= 10; i++) {
            String id = "690100" + String.format("%02d", i);
            users.add(new User(id, "pass" + i, User.Role.STUDENT));
        }
        users.add(new User("admin", "admin1234", User.Role.ADMIN));

        // Students
        students.add(new Student("69010001", "MR.", "Somchai", "XYZ", LocalDate.of(2005, 5, 10), "ABC School",
                "somchai@email.com"));
        students.add(new Student("69010002", "MRS.", "Somying", "ABC", LocalDate.of(2005, 8, 22), "QRS School",
                "somying@email.com"));
        students.add(new Student("69010003", "MR.", "Somsak", "A", LocalDate.of(2005, 3, 3), "DEF School", "c@c.com"));
        students.add(
                new Student("69010010", "MRS.", "Somwang", "Brook", LocalDate.of(2006, 9, 3), "HW School", "d@c.com"));

        // Subjects
        subjects.add(new Subject("05501101", "Intro to Programming", 3, "T.Java", null, 50));
        subjects.add(new Subject("05501102", "Data Structures", 3, "T.Algo", "05501101", 40)); // <-- มี Prerequisite
        subjects.add(new Subject("05502201", "Web Development", 3, "T.Web", "05501101", 30)); // <-- มี Prerequisite
        subjects.add(new Subject("05502301", "Database Systems", 3, "T.SQL", null, 40));
        subjects.add(new Subject("05503401", "Operating Systems", 3, "T.OS", "05501102", 25)); // <-- มี Prerequisite
        subjects.add(new Subject("90691011", "English for Communication", 3, "T.Adam", null, -1));
        subjects.add(new Subject("90691012", "Thai Language Usage", 3, "T.Somsri", null, 100));
        subjects.add(new Subject("90692022", "Critical Thinking", 3, "T.Think", null, 1)); // วิชาที่เต็มเร็ว
        subjects.add(new Subject("90693033", "Art Appreciation", 3, "T.Art", null, 80));
        subjects.add(new Subject("90694044", "Environmental Science", 3, "T.Save", null, 60));

        // // Pre-fill some registrations to test capacity
        // enrollStudent("69010002", "05501101");
        // enrollStudent("69010002", "90692022");

        // Pre-completed courses for testing prerequisite
        // ให้นักเรียนคนที่ 2 (ขวัญใจ) เรียน Intro to Programming ผ่านแล้วด้วยเกรด A
        completedCourses.add(new CompletedCourse("69010002", "05501101", "A"));

        // Pre-fill a full course
        registrations.add(new Registration("69010003", "90692022"));
    }

}