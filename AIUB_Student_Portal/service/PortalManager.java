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

    private void initDefaultCourses() {
        defaultCourses.add(new Course("CSC1101", "Introduction to Programming", 3, "A+", 4.00, "Dr. M. M. Rahman"));
        defaultCourses.add(new Course("CSC2102", "Data Structures & Algorithms", 3, "A", 3.75, "Prof. Dr. S. Ahmed"));
        defaultCourses.add(new Course("CSC2203", "Object Oriented Programming 1 (Java)", 3, "A+", 4.00, "Mr. K. Hasan"));
        defaultCourses.add(new Course("MAT1201", "Differential & Integral Calculus", 3, "A-", 3.50, "Dr. N. Sultana"));
        defaultCourses.add(new Course("ENG1101", "English Reading & Composition", 3, "B+", 3.25, "Ms. T. Kabir"));
    }

    // --- STUDENT CRUD METHODS 
    public boolean addStudent(Student student) {
        if (student == null) return false;
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(student.getId())) {
                return false;
            }
        }
        students.add(student);
        saveStudentsToFile();
        return true;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student getStudentById(String id) {
        if (id == null) return null;
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id.trim())) {
                return s;
            }
        }
        return null;
    }

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

    public boolean updateStudentCgpa(String studentId, double newCgpa) {
        Student s = getStudentById(studentId);
        if (s != null) {
            s.setCgpa(newCgpa);
            saveStudentsToFile();
            return true;
        }
        return false;
    }

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
        notices.add(0, notice);
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
        String pass = password.trim();

        
        for (Admin a : administrators) {
            if ((a.getId().equalsIgnoreCase(target) || a.getEmail().equalsIgnoreCase(target)) &&
                 a.getPassword().equals(pass)) {
                return a;
            }
        }

        
        for (Faculty f : facultyMembers) {
            if ((f.getId().equalsIgnoreCase(target) || f.getEmail().equalsIgnoreCase(target)) &&
                 f.getPassword().equals(pass)) {
                return f;
            }
        }

        
        for (Student s : students) {
            if ((s.getId().equalsIgnoreCase(target) || s.getEmail().equalsIgnoreCase(target)) &&
                 s.getPassword() != null && s.getPassword().equals(pass)) {
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

    public void loadStudentsFromFile() {
        students.clear();
        File file = new File(studentFilePath);
        if (!file.exists() || file.length() == 0) {
            seedDefaultStudents();
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
            seedDefaultStudents();
        }
    }

    public void saveStudentsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(studentFilePath))) {
            writer.println("# AIUB STUDENT PORTAL - STUDENTS DATABASE");
            for (Student s : students) {
                writer.println(s.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving students.txt: " + e.getMessage());
        }
    }

    public void loadFacultyFromFile() {
        facultyMembers.clear();
        File file = new File(facultyFilePath);
        if (!file.exists() || file.length() == 0) {
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
            for (Faculty f : facultyMembers) {
                writer.println(f.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving faculty.txt: " + e.getMessage());
        }
    }

    public void loadNoticesFromFile() {
        notices.clear();
        File file = new File(noticeFilePath);
        if (!file.exists() || file.length() == 0) {
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
            for (Notice n : notices) {
                writer.println(n.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving notices.txt: " + e.getMessage());
        }
    }

    private void seedDefaultStudents() {
        students.add(new Student(
            "26-64541-1",
            "Rafiul Qador Nafi",
            "rafiul.nafi@student.aiub.edu",
            "+880 1711-223344",
            "pass123",
            "Computer Science & Engineering (CSE)",
            5,
            3.85,
            68,
            "A+",
            "Mirpur, Dhaka"
        ));

        students.add(new Student(
            "26-64470-1",
            "Nowshin Sadia",
            "nowshin.sadia@student.aiub.edu",
            "+880 1811-556677",
            "pass123",
            "Computer Science & Engineering (CSE)",
            5,
            3.80,
            68,
            "A+",
            "Mirpur, Dhaka"
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
    }

    private void seedDefaultNotices() {
        notices.add(new Notice(
            "NOT-01",
            "Fall 2026-2027 Mid-Term Examination Schedule Published",
            "2026-09-15",
            "Exam",
            "All undergraduate students are advised to download their Mid-term exam permits from VUES portal.",
            "Office of Controller of Examinations"
        ));
    }
}