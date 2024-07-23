package medicalrecords;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GeneratePDF extends JFrame {

    private JPanel contentPane;
    private JTextField appointmentIDField;
    private JComboBox<String> doctorComboBox;
    private JTextField dateField;
    private JTextField textField;
    private JComboBox<String> timeComboBox;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GeneratePDF frame = new GeneratePDF();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GeneratePDF() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Download PDF Doctor Appointment Scheduler");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(120, 20, 500, 30);
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

        textField = new JTextField();
        textField.setBounds(300, 246, 250, 25);
        contentPane.add(textField);

        JLabel timeLabel = new JLabel("Select Time:");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timeLabel.setBounds(50, 305, 120, 20);
        contentPane.add(timeLabel);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16));
        searchButton.setBounds(580, 80, 100, 25);
        contentPane.add(searchButton);

        String[] times = {"10:00 AM", "11:00 AM", "2:00 PM", "3:00 PM"};
        timeComboBox = new JComboBox<>(times);
        timeComboBox.setBounds(300, 305, 250, 25);
        contentPane.add(timeComboBox);

        JButton generatePDFButton = new JButton("Generate PDF");
        generatePDFButton.setFont(new Font("Arial", Font.PLAIN, 16));
        generatePDFButton.setBounds(300, 400, 150, 30);
        contentPane.add(generatePDFButton);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchAppointment();
            }
        });

        generatePDFButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generatePDF();
            }
        });
    }

    private void searchAppointment() {
        String appointmentID = appointmentIDField.getText();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT dname, date, pname, time FROM appoinment WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, appointmentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                doctorComboBox.setSelectedItem(resultSet.getString("dname"));
                dateField.setText(resultSet.getString("date"));
                textField.setText(resultSet.getString("pname"));
                timeComboBox.setSelectedItem(resultSet.getString("time"));
            } else {
                JOptionPane.showMessageDialog(null, "No appointment found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generatePDF() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Downloads"));
            document.open();

            // Adding the professional heading
            Paragraph heading = new Paragraph("ASGN Channeling Center - Colombo 01",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 26));
            heading.setAlignment(Element.ALIGN_CENTER);
            document.add(heading);

            // Adding a description
            Paragraph description = new Paragraph("This document contains the details of the appointment scheduled at the ASGN Channeling Center. Please keep this document for your records.",
                    FontFactory.getFont(FontFactory.HELVETICA, 12));
            description.setAlignment(Element.ALIGN_CENTER);
            document.add(description);

            document.add(new Paragraph("\n")); // Adding a blank line for spacing

            // Adding appointment details
            document.add(new Paragraph("Appointment Details", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("Appointment ID: " + appointmentIDField.getText()));
            document.add(new Paragraph("Doctor: " + doctorComboBox.getSelectedItem().toString()));
            document.add(new Paragraph("Date: " + dateField.getText()));
            document.add(new Paragraph("Patient Name: " + textField.getText()));
            document.add(new Paragraph("Time: " + timeComboBox.getSelectedItem().toString()));

            document.close();
            JOptionPane.showMessageDialog(null, "PDF Generated Successfully!");
        } catch (DocumentException | FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
