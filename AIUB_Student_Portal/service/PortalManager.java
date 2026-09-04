package service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Admin;
import model.Course;
import model.Faculty;
import model.Notice;
import model.Person;
import model.Student;


public class PortalManager {
    private List<Student> students;
    private List<Faculty> facultyMembers;
    private List<Admin> administrators;
    private List<Course> defaultCourses;
    private List<Notice> notices;

    private final String studentFilePath = "students.txt";
    private final String facultyFilePath = "faculty.txt";
    private final String noticeFilePath = "notices.txt";

    public PortalManager() {
        this.students = new ArrayList<>();
        this.facultyMembers = new ArrayList<>();
        this.administrators = new ArrayList<>();
        this.defaultCourses = new ArrayList<>();
        this.notices = new ArrayList<>();

        initDefaultAdmin();
        initDefaultCourses();
        loadAllDataFromFiles();
    }

    // Default AIUB Admin Account
    private void initDefaultAdmin() {
        administrators.add(new Admin(
            "admin",
            "AIUB Registrar Admin",
            "registrar@aiub.edu",
            "+880 2 841 4046",
            "admin123",
            "Admin Building Room 102",
            "SUPER_ADMIN"
        ));
    }

    // AIUB Standard Semester Courses
    private void initDefaultCourses() {
        defaultCourses.add(new Course("CSC1101", "Introduction to Programming", 3, "A+", 4.00, "Dr. M. M. Rahman"));
        defaultCourses.add(new Course("CSC2102", "Data Structures & Algorithms", 3, "A", 3.75, "Prof. Dr. S. Ahmed"));
        defaultCourses.add(new Course("CSC2203", "Object Oriented Programming 1 (Java)", 3, "A+", 4.00, "Mr. K. Hasan"));
        defaultCourses.add(new Course("MAT1201", "Differential & Integral Calculus", 3, "A-", 3.50, "Dr. N. Sultana"));
        defaultCourses.add(new Course("ENG1101", "English Reading & Composition", 3, "B+", 3.25, "Ms. T. Kabir"));
    }

 
    public boolean addStudent(Student student) {
        if (student == null) return false;
        // Check for duplicate ID
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(student.getId())) {
                return false; // Duplicate ID found
            }
        }
        students.add(student);
        saveStudentsToFile();
        return true;
    }

    // READ (Get All Students)
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    // READ (Find Student by ID)
    public Student getStudentById(String id) {
        if (id == null) return null;
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id.trim())) {
                return s;
            }
        }
        return null;
    }

    // UPDATE (Modify existing student)
    public boolean updateStudent(Student updated) {
        if (updated == null) return false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equalsIgnoreCase(updated.getId())) {
                students.set(i, updated);
                saveStudentsToFile();
                return true;
            }
        }
        return false;
    }

    // UPDATE CGPA DIRECTLY (Used by Faculty or Admin)
    public boolean updateStudentCgpa(String studentId, double newCgpa) {
        Student s = getStudentById(studentId);
        if (s != null) {
            s.setCgpa(newCgpa);
            saveStudentsToFile();
            return true;
        }
        return false;
    }

    // DELETE (Remove student by ID)
    public boolean deleteStudent(String id) {
        if (id == null) return false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equalsIgnoreCase(id.trim())) {
                students.remove(i);
                saveStudentsToFile();
                return true;
            }
        }
        return false;
    }

    // SEARCH (By ID, Name, or Department)
    public List<Student> searchStudents(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllStudents();
        }
        String q = query.toLowerCase().trim();
        List<Student> results = new ArrayList<>();
        for (Student s : students) {
            if (s.getId().toLowerCase().contains(q) ||
                s.getName().toLowerCase().contains(q) ||
                s.getDepartment().toLowerCase().contains(q)) {
                results.add(s);
            }
        }
        return results;
    }

 
    public List<Faculty> getAllFaculty() {
        return new ArrayList<>(facultyMembers);
    }

    public Faculty getFacultyById(String id) {
        for (Faculty f : facultyMembers) {
            if (f.getId().equalsIgnoreCase(id.trim())) {
                return f;
            }
        }
        return null;
    }

    public boolean addFaculty(Faculty faculty) {
        if (faculty == null) return false;
        for (Faculty f : facultyMembers) {
            if (f.getId().equalsIgnoreCase(faculty.getId())) return false;
        }
        facultyMembers.add(faculty);
        saveFacultyToFile();
        return true;
    }
    public List<Notice> getAllNotices() {
        return new ArrayList<>(notices);
    }

    public boolean addNotice(Notice notice) {
        if (notice == null) return false;
        notices.add(0, notice); // Prepend to top of notice board
        saveNoticesToFile();
        return true;
    }

    public boolean deleteNotice(String id) {
        for (int i = 0; i < notices.size(); i++) {
            if (notices.get(i).getId().equalsIgnoreCase(id)) {
                notices.remove(i);
                saveNoticesToFile();
                return true;
            }
        }
        return false;
    }

    public List<Course> getDefaultCourses() {
        return defaultCourses;
    }

    public Person authenticate(String idOrEmail, String password) {
        if (idOrEmail == null || password == null) return null;
        String target = idOrEmail.trim();

        // 1. Check Admin
        for (Admin a : administrators) {
            if ((a.getId().equalsIgnoreCase(target) || a.getEmail().equalsIgnoreCase(target)) &&
                 a.getPassword().equals(password)) {
                return a;
            }
        }

        // 2. Check Faculty
        for (Faculty f : facultyMembers) {
            if ((f.getId().equalsIgnoreCase(target) || f.getEmail().equalsIgnoreCase(target)) &&
                 f.getPassword().equals(password)) {
                return f;
            }
        }

        // 3. Check Student
        for (Student s : students) {
            if ((s.getId().equalsIgnoreCase(target) || s.getEmail().equalsIgnoreCase(target)) &&
                 s.getPassword().equals(password)) {
                return s;
            }
        }

        return null;
    }

    public void loadAllDataFromFiles() {
        loadStudentsFromFile();
        loadFacultyFromFile();
        loadNoticesFromFile();
    }

    // Load Students from students.txt
    public void loadStudentsFromFile() {
        students.clear();
        File file = new File(studentFilePath);
        if (!file.exists()) {
            seedDefaultStudents(); // Seeds initial 2 AIUB students with CGPA!
            saveStudentsToFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                Student s = Student.fromFileString(line);
                if (s != null) {
                    students.add(s);
                }
            }
            if (students.isEmpty()) {
                seedDefaultStudents();
                saveStudentsToFile();
            }
        } catch (IOException e) {
            System.err.println("Note: Loading default students due to file read error: " + e.getMessage());
            seedDefaultStudents();
        }
    }

    // Save Students to students.txt
    public void saveStudentsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(studentFilePath))) {
            writer.println("# ===========================================================================");
            writer.println("# AIUB STUDENT PORTAL - PERSISTENT DATABASE FILE (students.txt)");
            writer.println("# Format: ID;Name;Email;Phone;Password;Department;Semester;CGPA;Credits;BloodGroup;Address");
            writer.println("# Edit or Add more students directly here or via the Java Swing Admin GUI");
            writer.println("# ===========================================================================");
            for (Student s : students) {
                writer.println(s.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving students.txt: " + e.getMessage());
        }
    }

    // Load Faculty from faculty.txt
    public void loadFacultyFromFile() {
        facultyMembers.clear();
        File file = new File(facultyFilePath);
        if (!file.exists()) {
            seedDefaultFaculty();
            saveFacultyToFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                Faculty f = Faculty.fromFileString(line);
                if (f != null) {
                    facultyMembers.add(f);
                }
            }
            if (facultyMembers.isEmpty()) {
                seedDefaultFaculty();
                saveFacultyToFile();
            }
        } catch (IOException e) {
            seedDefaultFaculty();
        }
    }

    public void saveFacultyToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(facultyFilePath))) {
            writer.println("# AIUB Faculty Database File");
            writer.println("# Format: ID;Name;Email;Phone;Password;Designation;Department;RoomNumber");
            for (Faculty f : facultyMembers) {
                writer.println(f.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving faculty.txt: " + e.getMessage());
        }
    }

    // Load Notices from notices.txt
    public void loadNoticesFromFile() {
        notices.clear();
        File file = new File(noticeFilePath);
        if (!file.exists()) {
            seedDefaultNotices();
            saveNoticesToFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//")) continue;
                Notice n = Notice.fromFileString(line);
                if (n != null) {
                    notices.add(n);
                }
            }
            if (notices.isEmpty()) {
                seedDefaultNotices();
                saveNoticesToFile();
            }
        } catch (IOException e) {
            seedDefaultNotices();
        }
    }

    public void saveNoticesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(noticeFilePath))) {
            writer.println("// AIUB Notice Board Database File");
            writer.println("// Format: ID#Title#Date#Category#Content#Author");
            for (Notice n : notices) {
                writer.println(n.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving notices.txt: " + e.getMessage());
        }
    }
    private void seedDefaultStudents() {
        // DEMO STUDENT 1 (Rafiul Qador Nafi - AIUB CSE Student)
        students.add(new Student(
            "22-48123-1",                          // Student ID
            "Rafiul Qador Nafi",                   // Full Name
            "rafiul.nafi@student.aiub.edu",        // AIUB Student Email
            "+880 1711-223344",                    // Phone
            "pass123",                             // Password
            "Computer Science & Engineering (CSE)",// Department
            5,                                     // Current Semester
            3.85,                                  // CGPA (Out of 4.00)
            68,                                    // Completed Credits
            "A+",                                  // Blood Group
            "Kuratoli, Khilkhet, Dhaka"            // Address
        ));

        // DEMO STUDENT 2 (Tasnim Ahmed - AIUB CSE Student)
        students.add(new Student(
            "23-50114-1",                          // Student ID
            "Tasnim Ahmed",                        // Full Name
            "tasnim.ahmed@student.aiub.edu",       // AIUB Student Email
            "+880 1819-556677",                    // Phone
            "pass123",                             // Password
            "Computer Science & Engineering (CSE)",// Department
            4,                                     // Current Semester
            3.72,                                  // CGPA (Out of 4.00)
            45,                                    // Completed Credits
            "O+",                                  // Blood Group
            "Bashundhara R/A, Dhaka"               // Address
        ));
    }

    private void seedDefaultFaculty() {
        facultyMembers.add(new Faculty(
            "F-1002",
            "Dr. M. M. Rahman",
            "m.rahman@aiub.edu",
            "+880 1912-334455",
            "pass123",
            "Associate Professor",
            "Computer Science & Engineering",
            "Room D-402, Building D"
        ));
        facultyMembers.add(new Faculty(
            "F-1005",
            "Engr. K. Hasan",
            "k.hasan@aiub.edu",
            "+880 1611-998877",
            "pass123",
            "Assistant Professor",
            "Computer Science & Engineering",
            "Room D-310, Building D"
        ));
    }

    private void seedDefaultNotices() {
        notices.add(new Notice(
            "NOT-01",
            "Fall 2026-2027 Mid-Term Examination Schedule Published",
            "2026-09-15",
            "Exam",
            "All undergraduate students are advised to download their Mid-term exam permits from VUES portal. Exams commence from next Sunday.",
            "Office of Controller of Examinations"
        ));
        notices.add(new Notice(
            "NOT-02",
            "Registration for AIUB CS Fest 2026 is Now Open!",
            "2026-09-10",
            "Event",
            "AIUB Computer Club invites all students to participate in Competitive Programming, Hackathon, and Robo Soccer. Register at Annex 1.",
            "AIUB Computer Club (ACC)"
        ));
        notices.add(new Notice(
            "NOT-03",
            "Course Add/Drop and Final Section Adjustment Window",
            "2026-09-05",
            "Academic",
            "Online add/drop window will remain open till 5:00 PM Thursday. Please contact your academic advisor for prerequisite clearances.",
            "Registrar Office, AIUB"
        ));
    }
}
