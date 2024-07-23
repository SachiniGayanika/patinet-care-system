package medicalrecords;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddPatient extends JFrame {

    private JPanel contentPane;
    private JTextField patientID;
    private JTextField patientName;
    private JTextField patientAge;
    private JTextField patientCity;
    private JTextField patientDiseases;
    private JTextField patientPhoneNumber;

    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/medical";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddPatient frame = new AddPatient();
                    frame.setVisible(true);
                    frame.setTitle("ADD PATIENT RECORD");
                    frame.setLayout(null);
                    frame.setLocationRelativeTo(null);
                    frame.setResizable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public AddPatient() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        setContentPane(contentPane);

        // Labels
        JLabel patientIDLabel = new JLabel("Patient ID");
        patientIDLabel.setFont(new Font("Arial", Font.BOLD, 20));
        patientIDLabel.setBounds(100, 110, 150, 30);
        contentPane.add(patientIDLabel);

        JLabel patientNameLabel = new JLabel("Patient Name");
        patientNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        patientNameLabel.setBounds(100, 160, 150, 30);
        contentPane.add(patientNameLabel);

        JLabel patientAgeLabel = new JLabel("Patient Age");
        patientAgeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        patientAgeLabel.setBounds(100, 210, 150, 30);
        contentPane.add(patientAgeLabel);

        JLabel patientCityLabel = new JLabel("Patient City");
        patientCityLabel.setFont(new Font("Arial", Font.BOLD, 20));
        patientCityLabel.setBounds(100, 260, 150, 30);
        contentPane.add(patientCityLabel);

        JLabel patientDiseasesLabel = new JLabel("Patient Diseases");
        patientDiseasesLabel.setFont(new Font("Arial", Font.BOLD, 20));
        patientDiseasesLabel.setBounds(100, 310, 180, 30);
        contentPane.add(patientDiseasesLabel);

        JLabel patientPhoneNumberLabel = new JLabel("Phone Number");
        patientPhoneNumberLabel.setFont(new Font("Arial", Font.BOLD, 20));
        patientPhoneNumberLabel.setBounds(100, 360, 180, 30);
        contentPane.add(patientPhoneNumberLabel);


        // Curved text fields
        patientID = new JTextField();
        patientID.setBounds(340, 112, 230, 28);
        contentPane.add(patientID);

        patientName = new JTextField();
        patientName.setBounds(340, 162, 230, 28);
        contentPane.add(patientName);

        patientAge = new JTextField();
        patientAge.setBounds(340, 212, 230, 28);
        contentPane.add(patientAge);

        patientCity = new JTextField();
        patientCity.setBounds(340, 262, 230, 28);
        contentPane.add(patientCity);

        patientDiseases = new JTextField();
        patientDiseases.setBounds(340, 312, 230, 28);
        contentPane.add(patientDiseases);

        patientPhoneNumber = new JTextField();
        patientPhoneNumber.setBounds(340, 362, 230, 28);
        contentPane.add(patientPhoneNumber);
        
        
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 530, 220, 30);
        backButton.setBackground(new Color(0, 153, 255));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open QRScanInterface.java
                HomePage HomePage = new HomePage();
                HomePage.setVisible(true);
                dispose(); // Close the current window
            }
        });
        contentPane.add(backButton);

        // Curved buttons with blue-white theme
        JButton registerBtn = new JButton("REGISTER");
        registerBtn.setBounds(100, 450, 120, 35);
        registerBtn.setBackground(new Color(0, 153, 255)); // Blue color
        registerBtn.setForeground(Color.WHITE); // White text
        registerBtn.setFocusPainted(false); // Remove focus border
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font
        registerBtn.setBorder(new RoundedBorder(10, new Color(0, 102, 204))); // Custom rounded border
        contentPane.add(registerBtn);

        // Register button action listener
        registerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform registration logic here
                insertPatientRecord();
            }
        });
        
        JButton updateBtn = new JButton("UPDATE");
        updateBtn.setBounds(250, 450, 120, 35);
        updateBtn.setBackground(new Color(0, 153, 255)); // Blue color
        updateBtn.setForeground(Color.WHITE); // White text
        updateBtn.setFocusPainted(false); // Remove focus border
        updateBtn.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font
        updateBtn.setBorder(new RoundedBorder(10, new Color(0, 102, 204))); // Custom rounded border
        contentPane.add(updateBtn);
        
        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pid = patientID.getText();
                String pname = patientName.getText();
                int page = Integer.parseInt(patientAge.getText());
                String pcity = patientCity.getText();
                String pdiseases = patientDiseases.getText();
                String pnumber = patientPhoneNumber.getText();

                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                    String sql = "UPDATE patient SET pname = ?, page = ?, pcity = ?, pdiseases = ?, pnumber = ? WHERE pid = ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, pname);
                    statement.setInt(2, page);
                    statement.setString(3, pcity);
                    statement.setString(4, pdiseases);
                    statement.setString(5, pnumber);
                    statement.setString(6, pid);

                    int rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(AddPatient.this, "Patient record updated successfully.");
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(AddPatient.this, "Failed to update patient record.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddPatient.this, "Error: " + ex.getMessage());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddPatient.this, "Please enter a valid age.");
                }
            }
        });


        JButton deleteBtn = new JButton("DELETE");
        deleteBtn.setBounds(400, 450, 120, 35);
        deleteBtn.setBackground(new Color(0, 153, 255)); // Blue color
        deleteBtn.setForeground(Color.WHITE); // White text
        deleteBtn.setFocusPainted(false); // Remove focus border
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font
        deleteBtn.setBorder(new RoundedBorder(10, new Color(0, 102, 204))); // Custom rounded border
        contentPane.add(deleteBtn);
        
        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pid = patientID.getText();

                int confirm = JOptionPane.showConfirmDialog(AddPatient.this,
                        "Are you sure you want to delete this patient record?", "Confirm Delete",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                        String sql = "DELETE FROM patient WHERE pid = ?";
                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.setString(1, pid);

                        int rowsDeleted = statement.executeUpdate();
                        if (rowsDeleted > 0) {
                            JOptionPane.showMessageDialog(AddPatient.this, "Patient record deleted successfully.");
                            clearFields();
                        } else {
                            JOptionPane.showMessageDialog(AddPatient.this, "Patient not found.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(AddPatient.this, "Error: " + ex.getMessage());
                    }
                }
            }
        });
        
        JButton searchBtn = new JButton("SEARCH");
        searchBtn.setBounds(600, 105, 120, 35);
        searchBtn.setBackground(new Color(0, 153, 255)); // Blue color
        searchBtn.setForeground(Color.WHITE); // White text
        searchBtn.setFocusPainted(false); // Remove focus border
        searchBtn.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font
        searchBtn.setBorder(new RoundedBorder(10, new Color(0, 102, 204))); // Custom rounded border
        contentPane.add(searchBtn);
        
        // Action listeners for buttons

        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchID = patientID.getText();
                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                    String sql = "SELECT * FROM patient WHERE pid = ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, searchID);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        patientName.setText(result.getString("pname"));
                        patientAge.setText(String.valueOf(result.getInt("page")));
                        patientCity.setText(result.getString("pcity"));
                        patientDiseases.setText(result.getString("pdiseases"));
                        patientPhoneNumber.setText(result.getString("pnumber"));
                        JOptionPane.showMessageDialog(AddPatient.this, "Patient found.");
                    } else {
                        JOptionPane.showMessageDialog(AddPatient.this, "Patient  not found.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddPatient.this, "Error: " + ex.getMessage());
                }
            }
        });


        
     


        
        JLabel addRecordLabel = new JLabel("ADD PATIENT RECORD");
        addRecordLabel.setFont(new Font("Arial", Font.BOLD, 24));
        addRecordLabel.setBounds(250, 30, 300, 30);
        contentPane.add(addRecordLabel);

    }

    // Method to insert patient record into the database
    private void insertPatientRecord() {
        String pid = patientID.getText();
        String pname = patientName.getText();
        int page = Integer.parseInt(patientAge.getText());
        String pcity = patientCity.getText();
        String pdiseases = patientDiseases.getText();
        String pnumber = patientPhoneNumber.getText();

        // Database connection and insert statement
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO patient (pid, pname, page, pcity, pdiseases, pnumber) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, pid);
            statement.setString(2, pname);
            statement.setInt(3, page);
            statement.setString(4, pcity);
            statement.setString(5, pdiseases);
            statement.setString(6, pnumber);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(AddPatient.this, "Patient record inserted successfully.");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(AddPatient.this, "Failed to insert patient record.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(AddPatient.this, "Error: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(AddPatient.this, "Please enter a valid age.");
        }
    }

    // Method to clear all input fields
    private void clearFields() {
        patientID.setText("");
        patientName.setText("");
        patientAge.setText("");
        patientCity.setText("");
        patientDiseases.setText("");
        patientPhoneNumber.setText("");
    }

    // Custom RoundedBorder class for button border
    private class RoundedBorder extends LineBorder {
        private int arcWidth;
        private int arcHeight;

        public RoundedBorder(int arcWidth, Color color) {
            super(color);
            this.arcWidth = arcWidth;
            this.arcHeight = arcWidth; // Rounded corners (arcHeight is same as arcWidth)
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(getLineColor());
            g2d.drawRoundRect(x, y, width - 1, height - 1, arcWidth, arcHeight);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(arcWidth + 1, arcWidth + 1, arcWidth + 2, arcWidth);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = arcWidth;
            return insets;
        }
    }
}