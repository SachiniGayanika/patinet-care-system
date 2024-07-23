package medicalrecords;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class LoginPage extends JFrame {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public LoginPage() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Login Page");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Load the image
        ImageIcon loginImage = new ImageIcon("F:\\DIPLOMA\\ead1\\QR Code Medical Records\\images\\loginimg.jpeg");
        Image img = loginImage.getImage();
        Image resizedImage = img.getScaledInstance(500, 250, Image.SCALE_SMOOTH);
        loginImage = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(loginImage);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(imageLabel, gbc);

        JLabel titleLabel = new JLabel("Welcome");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 150, 250));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(userLabel, gbc);

        JTextField userField = new JTextField(15);
        userField.setFont(new Font("Arial", Font.PLAIN, 16));
        userField.setBorder(new LineBorder(new Color(50, 150, 250), 2, true));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(userField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(15);
        passField.setFont(new Font("Arial", Font.PLAIN, 16));
        passField.setBorder(new LineBorder(new Color(50, 150, 250), 2, true));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(passField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(50, 150, 250));
        loginButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(loginButton, gbc);

        // Action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword());

                // Check credentials
                if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                    // Show success message dialog
                    JOptionPane.showMessageDialog(LoginPage.this,
                            "Login successful!",
                            "Login Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Open home page
                    HomePage homePage = new HomePage();
                    homePage.setVisible(true);

                    // Close login page
                    dispose();
                } else {
                    // Show error message dialog
                    JOptionPane.showMessageDialog(LoginPage.this,
                            "Invalid username or password.",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        });
    }
}