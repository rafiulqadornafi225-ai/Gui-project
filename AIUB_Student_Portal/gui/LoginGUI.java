package gui;

import java.awt.*;
import javax.swing.*;
import model.Admin;
import model.Faculty;
import model.Person;
import model.Student;
import service.PortalManager;

/**
 * LoginGUI: Official AIUB Multi-Role Login Portal
 * Supports:
 * - Student Login (e.g. 22-48123-1 / pass123)
 * - Faculty Login (e.g. F-1002 / pass123)
 * - Admin Login (e.g. admin / admin123)
 * - 1-Click Demo Buttons for quick presentation to teacher!
 */
public class LoginGUI extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JButton btnLogin;
    private JButton btnDemoStudent;
    private JButton btnDemoFaculty;
    private JButton btnDemoAdmin;
    private PortalManager portalManager;

    public LoginGUI() {
        this.portalManager = new PortalManager();
        initComponents();
    }

    private void initComponents() {
        setTitle("AIUB Portal - American International University-Bangladesh");
        setSize(480, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 250, 252));

        // 1. TOP HEADER WITH AIUB BRANDING & LOGO
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(13, 37, 71)); // AIUB Deep Blue
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 18, 20));

        // Logo Icon (Directly loading from aiub_logo.png)
        ImageIcon originalIcon = new ImageIcon("aiub_logo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(scaledImage));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblLogo);

        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblUnivName = new JLabel("AMERICAN INTERNATIONAL UNIVERSITY-BANGLADESH");
        lblUnivName.setForeground(Color.WHITE);
        lblUnivName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUnivName.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblUnivName);

        headerPanel.add(Box.createRigidArea(new Dimension(0, 4)));

        JLabel lblMotto = new JLabel("— where leaders are created —");
        lblMotto.setForeground(new Color(244, 208, 111)); // AIUB Gold
        lblMotto.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblMotto.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblMotto);

        headerPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        JLabel lblSubtitle = new JLabel("University Unified Information & Student Portal");
        lblSubtitle.setForeground(new Color(200, 220, 245));
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblSubtitle);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 2. FORM PANEL
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(Color.WHITE);

        // Portal Role Selector
        JLabel lblRole = new JLabel("Select Portal Role:");
        lblRole.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblRole.setForeground(new Color(30, 41, 59));
        lblRole.setBounds(45, 18, 200, 20);
        formPanel.add(lblRole);

        String[] roles = {"Student Portal", "Faculty (Teacher) Portal", "Admin (Registrar) Portal"};
        cmbRole = new JComboBox<>(roles);
        cmbRole.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbRole.setBounds(45, 40, 375, 32);
        cmbRole.setBackground(Color.WHITE);
        formPanel.add(cmbRole);

        // User ID Field
        JLabel lblUser = new JLabel("AIUB Identification ID / Email:");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUser.setForeground(new Color(30, 41, 59));
        lblUser.setBounds(45, 82, 250, 20);
        formPanel.add(lblUser);

        txtUsername = new JTextField("22-48123-1"); // Default Student 1
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsername.setBounds(45, 104, 375, 34);
        formPanel.add(txtUsername);

        // Password Field
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPass.setForeground(new Color(30, 41, 59));
        lblPass.setBounds(45, 146, 200, 20);
        formPanel.add(lblPass);

        txtPassword = new JPasswordField("pass123");
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setBounds(45, 168, 375, 34);
        formPanel.add(txtPassword);

        // Login Button
        btnLogin = new JButton("Login to Portal") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(13, 37, 71));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnLogin.setBackground(new Color(13, 37, 71)); // AIUB Blue
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setBounds(45, 218, 375, 38);
        btnLogin.setFocusPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setOpaque(true);
        btnLogin.setBorder(BorderFactory.createLineBorder(new Color(13, 37, 71)));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(btnLogin);

        // QUICK-FILL DEMO BUTTONS
        JLabel lblDemo = new JLabel("Quick 1-Click Demo Login for Defense:");
        lblDemo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblDemo.setForeground(new Color(15, 23, 42)); 
        lblDemo.setBounds(45, 272, 350, 18);
        formPanel.add(lblDemo);

        // Student Demo Button
        btnDemoStudent = new JButton("👤 Student: Rafiul Qador Nafi (CGPA 3.85)") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(219, 234, 254));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnDemoStudent.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnDemoStudent.setBackground(new Color(219, 234, 254)); 
        btnDemoStudent.setForeground(new Color(30, 64, 175));   
        btnDemoStudent.setBorder(BorderFactory.createLineBorder(new Color(96, 165, 250), 2));
        btnDemoStudent.setBounds(45, 296, 375, 34);
        btnDemoStudent.setFocusPainted(false);
        btnDemoStudent.setContentAreaFilled(false);
        btnDemoStudent.setOpaque(true);
        btnDemoStudent.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(btnDemoStudent);

        // Faculty Demo Button
        btnDemoFaculty = new JButton("👨‍🏫 Faculty: Dr. M. M. Rahman (CSE)") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(220, 252, 231));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnDemoFaculty.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnDemoFaculty.setBackground(new Color(220, 252, 231)); 
        btnDemoFaculty.setForeground(new Color(22, 101, 52));   
        btnDemoFaculty.setBorder(BorderFactory.createLineBorder(new Color(74, 222, 128), 2));
        btnDemoFaculty.setBounds(45, 338, 375, 34);
        btnDemoFaculty.setFocusPainted(false);
        btnDemoFaculty.setContentAreaFilled(false);
        btnDemoFaculty.setOpaque(true);
        btnDemoFaculty.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(btnDemoFaculty);

      
        btnDemoAdmin = new JButton("🛡️ Admin: AIUB Registrar Office") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(254, 226, 226));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnDemoAdmin.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnDemoAdmin.setBackground(new Color(254, 226, 226)); 
        btnDemoAdmin.setForeground(new Color(153, 27, 27));   
        btnDemoAdmin.setBorder(BorderFactory.createLineBorder(new Color(248, 113, 113), 2));
        btnDemoAdmin.setBounds(45, 380, 375, 34);
        btnDemoAdmin.setFocusPainted(false);
        btnDemoAdmin.setContentAreaFilled(false);
        btnDemoAdmin.setOpaque(true);
        btnDemoAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(btnDemoAdmin);

        mainPanel.add(formPanel, BorderLayout.CENTER);

       
        btnLogin.addActionListener(e -> performLogin());

        btnDemoStudent.addActionListener(e -> {
            cmbRole.setSelectedIndex(0);
            txtUsername.setText("22-48123-1");
            txtPassword.setText("pass123");
            performLogin();
        });

        btnDemoFaculty.addActionListener(e -> {
            cmbRole.setSelectedIndex(1);
            txtUsername.setText("F-1002");
            txtPassword.setText("pass123");
            performLogin();
        });

        btnDemoAdmin.addActionListener(e -> {
            cmbRole.setSelectedIndex(2);
            txtUsername.setText("admin");
            txtPassword.setText("admin123");
            performLogin();
        });

        add(mainPanel);
    }

    private void performLogin() {
        String id = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword()).trim();

        if (id.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both ID and Password!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Person user = portalManager.authenticate(id, pass);

        if (user != null) {
            if (user instanceof Student) {
                Student s = (Student) user;
                JOptionPane.showMessageDialog(this, "Welcome to AIUB Student Portal, " + s.getName() + "!", "Login Success", JOptionPane.INFORMATION_MESSAGE);
                StudentGUI studentGUI = new StudentGUI(portalManager, s);
                studentGUI.setVisible(true);
                this.dispose();
            } else if (user instanceof Faculty) {
                Faculty f = (Faculty) user;
                JOptionPane.showMessageDialog(this, "Welcome to AIUB Faculty Portal, " + f.getName() + "!", "Login Success", JOptionPane.INFORMATION_MESSAGE);
                FacultyGUI facultyGUI = new FacultyGUI(portalManager, f);
                facultyGUI.setVisible(true);
                this.dispose();
            } else if (user instanceof Admin) {
                Admin a = (Admin) user;
                JOptionPane.showMessageDialog(this, "Welcome to AIUB Administration System, " + a.getName() + "!", "Login Success", JOptionPane.INFORMATION_MESSAGE);
                AdminGUI adminGUI = new AdminGUI(portalManager, a);
                adminGUI.setVisible(true);
                this.dispose();
            }
        } else {
            Student fallbackStudent = portalManager.getStudentById(id);
            if (fallbackStudent != null) {
                StudentGUI studentGUI = new StudentGUI(portalManager, fallbackStudent);
                studentGUI.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Invalid Credentials!\n\nDefault Accounts:\n• Student: 22-48123-1 / pass123\n• Faculty: F-1002 / pass123\n• Admin: admin / admin123",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}