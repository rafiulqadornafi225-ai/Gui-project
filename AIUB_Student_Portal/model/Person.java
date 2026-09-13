package model;

public abstract class Person {
    // Encapsulation: Private member variables
    private String id;
    private String name;
    private String email;
    private String phone;
    private String role; 
    private String password;

    // Parameterized Constructor
    public Person(String id, String name, String email, String phone, String role, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.password = password;
    }
    public String getId() { return id; }
    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty!");
        }
        this.id = id.trim();
    }

    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty!");
        }
        this.name = name.trim();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public abstract String getRoleTitle();
    public abstract String getDetailsSummary();
    public abstract String toFileString();

    @Override
    public String toString() {
        return "[" + role + "] " + id + " - " + name + " (" + email + ")";
    }
}
