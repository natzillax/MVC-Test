package model;
public class User {
    public enum Role { STUDENT, ADMIN }

    private String username; // คือ studentId สำหรับนักเรียน
    private String password;
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
}