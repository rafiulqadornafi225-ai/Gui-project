package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Faculty;
import model.Notice;
import model.Student;
import service.PortalManager;
public class FacultyGUI extends JFrame {
    private PortalManager portalManager;
    private Faculty currentFaculty;

    private JTable tblStudents;
    private DefaultTableModel studentModel;
    private JTextField txtSelectedId;
    private JTextField txtSelectedName;
    private JTextField txtNewCgpa;
    private JComboBox<String> cmbGrade;
    private JButton btnUpdateGrade;

    private JTextField txtNoticeTitle;
    private JTextArea txtNoticeContent;
    private JButton btnPostNotice;

    public FacultyGUI(PortalManager portalManager, Faculty faculty) {
        this.portalManager = portalManager;
        this.currentFaculty = faculty != null ? faculty : portalManager.getAllFaculty().get(0);
        initComponents();
        loadStudents();
    }

    private void initComponents() {
        setTitle("AIUB Faculty Portal - " + currentFaculty.getName() + " (" + currentFaculty.getDesignation() + ")");
        setSize(980, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());

        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(new Color(13, 37, 71));
        banner.setPreferredSize(new Dimension(980, 75));
        banner.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        brandPanel.setOpaque(false);
        
        // --- লোগো সেটআপ (aiub_logo3.png) ---
        ImageIcon logoIcon = null;
        try {
            ImageIcon originalIcon = new ImageIcon("aiub_logo3.png");
            if (originalIcon.getIconWidth() > 0) {
                Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                logoIcon = new ImageIcon(scaledImage);
            }
        } catch (Exception e) {
            logoIcon = null;
        }
        
        JLabel lblLogo = logoIcon != null ? new JLabel(logoIcon) : new JLabel("AIUB");
        if (logoIcon == null) {
            lblLogo.setForeground(Color.WHITE);
            lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }
        brandPanel.add(lblLogo);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        JLabel lblTitle = new JLabel("AIUB FACULTY PORTAL (TEACHER)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(Color.WHITE);
        JLabel lblSub = new JLabel(currentFaculty.getName() + " | " + currentFaculty.getDetailsSummary());
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(244, 208, 111));
        titlePanel.add(lblTitle);
        titlePanel.add(lblSub);
        brandPanel.add(titlePanel);
        banner.add(brandPanel, BorderLayout.WEST);

        // Logout Button
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

        tabs.addTab("📊 Student Evaluation & CGPA Update", createGradePanel());
        tabs.addTab("📢 Post Announcement / Class Notice", createPostNoticePanel());

        root.add(tabs, BorderLayout.CENTER);

        add(root);
    }

    private JPanel createGradePanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setPreferredSize(new Dimension(320, 500));
        form.setBackground(new Color(248, 250, 252));
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Student CGPA & Grade Evaluation"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        txtSelectedId = addFormField(form, "Selected Student ID:");
        txtSelectedId.setEditable(false);
        txtSelectedName = addFormField(form, "Student Name:");
        txtSelectedName.setEditable(false);

        JLabel lblG = new JLabel("Course Final Grade:");
        lblG.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        String[] grades = {"A+ (4.00)", "A (3.75)", "B+ (3.50)", "B (3.25)", "C+ (3.00)", "C (2.75)", "D (2.50)", "F (0.00)"};
        cmbGrade = new JComboBox<>(grades);
        form.add(lblG);
        form.add(cmbGrade);
        form.add(Box.createRigidArea(new Dimension(0, 8)));

        txtNewCgpa = addFormField(form, "New Updated CGPA (0.00 - 4.00):");

        btnUpdateGrade = new JButton("Update Student CGPA & Grade") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(13, 37, 71));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnUpdateGrade.setBackground(new Color(13, 37, 71));
        btnUpdateGrade.setForeground(Color.WHITE);
        btnUpdateGrade.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnUpdateGrade.setFocusPainted(false);
        btnUpdateGrade.setContentAreaFilled(false);
        btnUpdateGrade.setOpaque(true);
        btnUpdateGrade.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdateGrade.addActionListener(e -> handleUpdateGrade());
        form.add(btnUpdateGrade);

        panel.add(form, BorderLayout.WEST);

        String[] cols = {"Student ID", "Full Name", "Department", "Semester", "Current CGPA", "Credits"};
        studentModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblStudents = new JTable(studentModel);
        tblStudents.setRowHeight(26);
        tblStudents.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblStudents.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        tblStudents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblStudents.getSelectedRow();
                if (row != -1) {
                    String id = (String) studentModel.getValueAt(row, 0);
                    Student s = portalManager.getStudentById(id);
                    if (s != null) {
                        txtSelectedId.setText(s.getId());
                        txtSelectedName.setText(s.getName());
                        txtNewCgpa.setText(String.format("%.2f", s.getCgpa()));
                    }
                }
            }
        });

        panel.add(new JScrollPane(tblStudents), BorderLayout.CENTER);
        return panel;
    }

    private JTextField addFormField(JPanel p, String labelText) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        JTextField txt = new JTextField();
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));
        p.add(lbl);
        p.add(txt);
        p.add(Box.createRigidArea(new Dimension(0, 6)));
        return txt;
    }

    private void handleUpdateGrade() {
        String id = txtSelectedId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student from the table first!", "Notice", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double cgpa = Double.parseDouble(txtNewCgpa.getText().trim());
            if (cgpa < 0.0 || cgpa > 4.0) {
                JOptionPane.showMessageDialog(this, "CGPA must be between 0.00 and 4.00!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = portalManager.updateStudentCgpa(id, cgpa);
            if (success) {
                JOptionPane.showMessageDialog(this, "CGPA updated successfully and saved to students.txt!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadStudents();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric CGPA (e.g. 3.85)", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createPostNoticePanel() {
        JPanel p = new JPanel(new BorderLayout(15, 15));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JPanel form = new JPanel(new GridLayout(6, 1, 8, 8));
        form.setOpaque(false);

        JLabel lblT = new JLabel("Announcement / Notice Title:");
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtNoticeTitle = new JTextField();

        JLabel lblC = new JLabel("Announcement Details / Instructions for Students:");
        lblC.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtNoticeContent = new JTextArea(4, 20);
        txtNoticeContent.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        btnPostNotice = new JButton("Publish Notice to AIUB Bulletin") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(22, 101, 52));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnPostNotice.setBackground(new Color(22, 101, 52));
        btnPostNotice.setForeground(Color.WHITE);
        btnPostNotice.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnPostNotice.setFocusPainted(false);
        btnPostNotice.setContentAreaFilled(false);
        btnPostNotice.setOpaque(true);
        btnPostNotice.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPostNotice.addActionListener(e -> {
            String title = txtNoticeTitle.getText().trim();
            String content = txtNoticeContent.getText().trim();
            if (title.isEmpty() || content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter title and content!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Notice n = new Notice(
                "NOT-" + (portalManager.getAllNotices().size() + 1),
                title,
                "2026-09-04",
                "Academic",
                content,
                currentFaculty.getName() + " (" + currentFaculty.getDepartment() + ")"
            );

            portalManager.addNotice(n);
            JOptionPane.showMessageDialog(this, "Notice published and saved to notices.txt!", "Notice Published", JOptionPane.INFORMATION_MESSAGE);
            txtNoticeTitle.setText("");
            txtNoticeContent.setText("");
        });

        form.add(lblT);
        form.add(txtNoticeTitle);
        form.add(lblC);
        form.add(new JScrollPane(txtNoticeContent));
        form.add(Box.createRigidArea(new Dimension(0, 5)));
        form.add(btnPostNotice);

        p.add(form, BorderLayout.NORTH);
        return p;
    }

    private void loadStudents() {
        studentModel.setRowCount(0);
        for (Student s : portalManager.getAllStudents()) {
            studentModel.addRow(new Object[]{
                s.getId(),
                s.getName(),
                s.getDepartment(),
                "Semester " + s.getSemester(),
                String.format("%.2f", s.getCgpa()),
                s.getCompletedCredits()
            });
        }
    }
}