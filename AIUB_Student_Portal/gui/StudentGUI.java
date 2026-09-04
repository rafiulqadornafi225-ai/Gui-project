package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Course;
import model.Notice;
import model.Student;
import service.PortalManager;


public class StudentGUI extends JFrame {
    private PortalManager portalManager;
    private Student currentStudent;

    public StudentGUI(PortalManager portalManager, Student student) {
        this.portalManager = portalManager;
        this.currentStudent = student != null ? student : portalManager.getAllStudents().get(0);
        initComponents();
    }

    private void initComponents() {
        setTitle("AIUB Student Portal - " + currentStudent.getName() + " (" + currentStudent.getId() + ")");
        setSize(980, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());

        // 1. TOP BANNER WITH AIUB LOGO & USER INFO
        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(new Color(13, 37, 71)); // AIUB Deep Navy
        banner.setPreferredSize(new Dimension(980, 75));
        banner.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        
        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        brandPanel.setOpaque(false);
        JLabel lblLogo = new JLabel(ImageHelper.getAIUBLogo(50, 50));
        brandPanel.add(lblLogo);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        JLabel lblTitle = new JLabel("AMERICAN INTERNATIONAL UNIVERSITY-BANGLADESH");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(Color.WHITE);
        JLabel lblSub = new JLabel("Student Portal — " + currentStudent.getDepartment());
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(244, 208, 111)); // AIUB Gold
        titlePanel.add(lblTitle);
        titlePanel.add(lblSub);
        brandPanel.add(titlePanel);

        banner.add(brandPanel, BorderLayout.WEST);

       
        JPanel userBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        userBox.setOpaque(false);
        JLabel lblUser = new JLabel("Logged in: " + currentStudent.getName() + " | CGPA: " + String.format("%.2f", currentStudent.getCgpa()));
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUser.setForeground(new Color(225, 238, 255));

        JButton btnLogout = new JButton("Logout") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(220, 38, 38));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnLogout.setBackground(new Color(220, 38, 38));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnLogout.setFocusPainted(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setOpaque(true);
        btnLogout.setBorder(BorderFactory.createLineBorder(new Color(185, 28, 28)));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> {
            new LoginGUI().setVisible(true);
            this.dispose();
        });

        userBox.add(lblUser);
        userBox.add(btnLogout);
        banner.add(userBox, BorderLayout.EAST);

        root.add(banner, BorderLayout.NORTH);

       
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 12));

        tabs.addTab("👤 My Academic Profile & CGPA", createProfileTab());
        tabs.addTab("📚 Registered Courses & Grades", createCoursesTab());
        tabs.addTab("📢 AIUB Notice Bulletin", createNoticeTab());
        tabs.addTab("🪪 Digital AIUB Student ID Card", createIdCardTab());

        root.add(tabs, BorderLayout.CENTER);

        
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(241, 245, 249));
        footer.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));
        JLabel lblFooter = new JLabel("AIUB Permanent Campus: 408/1, Kuratoli, Khilkhet, Dhaka 1229, Bangladesh | Connected to students.txt");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(new Color(100, 116, 139));
        footer.add(lblFooter, BorderLayout.WEST);
        root.add(footer, BorderLayout.SOUTH);

        add(root);
    }

   
    private JPanel createProfileTab() {
        JPanel p = new JPanel(new BorderLayout(20, 20));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        
        JPanel statRow = new JPanel(new GridLayout(1, 4, 15, 0));
        statRow.setOpaque(false);
        statRow.add(createMetricBox("Cumulative CGPA", String.format("%.2f", currentStudent.getCgpa()) + " / 4.00", new Color(16, 185, 129)));
        statRow.add(createMetricBox("Current Semester", "Semester " + currentStudent.getSemester(), new Color(59, 130, 246)));
        statRow.add(createMetricBox("Completed Credits", currentStudent.getCompletedCredits() + " Credits", new Color(139, 92, 246)));
        statRow.add(createMetricBox("Academic Standing", currentStudent.getCgpa() >= 3.75 ? "Dean's Honor Roll" : "Good Standing", new Color(234, 88, 12)));
        p.add(statRow, BorderLayout.NORTH);

        // Detailed Information Grid
        JPanel detailsCard = new JPanel(new GridLayout(7, 2, 12, 12));
        detailsCard.setBackground(new Color(248, 250, 252));
        detailsCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        addRow(detailsCard, "Student Full Name:", currentStudent.getName());
        addRow(detailsCard, "AIUB Student ID:", currentStudent.getId());
        addRow(detailsCard, "Academic Department:", currentStudent.getDepartment());
        addRow(detailsCard, "Institutional Email:", currentStudent.getEmail());
        addRow(detailsCard, "Contact Mobile:", currentStudent.getPhone());
        addRow(detailsCard, "Blood Group:", currentStudent.getBloodGroup());
        addRow(detailsCard, "Residential Address:", currentStudent.getAddress());
        addRow(detailsCard, "Degree Program:", currentStudent.getRoleTitle());
        addRow(detailsCard, "Campus Location:", "Permanent Campus, Kuratoli, Dhaka");
        addRow(detailsCard, "Portal System Role:", currentStudent.getRole());

        p.add(detailsCard, BorderLayout.CENTER);
        return p;
    }

    private JPanel createMetricBox(String title, String value, Color accent) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(new Color(248, 250, 252));
        box.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, accent),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        JLabel lblT = new JLabel(title);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblT.setForeground(new Color(100, 116, 139));

        JLabel lblV = new JLabel(value);
        lblV.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblV.setForeground(new Color(15, 23, 42));

        box.add(lblT);
        box.add(Box.createRigidArea(new Dimension(0, 4)));
        box.add(lblV);
        return box;
    }

    private void addRow(JPanel p, String label, String val) {
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(new Color(71, 85, 105));
        JLabel v = new JLabel(val);
        v.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        v.setForeground(new Color(15, 23, 42));
        p.add(l);
        p.add(v);
    }

    // TAB 2: Courses & Grades
    private JPanel createCoursesTab() {
        JPanel p = new JPanel(new BorderLayout(15, 15));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] cols = {"Course Code", "Course Title", "Credits", "Letter Grade", "Grade Point", "Faculty Instructor"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        for (Course c : portalManager.getDefaultCourses()) {
            model.addRow(new Object[]{
                c.getCourseCode(),
                c.getCourseTitle(),
                c.getCreditHours(),
                c.getGrade(),
                String.format("%.2f", c.getGradePoint()),
                c.getInstructor()
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(241, 245, 249));

        p.add(new JScrollPane(table), BorderLayout.CENTER);

     
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        bottom.setBackground(new Color(248, 250, 252));
        bottom.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        JLabel lblSummary = new JLabel("Enrolled Credits: 15.0 | Term GPA: 3.70 | Cumulative CGPA: " + String.format("%.2f", currentStudent.getCgpa()));
        lblSummary.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSummary.setForeground(new Color(13, 37, 71));
        bottom.add(lblSummary);
        p.add(bottom, BorderLayout.SOUTH);

        return p;
    }

    
    private JPanel createNoticeTab() {
        JPanel p = new JPanel(new BorderLayout(15, 15));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] cols = {"Notice ID", "Date", "Category", "Title", "Published By"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        for (Notice n : portalManager.getAllNotices()) {
            model.addRow(new Object[]{
                n.getId(),
                n.getDate(),
                n.getCategory(),
                n.getTitle(),
                n.getAuthor()
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

   
    private JPanel createIdCardTab() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(241, 245, 249));

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(420, 260));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(13, 37, 71), 3),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        
        JPanel cardHead = new JPanel(new BorderLayout());
        cardHead.setOpaque(false);
        JLabel lblLogo = new JLabel(ImageHelper.getAIUBLogo(40, 40));
        JLabel lblUniv = new JLabel("<html><b>AMERICAN INTERNATIONAL UNIVERSITY-BANGLADESH</b><br><small>STUDENT IDENTIFICATION CARD</small></html>");
        lblUniv.setForeground(new Color(13, 37, 71));
        cardHead.add(lblLogo, BorderLayout.WEST);
        cardHead.add(lblUniv, BorderLayout.CENTER);
        card.add(cardHead, BorderLayout.NORTH);

    
        JPanel cardBody = new JPanel(new GridLayout(4, 1, 4, 4));
        cardBody.setOpaque(false);
        cardBody.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

        JLabel lblName = new JLabel("Name: " + currentStudent.getName());
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel lblId = new JLabel("AIUB ID: " + currentStudent.getId());
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblId.setForeground(new Color(29, 78, 216));
        JLabel lblDept = new JLabel("Department: " + currentStudent.getDepartment());
        JLabel lblBlood = new JLabel("Blood Group: " + currentStudent.getBloodGroup() + " | Valid Till: 2027");

        cardBody.add(lblName);
        cardBody.add(lblId);
        cardBody.add(lblDept);
        cardBody.add(lblBlood);
        card.add(cardBody, BorderLayout.CENTER);

       
        JLabel lblBar = new JLabel("||| | ||||| ||| |||||||| |||| | ||||||||| | |||||", SwingConstants.CENTER);
        lblBar.setFont(new Font("Courier New", Font.BOLD, 16));
        lblBar.setForeground(Color.DARK_GRAY);
        card.add(lblBar, BorderLayout.SOUTH);

        p.add(card);
        return p;
    }
}