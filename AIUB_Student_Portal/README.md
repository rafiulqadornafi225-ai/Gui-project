# 🎓 AIUB Student & Faculty Portal System (Java Swing OOP1 Project)
### American International University - Bangladesh (AIUB)
**Course:** Object Oriented Programming 1 (Java) — Final Lab Project

---

## 🏛️ Project Structure (Matches Teacher's Package Architecture)

```
AIUB_Portal/
├── 📁 gui/
│   ├── ☕ LoginGUI.java         # AIUB Multi-Role Login (Student, Faculty, Admin)
│   ├── ☕ StudentGUI.java       # Student Portal (Profile, CGPA, Courses, ID Card)
│   ├── ☕ FacultyGUI.java       # Faculty Portal (Grade Submission, Post Notices)
│   ├── ☕ AdminGUI.java         # Admin Portal (Full CRUD Student & Notice Manager)
│   └── ☕ ImageHelper.java      # Crash-free AIUB Logo & graphics generator
├── 📁 model/
│   ├── ☕ Person.java           # Abstract Base Class (Encapsulation & Abstraction)
│   ├── ☕ Student.java          # Child Class (Inheritance & Polymorphism)
│   ├── ☕ Faculty.java          # Child Class (Teacher details & role methods)
│   ├── ☕ Admin.java            # Child Class (Administrator privileges)
│   ├── ☕ Course.java           # Academic Course Data Model
│   └── ☕ Notice.java           # Notice Board Model
├── 📁 service/
│   └── ☕ PortalManager.java    # CRUD Operations & File I/O (students.txt, notices.txt)
├── 📄 students.txt              # Persistent storage with 2 initial students & CGPA
├── 📄 faculty.txt               # Faculty database file
├── 📄 notices.txt               # Notice bulletin file
├── ☕ Start.java                # Main entry point (SwingUtilities.invokeLater)
├── ⚡ run.bat                   # Windows 1-Click Batch Runner
├── ⚡ run.sh                    # Linux / Mac Shell Script
└── 📁 .vscode/launch.json       # VS Code F5 Run/Debug Config
```

---

## 🏆 Four Core OOP Pillars Demonstrated (Defense Guide)

### 1. Encapsulation (এনক্যাপসুলেশন):
- All fields in `Person.java`, `Student.java`, and `Faculty.java` are declared `private`.
- Controlled access via `public` getters and setters with validation (e.g. `setCgpa(double cgpa)` validates range 0.00 - 4.00).

### 2. Inheritance (ইনহেরিটেন্স):
- `Student extends Person`
- `Faculty extends Person`
- `Admin extends Person`
- Child classes call `super(id, name, email, phone, role, password)`.

### 3. Abstraction (অ্যাবস্ট্রাকশন):
- `public abstract class Person` prevents instantiation of incomplete person entities.
- Abstract methods: `getRoleTitle()`, `getDetailsSummary()`, and `toFileString()`.

### 4. Polymorphism (পলিমরফিজম):
- Method overriding (`@Override`) across `Student`, `Faculty`, and `Admin`.
- Dynamic method dispatch via `Person` reference in `PortalManager`.

---

## ⚡ How to Run in VS Code:
1. Open VS Code and open this folder (**File -> Open Folder...**).
2. Open `Start.java`.
3. Press **F5** or click the **▷ Run** button in the top right.
4. Or in terminal:
   ```bash
   javac -d bin model/*.java service/*.java gui/*.java Start.java ; java -cp bin Start
   ```
