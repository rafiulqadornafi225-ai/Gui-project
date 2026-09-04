package gui;

import java.awt.*;
import javax.swing.*;
import model.Admin;
import model.Faculty;
import model.Person;
import model.Student;
import service.PortalManager;

public class LoginGUI extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JButton btnLogin;
    private JButton btnDemoStudentNafi;
    private JButton btnDemoStudentSadia;
    private JButton btnDemoFaculty;
    private JButton btnDemoAdmin;
    private PortalManager portalManager;

    public LoginGUI() {
        this.portalManager = new PortalManager();
        initComponents();
    }

    private void initComponents() {
        setTitle("AIUB Portal - American International University-Bangladesh");
        setSize(480, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 250, 252));

       
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(13, 37, 71));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        ImageIcon originalIcon = new ImageIcon("aiub_logo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(56, 56, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(scaledImage));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblLogo);

        headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        JLabel lblUnivName = new JLabel("AMERICAN INTERNATIONAL UNIVERSITY-BANGLADESH");
        lblUnivName.setForeground(Color.WHITE);
        lblUnivName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUnivName.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblUnivName);

        headerPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JLabel lblMotto = new JLabel("— where leaders are created —");
        lblMotto.setForeground(new Color(244, 208, 111));
        lblMotto.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblMotto.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblMotto);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

       
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(Color.WHITE);

        JLabel lblRole = new JLabel("Select Portal Role:");
        lblRole.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblRole.setForeground(new Color(30, 41, 59));
        lblRole.setBounds(45, 15, 200, 20);
        formPanel.add(lblRole);

        String[] roles = {"Student", "Faculty", "Admin"};
        cmbRole = new JComboBox<>(roles);
        cmbRole.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbRole.setBounds(45, 37, 375, 30);
        cmbRole.setBackground(Color.WHITE);
        formPanel.add(cmbRole);

        JLabel lblUser = new JLabel("AIUB Identification ID / Email:");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUser.setForeground(new Color(30, 41, 59));
        lblUser.setBounds(45, 75, 250, 20);
        formPanel.add(lblUser);

        txtUsername = new JTextField("26-64541-1");
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsername.setBounds(45, 97, 375, 32);
        formPanel.add(txtUsername);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPass.setForeground(new Color(30, 41, 59));
        lblPass.setBounds(45, 135, 200, 20);
        formPanel.add(lblPass);

        txtPassword = new JPasswordField("pass123");
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setBounds(45, 157, 375, 32);
        formPanel.add(txtPassword);

        cmbRole.addActionListener(e -> {
            int selectedIndex = cmbRole.getSelectedIndex();
            if (selectedIndex == 0) {
                txtUsername.setText("26-64541-1");
                txtPassword.setText("pass123");
            } else if (selectedIndex == 1) {
                txtUsername.setText("F-1002");
                txtPassword.setText("pass123");
            } else if (selectedIndex == 2) {
                txtUsername.setText("admin");
                txtPassword.setText("admin123");
            }
        });

        btnLogin = new JButton("Login to Portal") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(13, 37, 71));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnLogin.setBackground(new Color(13, 37, 71));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setBounds(45, 202, 375, 36);
        btnLogin.setFocusPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setOpaque(true);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(btnLogin);

       
        JLabel lblDemo = new JLabel("Quick 1-Click Demo Login for Defense:");
        lblDemo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblDemo.setForeground(new Color(15, 23, 42)); 
        lblDemo.setBounds(45, 248, 350, 18);
        formPanel.add(lblDemo);

        
        btnDemoStudentNafi = new JButton("👤 Student: Rafiul Qador Nafi") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(219, 234, 254));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnDemoStudentNafi.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnDemoStudentNafi.setForeground(new Color(30, 64, 175));   
        btnDemoStudentNafi.setBounds(45, 270, 375, 30);
        btnDemoStudentNafi.setFocusPainted(false);
        btnDemoStudentNafi.setContentAreaFilled(false);
        btnDemoStudentNafi.setOpaque(true);
        btnDemoStudentNafi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(btnDemoStudentNafi);

        
        btnDemoStudentSadia = new JButton("👤 Student: Nowshin Sadia") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(238, 242, 255));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnDemoStudentSadia.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnDemoStudentSadia.setForeground(new Color(79, 70, 229));   
        btnDemoStudentSadia.setBounds(45, 305, 375, 30);
        btnDemoStudentSadia.setFocusPainted(false);
        btnDemoStudentSadia.setContentAreaFilled(false);
        btnDemoStudentSadia.setOpaque(true);
        btnDemoStudentSadia.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(btnDemoStudentSadia);

        
        btnDemoFaculty = new JButton("👨‍🏫 Faculty: Dr. M. M. Rahman") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(220, 252, 231));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnDemoFaculty.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnDemoFaculty.setForeground(new Color(22, 101, 52));   
        btnDemoFaculty.setBounds(45, 340, 375, 30);
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
        btnDemoAdmin.setForeground(new Color(153, 27, 27));   
        btnDemoAdmin.setBounds(45, 375, 375, 30);
        btnDemoAdmin.setFocusPainted(false);
        btnDemoAdmin.setContentAreaFilled(false);
        btnDemoAdmin.setOpaque(true);
        btnDemoAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(btnDemoAdmin);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Action Listeners
        btnLogin.addActionListener(e -> performLogin());

        btnDemoStudentNafi.addActionListener(e -> {
            cmbRole.setSelectedIndex(0);
            txtUsername.setText("26-64541-1");
            txtPassword.setText("pass123");
            performLogin();
        });

        btnDemoStudentSadia.addActionListener(e -> {
            cmbRole.setSelectedIndex(0);
            txtUsername.setText("26-64470-1");
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
                    "Invalid Credentials!\n\nDefault Accounts:\n• Nafi: 26-64541-1 / pass123\n• Sadia: 26-64470-1 / pass123\n• Faculty: F-1002 / pass123\n• Admin: admin / admin123",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}