
import gui.LoginGUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Start {
    public static void main(String[] args) {
        try {
            // Apply native operating system look and feel for modern UI styling
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Note: Fallback to default Java Swing Look & Feel.");
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginGUI loginFrame = new LoginGUI();
                loginFrame.setVisible(true);
            }
        });
    }
}
