package gui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Admin;
import model.Notice;
import model.Student;
import service.PortalManager;


public class AdminGUI extends JFrame {
    private PortalManager portalManager;
    private Admin currentAdmin;

    
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtDept;
    private JTextField txtSemester;
    private JTextField txtCgpa;
    private JTextField txtCredits;
    private JTextField txtBlood;
    private JTextField txtAddress;
    private JTextField txtSearch;

    private JTable tblStudents;
    private DefaultTableModel studentModel;

    
    private JTable tblNotices;
    private DefaultTableModel noticeModel;
    private JTextField txtNoticeTitle;
    private JTextField txtNoticeCategory;
    private JTextArea txtNoticeContent;

    public AdminGUI(PortalManager portalManager, Admin admin) {
        this.portalManager = portalManager;
        this.currentAdmin = admin;
        initComponents();
        loadStudentsToTable();
        loadNoticesToTable();
    }

    private void initComponents() {
        setTitle("AIUB Portal Administration - Registrar Office");
        setSize(1040, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());

        
        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(new Color(13, 37, 71)); // AIUB Blue
        banner.setPreferredSize(new Dimension(1040, 75));
        banner.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        brand.setOpaque(false);
        
      
        ImageIcon logoIcon = null;
        try {
            ImageIcon originalIcon = new ImageIcon("aiub_logo.png");
            if (originalIcon.getIconWidth() > 0) {
                Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                logoIcon = new ImageIcon(scaledImage);
            }
        } catch (Exception e) {
            logoIcon = null;
        }
        
        JLabel lblLogo = logoIcon != null ? new JLabel(logoIcon) : new JLabel();
        brand.add(lblLogo);

        JPanel titles = new JPanel();
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));
        titles.setOpaque(false);
        JLabel lblT = new JLabel("AMERICAN INTERNATIONAL UNIVERSITY-BANGLADESH");
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblT.setForeground(Color.WHITE);
        JLabel lblS = new JLabel("Registrar & Administrative System Control Panel");
        lblS.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblS.setForeground(new Color(244, 208, 111));
        titles.add(lblT);
        titles.add(lblS);
        brand.add(titles);
        banner.add(brand, BorderLayout.WEST);

      
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
        banner.add(btnLogout, BorderLayout.EAST);
        root.add(banner, BorderLayout.NORTH);

       
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 12));

        tabs.addTab("👥 Student Records Management (CRUD)", createStudentCrudTab());
        tabs.addTab("📢 University Notice Board Manager", createNoticeCrudTab());

        root.add(tabs, BorderLayout.CENTER);

       
        JPanel status = new JPanel(new BorderLayout());
        status.setBackground(new Color(241, 245, 249));
        status.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));
        JLabel lblStatus = new JLabel("Database Status: Connected to students.txt & notices.txt | AIUB System Ready");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        status.add(lblStatus, BorderLayout.WEST);
        root.add(status, BorderLayout.SOUTH);

        add(root);
    }

    private JPanel createStudentCrudTab() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setPreferredSize(new Dimension(340, 520));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder("Student Details Form (CRUD)"));

        txtId = addField(form, "Student ID (e.g. 22-48123-1):");
        txtName = addField(form, "Full Name:");
        txtEmail = addField(form, "Official Email:");
        txtPhone = addField(form, "Contact Phone:");
        txtDept = addField(form, "Department (e.g. CSE):");
        txtSemester = addField(form, "Semester (1-8):");
        txtCgpa = addField(form, "CGPA (0.00 - 4.00):");
        txtCredits = addField(form, "Completed Credits:");
        txtBlood = addField(form, "Blood Group (e.g. A+):");
        txtAddress = addField(form, "Residential Address:");

        
        JPanel btnRow = new JPanel(new GridLayout(2, 2, 6, 6));
        btnRow.setBackground(Color.WHITE);
        btnRow.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton btnInsert = new JButton("Insert (Add)") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(22, 101, 52));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnInsert.setBackground(new Color(22, 101, 52));
        btnInsert.setForeground(Color.WHITE);
        btnInsert.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnInsert.setFocusPainted(false);
        btnInsert.setContentAreaFilled(false);
        btnInsert.setOpaque(true);

        JButton btnUpdate = new JButton("Update") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(30, 64, 175));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnUpdate.setBackground(new Color(30, 64, 175));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnUpdate.setFocusPainted(false);
        btnUpdate.setContentAreaFilled(false);
        btnUpdate.setOpaque(true);

        JButton btnDelete = new JButton("Delete") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(185, 28, 28));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnDelete.setBackground(new Color(185, 28, 28));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnDelete.setFocusPainted(false);
        btnDelete.setContentAreaFilled(false);
        btnDelete.setOpaque(true);

        JButton btnClear = new JButton("Clear Form") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(100, 116, 139));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnClear.setBackground(new Color(100, 116, 139));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnClear.setFocusPainted(false);
        btnClear.setContentAreaFilled(false);
        btnClear.setOpaque(true);

        btnRow.add(btnInsert);
        btnRow.add(btnUpdate);
        btnRow.add(btnDelete);
        btnRow.add(btnClear);
        form.add(btnRow);

        panel.add(form, BorderLayout.WEST);

        // RIGHT: Search Bar & Students Table
        JPanel right = new JPanel(new BorderLayout(8, 8));
        right.setBackground(Color.WHITE);

        // Search Bar
        JPanel searchBar = new JPanel(new BorderLayout(6, 6));
        searchBar.setBackground(Color.WHITE);
        txtSearch = new JTextField();
        txtSearch.setToolTipText("Search by Student ID, Name, or Department");
        JButton btnSearch = new JButton("Search / Refresh") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(13, 37, 71));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnSearch.setBackground(new Color(13, 37, 71));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setContentAreaFilled(false);
        btnSearch.setOpaque(true);

        searchBar.add(new JLabel("Search Student: "), BorderLayout.WEST);
        searchBar.add(txtSearch, BorderLayout.CENTER);
        searchBar.add(btnSearch, BorderLayout.EAST);
        right.add(searchBar, BorderLayout.NORTH);

        // Table
        String[] cols = {"ID", "Full Name", "Department", "Sem", "CGPA", "Credits", "Phone"};
        studentModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblStudents = new JTable(studentModel);
        tblStudents.setRowHeight(24);
        tblStudents.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tblStudents.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));

        tblStudents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblStudents.getSelectedRow();
                if (row != -1) {
                    String id = (String) studentModel.getValueAt(row, 0);
                    Student s = portalManager.getStudentById(id);
                    if (s != null) {
                        populateStudentForm(s);
                    }
                }
            }
        });

        right.add(new JScrollPane(tblStudents), BorderLayout.CENTER);
        panel.add(right, BorderLayout.CENTER);

        // Action Handlers
        btnInsert.addActionListener(e -> handleInsertStudent());
        btnUpdate.addActionListener(e -> handleUpdateStudent());
        btnDelete.addActionListener(e -> handleDeleteStudent());
        btnClear.addActionListener(e -> clearStudentForm());
        btnSearch.addActionListener(e -> handleSearchStudent());
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleSearchStudent();
            }
        });

        return panel;
    }

    private JTextField addField(JPanel p, String lblText) {
        JLabel lbl = new JLabel(lblText);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        JTextField txt = new JTextField();
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        p.add(lbl);
        p.add(txt);
        p.add(Box.createRigidArea(new Dimension(0, 3)));
        return txt;
    }

    private void populateStudentForm(Student s) {
        txtId.setText(s.getId());
        txtName.setText(s.getName());
        txtEmail.setText(s.getEmail());
        txtPhone.setText(s.getPhone());
        txtDept.setText(s.getDepartment());
        txtSemester.setText(String.valueOf(s.getSemester()));
        txtCgpa.setText(String.valueOf(s.getCgpa()));
        txtCredits.setText(String.valueOf(s.getCompletedCredits()));
        txtBlood.setText(s.getBloodGroup());
        txtAddress.setText(s.getAddress());
        txtId.setEditable(false);
    }

    private void clearStudentForm() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtDept.setText("");
        txtSemester.setText("");
        txtCgpa.setText("");
        txtCredits.setText("");
        txtBlood.setText("");
        txtAddress.setText("");
        txtId.setEditable(true);
        tblStudents.clearSelection();
    }

    private void handleInsertStudent() {
        try {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String dept = txtDept.getText().trim();
            int sem = Integer.parseInt(txtSemester.getText().trim());
            double cgpa = Double.parseDouble(txtCgpa.getText().trim());
            int credits = Integer.parseInt(txtCredits.getText().trim());
            String blood = txtBlood.getText().trim();
            String addr = txtAddress.getText().trim();

            if (id.isEmpty() || name.isEmpty() || dept.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID, Name, and Department are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student newS = new Student(id, name, email, phone, "pass123", dept, sem, cgpa, credits, blood, addr);
            boolean ok = portalManager.addStudent(newS);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Student registered successfully and saved to students.txt!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadStudentsToTable();
                clearStudentForm();
            } else {
                JOptionPane.showMessageDialog(this, "Student ID already exists!", "Duplicate ID", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Semester, CGPA, and Credits must be valid numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdateStudent() {
        try {
            String id = txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a student from the table first!", "Notice", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String dept = txtDept.getText().trim();
            int sem = Integer.parseInt(txtSemester.getText().trim());
            double cgpa = Double.parseDouble(txtCgpa.getText().trim());
            int credits = Integer.parseInt(txtCredits.getText().trim());
            String blood = txtBlood.getText().trim();
            String addr = txtAddress.getText().trim();

            Student updated = new Student(id, name, email, phone, "pass123", dept, sem, cgpa, credits, blood, addr);
            boolean ok = portalManager.updateStudent(updated);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Student updated and saved to students.txt!", "Updated", JOptionPane.INFORMATION_MESSAGE);
                loadStudentsToTable();
                clearStudentForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format for Semester, CGPA, or Credits!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteStudent() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete!", "Notice", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opt = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete student ID: " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            portalManager.deleteStudent(id);
            JOptionPane.showMessageDialog(this, "Student deleted from database and students.txt updated.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
            loadStudentsToTable();
            clearStudentForm();
        }
    }

    private void handleSearchStudent() {
        String q = txtSearch.getText().trim();
        List<Student> list = portalManager.searchStudents(q);
        populateStudentTable(list);
    }

    private void loadStudentsToTable() {
        populateStudentTable(portalManager.getAllStudents());
    }

    private void populateStudentTable(List<Student> list) {
        studentModel.setRowCount(0);
        for (Student s : list) {
            studentModel.addRow(new Object[]{
                s.getId(),
                s.getName(),
                s.getDepartment(),
                s.getSemester(),
                String.format("%.2f", s.getCgpa()),
                s.getCompletedCredits(),
                s.getPhone()
            });
        }
    }

    private JPanel createNoticeCrudTab() {
        JPanel p = new JPanel(new BorderLayout(15, 15));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        
        JPanel form = new JPanel(new GridLayout(5, 1, 6, 6));
        form.setPreferredSize(new Dimension(320, 300));
        form.setBackground(new Color(248, 250, 252));
        form.setBorder(BorderFactory.createTitledBorder("Post / Update Notice"));

        txtNoticeTitle = new JTextField();
        txtNoticeCategory = new JTextField("Academic");
        txtNoticeContent = new JTextArea(3, 20);

        JButton btnAddN = new JButton("Publish Notice to notices.txt") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(13, 37, 71));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnAddN.setBackground(new Color(13, 37, 71));
        btnAddN.setForeground(Color.WHITE);
        btnAddN.setFocusPainted(false);
        btnAddN.setContentAreaFilled(false);
        btnAddN.setOpaque(true);
        btnAddN.addActionListener(e -> {
            String title = txtNoticeTitle.getText().trim();
            String cat = txtNoticeCategory.getText().trim();
            String cnt = txtNoticeContent.getText().trim();
            if (title.isEmpty()) return;

            Notice n = new Notice("NOT-" + (portalManager.getAllNotices().size() + 1), title, "2026-09-04", cat, cnt, "AIUB Registrar Office");
            portalManager.addNotice(n);
            loadNoticesToTable();
            txtNoticeTitle.setText("");
            txtNoticeContent.setText("");
        });

        form.add(new JLabel("Notice Title:"));
        form.add(txtNoticeTitle);
        form.add(new JLabel("Category (Exam / Academic / Event):"));
        form.add(txtNoticeCategory);
        form.add(btnAddN);
        p.add(form, BorderLayout.WEST);

        
        String[] cols = {"ID", "Date", "Category", "Title", "Author"};
        noticeModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblNotices = new JTable(noticeModel);
        p.add(new JScrollPane(tblNotices), BorderLayout.CENTER);

        return p;
    }

    private void loadNoticesToTable() {
        noticeModel.setRowCount(0);
        for (Notice n : portalManager.getAllNotices()) {
            noticeModel.addRow(new Object[]{
                n.getId(),
                n.getDate(),
                n.getCategory(),
                n.getTitle(),
                n.getAuthor()
            });
        }
    }
}