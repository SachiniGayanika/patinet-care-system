package medicalrecords;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


public class DoctorAppointmen extends JFrame {

    private JPanel contentPane;
    private JComboBox<String> doctorComboBox;
    private JComboBox<String> timeComboBox;
    private JTextField dateField;
    private JTextField textField;
    private JTextField appointmentIDField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DoctorAppointmen frame = new DoctorAppointmen();
                    frame.setVisible(true);
                    frame.setTitle("DOCTOR APPOINTMENT");
                    frame.getContentPane().setLayout(null); // Avoid using null layout
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
    public DoctorAppointmen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Doctor Appointment Scheduler");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(120, 20, 400, 30);
        contentPane.add(titleLabel);

        JLabel appointmentIDLabel = new JLabel("Appointment ID:");
        appointmentIDLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        appointmentIDLabel.setBounds(50, 80, 120, 20);
        contentPane.add(appointmentIDLabel);

        appointmentIDField = new JTextField();
        appointmentIDField.setBounds(300, 80, 250, 25);
        contentPane.add(appointmentIDField);

        JLabel doctorLabel = new JLabel("Select Doctor:");
        doctorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        doctorLabel.setBounds(50, 134, 120, 20);
        contentPane.add(doctorLabel);

        String[] doctors = {"Dr. Smith", "Dr. Johnson", "Dr. Williams"};
        doctorComboBox = new JComboBox<>(doctors);
        doctorComboBox.setBounds(300, 134, 250, 25);
        contentPane.add(doctorComboBox);
        
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

        JLabel dateLabel = new JLabel("Enter Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        dateLabel.setBounds(50, 185, 300, 20);
        contentPane.add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(300, 185, 250, 25);
        contentPane.add(dateField);

        JLabel pname = new JLabel("Enter Patient Name:");
        pname.setFont(new Font("Arial", Font.PLAIN, 16));
        pname.setBounds(50, 246, 300, 20);
        contentPane.add(pname);

        textField = new JTextField();
        textField.setBounds(300, 246, 250, 25);
        contentPane.add(textField);

        JLabel timeLabel = new JLabel("Select Time:");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timeLabel.setBounds(50, 305, 120, 20);
        contentPane.add(timeLabel);

        String[] times = {"10:00 AM", "11:00 AM", "2:00 PM", "3:00 PM"};
        timeComboBox = new JComboBox<>(times);
        timeComboBox.setBounds(300, 305, 250, 25);
        contentPane.add(timeComboBox);

        JButton scheduleButton = new JButton("Schedule Appointment");
        scheduleButton.setBounds(50, 400, 220, 30);
        scheduleButton.setBackground(new Color(0, 153, 255));
        scheduleButton.setForeground(Color.WHITE);
        scheduleButton.setFocusPainted(false);
        scheduleButton.setFont(new Font("Arial", Font.BOLD, 14));
        scheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scheduleAppointment();
            }
        });
        contentPane.add(scheduleButton);

        JButton qrButton = new JButton("Generate QR");
        qrButton.setBounds(350, 400, 220, 30);
        qrButton.setBackground(new Color(0, 153, 255));
        qrButton.setForeground(Color.WHITE);
        qrButton.setFocusPainted(false);
        qrButton.setFont(new Font("Arial", Font.BOLD, 14));
        qrButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateQRCode();
            }
        });
        contentPane.add(qrButton);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(570, 80, 90, 25);
        searchButton.setBackground(new Color(0, 153, 255));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchAppointment();
            }
        });
        contentPane.add(searchButton);
    }

    private void scheduleAppointment() {
        String appointmentID = appointmentIDField.getText();
        String doctor = (String) doctorComboBox.getSelectedItem();
        String date = dateField.getText();
        String time = (String) timeComboBox.getSelectedItem();
        String patientName = textField.getText();

        if (appointmentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the appointment ID.");
            return;
        }

        if (patientName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name.");
            return;
        }

        if (date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the date in YYYY-MM-DD format.");
            return;
        }

        // Insert appointment details into the database
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO appoinment (id, dname, date, pname, time, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, appointmentID);
            pstmt.setString(2, doctor);
            pstmt.setString(3, date);
            pstmt.setString(4, patientName);
            pstmt.setString(5, time);
            pstmt.setString(6, "Scheduled"); // Initial status

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Appointment scheduled successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to schedule appointment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }

        // You can implement further logic here, like showing a confirmation message.
        String message = String.format("Appointment %s scheduled with %s on %s at %s for %s.", appointmentID, doctor, date, time, patientName);
        JOptionPane.showMessageDialog(this, message, "Appointment Scheduled", JOptionPane.INFORMATION_MESSAGE);
    }

    private void searchAppointment() {
        String appointmentID = appointmentIDField.getText();

        if (appointmentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the appointment ID.");
            return;
        }

        // Retrieve appointment details from the database
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM appoinment WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, appointmentID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String doctor = rs.getString("dname");
                String date = rs.getString("date");
                String patientName = rs.getString("pname");
                String time = rs.getString("time");

                // Populate the fields with retrieved data
                doctorComboBox.setSelectedItem(doctor);
                dateField.setText(date);
                textField.setText(patientName);
                timeComboBox.setSelectedItem(time);

                JOptionPane.showMessageDialog(this, "Appointment found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No appointment found with ID: " + appointmentID, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }

    private void generateQRCode() {
        String appointmentID = appointmentIDField.getText();

        if (appointmentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the appointment ID.");
            return;
        }

        // Retrieve appointment details from the database
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM appoinment WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, appointmentID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String doctor = rs.getString("dname");
                String date = rs.getString("date");
                String patientName = rs.getString("pname");
                String time = rs.getString("time");

                // Generate QR code data
                String qrData = String.format("Appointment ID: %s\nDoctor: %s\nDate: %s\nTime: %s\nPatient Name: %s", appointmentID, doctor, date, time, patientName);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 200, 200);

                // Ask user to save the QR code
                int userResponse = JOptionPane.showConfirmDialog(this, "Do you want to download the QR code?", "QR Code", JOptionPane.YES_NO_OPTION);
                if (userResponse == JOptionPane.YES_OPTION) {
                    String filePath = "D:\\QR\\qr" + appointmentID + "_QR.png";
                    Path path = FileSystems.getDefault().getPath(filePath);
                 // After generating BitMatrix bitMatrix
                    MatrixToImageWriter.writeToFile(bitMatrix, "PNG", filePath);

                    JOptionPane.showMessageDialog(this, "QR code generated and saved successfully.", "QR Code", JOptionPane.INFORMATION_MESSAGE);

                    // Update appointment status to "Issued"
                    String updateQuery = "UPDATE appoinment SET status = ? WHERE id = ?";
                    PreparedStatement updatePstmt = conn.prepareStatement(updateQuery);
                    updatePstmt.setString(1, "Issued");
                    updatePstmt.setString(2, appointmentID);
                    updatePstmt.executeUpdate();
                }
            } else {
                JOptionPane.showMessageDialog(this, "No appointment found with ID: " + appointmentID, "QR Code", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException | WriterException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating QR code: " + e.getMessage());
        }
    }
}
