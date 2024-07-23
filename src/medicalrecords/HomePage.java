package medicalrecords;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HomePage frame = new HomePage();
                    frame.setVisible(true);
                    frame.setTitle("HOME PAGE");
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
    public HomePage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Medical Records System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(120, 20, 400, 30);
        contentPane.add(titleLabel);

        JButton addPatientButton = new JButton("ADD NEW PATIENT");
        addPatientButton.setBounds(180, 80, 220, 30);
        addPatientButton.setBackground(new Color(0, 153, 255)); // Blue color
        addPatientButton.setForeground(Color.WHITE); // White text
        addPatientButton.setFocusPainted(false); // Remove focus border
        addPatientButton.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font
        addPatientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open AddPatient.java
                AddPatient addPatient = new AddPatient();
                addPatient.setVisible(true);
                dispose(); // Close the current window
            }
        });
        contentPane.add(addPatientButton);

        JButton scheduleAppointmentButton = new JButton("SCHEDULE APPOINTMENT");
        scheduleAppointmentButton.setBounds(180, 130, 220, 30);
        scheduleAppointmentButton.setBackground(new Color(0, 153, 255));
        scheduleAppointmentButton.setForeground(Color.WHITE);
        scheduleAppointmentButton.setFocusPainted(false);
        scheduleAppointmentButton.setFont(new Font("Arial", Font.BOLD, 14));
        scheduleAppointmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open DoctorAppointmen.java
                DoctorAppointmen doctorAppointment = new DoctorAppointmen();
                doctorAppointment.setVisible(true);
                dispose(); // Close the current window
            }
        });
        contentPane.add(scheduleAppointmentButton);

        JButton issuePrescriptionButton = new JButton("ISSUE PRESCRIPTION");
        issuePrescriptionButton.setBounds(180, 180, 220, 30);
        issuePrescriptionButton.setBackground(new Color(0, 153, 255));
        issuePrescriptionButton.setForeground(Color.WHITE);
        issuePrescriptionButton.setFocusPainted(false);
        issuePrescriptionButton.setFont(new Font("Arial", Font.BOLD, 14));
        issuePrescriptionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open GenaratePdf.java (assuming this handles prescription generation)
            	GeneratePDF  generatePdf = new GeneratePDF ();
                generatePdf.setVisible(true);
                dispose(); // Close the current window
            }
        });  
        contentPane.add(issuePrescriptionButton);

        JButton scanQRCodeButton = new JButton("SCAN QR CODE");
        scanQRCodeButton.setBounds(180, 230, 220, 30);
        scanQRCodeButton.setBackground(new Color(0, 153, 255));
        scanQRCodeButton.setForeground(Color.WHITE);
        scanQRCodeButton.setFocusPainted(false);
        scanQRCodeButton.setFont(new Font("Arial", Font.BOLD, 14));
        scanQRCodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open QRScanInterface.java
                QRScanInterface qrScanInterface = new QRScanInterface();
                qrScanInterface.setVisible(true);
                dispose(); // Close the current window
            }
        });
        contentPane.add(scanQRCodeButton);

        JButton dashboardButton = new JButton("DASHBOARD");
        dashboardButton.setBounds(180, 280, 220, 30);
        dashboardButton.setBackground(new Color(0, 153, 255));
        dashboardButton.setForeground(Color.WHITE);
        dashboardButton.setFocusPainted(false);
        dashboardButton.setFont(new Font("Arial", Font.BOLD, 14));
        dashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open ViewDetails.java (assuming this is the dashboard or patient details view)
            	viewDetails viewDetails = new viewDetails();
               viewDetails.setVisible(true);
                dispose(); // Close the current window
            }
        });
        contentPane.add(dashboardButton);

        // Add the image
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(50, 50, 500, 520);
        ImageIcon imageIcon = new ImageIcon("F:/DIPLOMA/ead1/QR Code Medical Records/images/home.jpeg");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(imageIcon);
        contentPane.add(imageLabel);
    }
}
