package model;

public class Admin extends Person {
    private String officeRoom;
    private String accessLevel;

    public Admin(String id, String name, String email, String phone, String password,
                 String officeRoom, String accessLevel) {
        super(id, name, email, phone, "ADMIN", password);
        this.officeRoom = officeRoom;
        this.accessLevel = accessLevel;
    }

    public String getOfficeRoom() { return officeRoom; }
    public void setOfficeRoom(String officeRoom) { this.officeRoom = officeRoom; }

    public String getAccessLevel() { return accessLevel; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }

    @Override
    public String getRoleTitle() {
        return "System Administrator (AIUB Registrar Office)";
    }

    @Override
    public String getDetailsSummary() {
        return "AIUB Admin | Office: " + officeRoom + " | Access: " + accessLevel;
    }

    @Override
    public String toFileString() {
        return getId() + ";" + getName() + ";" + getEmail() + ";" + getPhone() + ";" + getPassword() + ";"
                + officeRoom + ";" + accessLevel;
    }
}
