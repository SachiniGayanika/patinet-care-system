package medicalrecords;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class viewDetails extends JFrame {

    private JPanel contentPane;
    private JTextField totalPatientsField;
    private JTextField totalAppointmentsField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    viewDetails frame = new viewDetails();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public viewDetails() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Medical Records - Totals");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(150, 20, 300, 30);
        contentPane.add(titleLabel);

        JLabel totalPatientsLabel = new JLabel("Total Patients:");
        totalPatientsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        totalPatientsLabel.setBounds(50, 100, 150, 25);
        contentPane.add(totalPatientsLabel);

        totalPatientsField = new JTextField();
        totalPatientsField.setBounds(200, 100, 200, 25);
        totalPatientsField.setEditable(false);
        contentPane.add(totalPatientsField);

        JLabel totalAppointmentsLabel = new JLabel("Total Appointments:");
        totalAppointmentsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        totalAppointmentsLabel.setBounds(50, 150, 150, 25);
        contentPane.add(totalAppointmentsLabel);

        totalAppointmentsField = new JTextField();
        totalAppointmentsField.setBounds(200, 150, 200, 25);
        totalAppointmentsField.setEditable(false);
        contentPane.add(totalAppointmentsField);
        
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 300, 220, 30);
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

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 16));
        refreshButton.setBounds(200, 200, 100, 30);
        contentPane.add(refreshButton);

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchTotals();
            }
        });

        fetchTotals(); // Fetch totals when the window opens
    }

    private void fetchTotals() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();

            // Query to count total patients
            String countPatientsQuery = "SELECT COUNT(DISTINCT pname) AS totalPatients FROM appoinment";
            ResultSet patientsResultSet = statement.executeQuery(countPatientsQuery);
            if (patientsResultSet.next()) {
                totalPatientsField.setText(String.valueOf(patientsResultSet.getInt("totalPatients")));
            }

            // Query to count total appointments
            String countAppointmentsQuery = "SELECT COUNT(*) AS totalAppointments FROM appoinment";
            ResultSet appointmentsResultSet = statement.executeQuery(countAppointmentsQuery);
            if (appointmentsResultSet.next()) {
                totalAppointmentsField.setText(String.valueOf(appointmentsResultSet.getInt("totalAppointments")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
