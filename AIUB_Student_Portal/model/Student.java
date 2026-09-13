package model;


public class Student extends Person {
    // Specific attributes for AIUB Student
    private String department;
    private int semester;
    private double cgpa;
    private int completedCredits;
    private String bloodGroup;
    private String address;

    // Full Constructor
    public Student(String id, String name, String email, String phone, String password,
                   String department, int semester, double cgpa, int completedCredits,
                   String bloodGroup, String address) {
        super(id, name, email, phone, "STUDENT", password);
        this.department = department;
        this.semester = semester;
        this.cgpa = cgpa;
        this.completedCredits = completedCredits;
        this.bloodGroup = bloodGroup;
        this.address = address;
    }

    // Getters and Setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public double getCgpa() { return cgpa; }
    public void setCgpa(double cgpa) {
        if (cgpa < 0.0 || cgpa > 4.0) {
            throw new IllegalArgumentException("CGPA must be between 0.00 and 4.00");
        }
        this.cgpa = cgpa;
    }

    public int getCompletedCredits() { return completedCredits; }
    public void setCompletedCredits(int completedCredits) { this.completedCredits = completedCredits; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    // Polymorphic implementation of Person abstract methods
    @Override
    public String getRoleTitle() {
        return "Undergraduate Student (" + department + ")";
    }

    @Override
    public String getDetailsSummary() {
        return "Dept: " + department + " | Sem: " + semester + " | CGPA: " + String.format("%.2f", cgpa) + " | Credits: " + completedCredits;
    }

    @Override
    public String toFileString() {
        return getId() + ";" + getName() + ";" + getEmail() + ";" + getPhone() + ";" + getPassword() + ";"
                + department + ";" + semester + ";" + cgpa + ";" + completedCredits + ";" + bloodGroup + ";" + address;
    }

    
    public static Student fromFileString(String line) {
        if (line == null || line.trim().isEmpty() || line.startsWith("#")) return null;
        String[] parts = line.split(";");
        if (parts.length >= 11) {
            return new Student(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                parts[4].trim(),
                parts[5].trim(),
                Integer.parseInt(parts[6].trim()),
                Double.parseDouble(parts[7].trim()),
                Integer.parseInt(parts[8].trim()),
                parts[9].trim(),
                parts[10].trim()
            );
        }
        return null;
    }
}
