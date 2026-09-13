package model;

public class Course {
    private String courseCode;
    private String courseTitle;
    private int creditHours;
    private String grade;
    private double gradePoint;
    private String instructor;

    public Course(String courseCode, String courseTitle, int creditHours, String grade, double gradePoint, String instructor) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
        this.grade = grade;
        this.gradePoint = gradePoint;
        this.instructor = instructor;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }


    public String getCourseTitle() { return courseTitle; }

    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public int getCreditHours() { return creditHours; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }

    public String getGrade() { return grade; }


    public void setGrade(String grade) { this.grade = grade; }

    public double getGradePoint() { return gradePoint; }
    public void setGradePoint(double gradePoint) { this.gradePoint = gradePoint; }
    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public String toFileString() {
        return courseCode + ";" + courseTitle + ";" + creditHours + ";" + grade + ";" + gradePoint + ";" + instructor;
    }

    public static Course fromFileString(String line) {
        String[] parts = line.split(";");
        if (parts.length >= 6) {
            return new Course(
                parts[0].trim(),
                parts[1].trim(),
                Integer.parseInt(parts[2].trim()),
                parts[3].trim(),
                Double.parseDouble(parts[4].trim()),
                parts[5].trim()
            );
        }
        return null;
    }
}
