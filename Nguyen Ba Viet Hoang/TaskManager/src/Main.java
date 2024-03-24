import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
       
        setTitle("Task Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setVerticalAlignment(SwingConstants.CENTER);
        passwordLabel.setVerticalAlignment(SwingConstants.CENTER);

        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        JButton Login_Button = getButton(usernameField, passwordField);

        GridLayout loginPanel = new GridLayout(3, 2, 5, 6);
        this.setLayout(loginPanel);
        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(new JLabel());
        this.add(Login_Button);
    }

    private JButton getButton(JTextField usernameField, JPasswordField passwordField) {

        JButton Login_Button = new JButton("Login");
        Login_Button.addActionListener(_ -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (login(username, password)) {
                openTaskManager(username);
            } else {
                JOptionPane.showMessageDialog(Main.this, "Invalid username or password. Please try again.");
            }
        });
        return Login_Button;
    }

    private boolean login(String username, String password) 
    {
        return username.equals("admin") && password.equals("admin");
    }

    private void openTaskManager(String username) 
    {
        TaskManagerFrame TM = new TaskManagerFrame(username);
        TM.setVisible(true);
        dispose();
    }

    public static void main(String[] args) 
    {
        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }

}
