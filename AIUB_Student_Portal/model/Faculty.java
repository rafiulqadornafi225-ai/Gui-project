package model;

/**
 * Child Class: Faculty (Teacher)
 * Extends abstract Person class.
 */
public class Faculty extends Person {
    private String designation; // e.g. "Assistant Professor", "Lecturer"
    private String department;  // e.g. "Computer Science & Engineering"
    private String roomNumber;  // e.g. "D-402, Building D"

    public Faculty(String id, String name, String email, String phone, String password,
                   String designation, String department, String roomNumber) {
        super(id, name, email, phone, "FACULTY", password);
        this.designation = designation;
        this.department = department;
        this.roomNumber = roomNumber;
    }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    @Override
    public String getRoleTitle() {
        return designation + ", Dept. of " + department;
    }

    @Override
    public String getDetailsSummary() {
        return designation + " | Room: " + roomNumber + " | AIUB " + department;
    }

    @Override
    public String toFileString() {
        return getId() + ";" + getName() + ";" + getEmail() + ";" + getPhone() + ";" + getPassword() + ";"
                + designation + ";" + department + ";" + roomNumber;
    }

    public static Faculty fromFileString(String line) {
        if (line == null || line.trim().isEmpty() || line.startsWith("#")) return null;
        String[] parts = line.split(";");
        if (parts.length >= 8) {
            return new Faculty(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                parts[4].trim(),
                parts[5].trim(),
                parts[6].trim(),
                parts[7].trim()
            );
        }
        return null;
    }
}
