package medicalrecords;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.zxing.*;
import com.google.zxing.common.*;
import com.google.zxing.qrcode.*;

public class QRScanInterface extends JFrame {
    private JLabel qrImageLabel;

    public QRScanInterface() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Scan QR Code");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        qrImageLabel = new JLabel();
        panel.add(qrImageLabel, BorderLayout.CENTER);

        JButton scanButton = new JButton("Scan QR Code");
        scanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        BufferedImage image = ImageIO.read(selectedFile);
                        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
                        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
                        String appointmentID = qrCodeResult.getText();
                        insertAttendance(appointmentID); // Call insert method instead of update
                    } catch (IOException | NotFoundException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error scanning QR code: " + ex.getMessage());
                    }
                }
            }
        });
        panel.add(scanButton, BorderLayout.SOUTH);

        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current frame (QRScanInterface) and open the home page
                dispose(); // Close current frame
                HomePage homePage = new HomePage();
                homePage.setVisible(true); // Show the home page
            }
        });
        panel.add(backButton, BorderLayout.NORTH);

        add(panel);
    }

    private void insertAttendance(String appointmentID) {
        // Insert attendance into the database
        try (Connection conn = DatabaseConnection.getConnection()) {
            String insertQuery = "INSERT INTO qr (id, attendance) VALUES (?, 'Present')";
            PreparedStatement insertPstmt = conn.prepareStatement(insertQuery);
            insertPstmt.setString(1, appointmentID);
            insertPstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "New record inserted for Appointment ID: " + appointmentID);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    QRScanInterface frame = new QRScanInterface();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
