//Username: admin
// Password: password
import java.io.*;
import java.util.List; // Explicitly importing List from java.util
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OnlineReservationSystemGUI {
    // Centralized "Database" (In memory simulation for simplicity)
    private static Map<String, Reservation> reservations = new HashMap<>();
    private static List<User> users = new ArrayList<>();
    
    // GUI components
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextArea outputArea;
    private JTextField nameField, trainNumberField, classTypeField, dateField, fromField, toField, pnrField;

    public static void main(String[] args) {
        // Initialize a sample user for login
        users.add(new User("admin", "password"));
        new OnlineReservationSystemGUI().createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Online Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(220, 220, 255)); // Light blue background

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));
        loginPanel.setBackground(new Color(200, 200, 255)); // Light blue for the panel
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        loginPanel.add(new JLabel("Login ID:"));
        usernameField = new JTextField();
        loginPanel.add(usernameField);

        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);
        
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(100, 150, 255)); // Button color
        loginButton.setForeground(Color.WHITE); // Button text color
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        loginPanel.add(loginButton);
        
        frame.add(loginPanel, BorderLayout.NORTH);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(Color.WHITE);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void login() {
        String loginId = usernameField.getText();
        String password = new String(passwordField.getPassword());

        for (User user : users) {
            if (user.loginId.equals(loginId) && user.password.equals(password)) {
                outputArea.setText("Login successful! Welcome, " + loginId);
                showMainMenu();
                return;
            }
        }

        outputArea.setText("Invalid login credentials.");
    }

    private void showMainMenu() {
        JFrame menuFrame = new JFrame("Main Menu");
        menuFrame.setSize(400, 300);
        menuFrame.setLayout(new GridLayout(4, 1));
        menuFrame.getContentPane().setBackground(new Color(180, 220, 255)); // Light blue for menu

        JButton makeReservationButton = new JButton("Make Reservation");
        makeReservationButton.setBackground(new Color(100, 150, 255)); // Button color
        makeReservationButton.setForeground(Color.WHITE);
        makeReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showReservationForm();
            }
        });
        
        JButton cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.setBackground(new Color(100, 150, 255));
        cancelReservationButton.setForeground(Color.WHITE);
        cancelReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCancellationForm();
            }
        });
        
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(255, 50, 50)); // Red for exit button
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                frame.dispose();
            }
        });
        
        menuFrame.add(makeReservationButton);
        menuFrame.add(cancelReservationButton);
        menuFrame.add(exitButton);
        menuFrame.setVisible(true);
    }

    private void showReservationForm() {
        JFrame reservationFrame = new JFrame("Make Reservation");
        reservationFrame.setSize(400, 400);
        reservationFrame.setLayout(new GridLayout(7, 2));
        reservationFrame.getContentPane().setBackground(new Color(255, 255, 204)); // Light yellow

        reservationFrame.add(new JLabel("Name:"));
        nameField = new JTextField();
        reservationFrame.add(nameField);

        reservationFrame.add(new JLabel("Train Number:"));
        trainNumberField = new JTextField();
        reservationFrame.add(trainNumberField);

        reservationFrame.add(new JLabel("Class Type:"));
        classTypeField = new JTextField();
        reservationFrame.add(classTypeField);

        reservationFrame.add(new JLabel("Date of Journey (dd-mm-yyyy):"));
        dateField = new JTextField();
        reservationFrame.add(dateField);

        reservationFrame.add(new JLabel("From:"));
        fromField = new JTextField();
        reservationFrame.add(fromField);

        reservationFrame.add(new JLabel("To:"));
        toField = new JTextField();
        reservationFrame.add(toField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(100, 150, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeReservation();
                reservationFrame.dispose();
            }
        });
        
        reservationFrame.add(submitButton);
        reservationFrame.setVisible(true);
    }

    private void makeReservation() {
        String name = nameField.getText();
        String trainNumber = trainNumberField.getText();
        String trainName = getTrainName(trainNumber); // Dummy function for train name
        String classType = classTypeField.getText();
        String dateOfJourney = dateField.getText();
        String from = fromField.getText();
        String to = toField.getText();

        // Generate a unique PNR number
        String pnr = UUID.randomUUID().toString();

        // Create reservation object and store it in the "database"
        Reservation reservation = new Reservation(pnr, name, trainNumber, trainName, classType, dateOfJourney, from, to);
        reservations.put(pnr, reservation);

        outputArea.setText("Reservation successful! Your PNR is " + pnr);
    }

    private void showCancellationForm() {
        JFrame cancellationFrame = new JFrame("Cancel Reservation");
        cancellationFrame.setSize(400, 200);
        cancellationFrame.setLayout(new GridLayout(2, 2));
        cancellationFrame.getContentPane().setBackground(new Color(255, 204, 204)); // Light red

        cancellationFrame.add(new JLabel("Enter your PNR number:"));
        pnrField = new JTextField();
        cancellationFrame.add(pnrField);

        JButton cancelButton = new JButton("Cancel Reservation");
        cancelButton.setBackground(new Color(100, 150, 255));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelReservation();
                cancellationFrame.dispose();
            }
        });
        
        cancellationFrame.add(cancelButton);
        cancellationFrame.setVisible(true);
    }

    private void cancelReservation() {
        String pnr = pnrField.getText();

        // Find the reservation
        if (reservations.containsKey(pnr)) {
            Reservation reservation = reservations.get(pnr);
            outputArea.setText("Reservation Details:\n" + reservation);
            reservations.remove(pnr);
            outputArea.append("\nReservation canceled successfully!");
        } else {
            outputArea.setText("Invalid PNR number! No reservation found.");
        }
    }

    // Dummy function to get train name based on train number
    private String getTrainName(String trainNumber) {
        // In real application, this will query a database. Here, we'll assume static names.
        switch (trainNumber) {
            case "12345": return "Super Fast Express";
            case "54321": return "Mountain Express";
            case "67890": return "Coastal Express";
            default: return "Unknown Train";
        }
    }
}

// User class to handle login functionality
class User {
    String loginId;
    String password;

    User(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}

// Reservation class to hold the reservation details
class Reservation {
    String pnr;
    String name;
    String trainNumber;
    String trainName;
    String classType;
    String dateOfJourney;
    String from;
    String to;

    Reservation(String pnr, String name, String trainNumber, String trainName, String classType, String dateOfJourney, String from, String to) {
        this.pnr = pnr;
        this.name = name;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "PNR: " + pnr +
               "\nName: " + name +
               "\nTrain Number: " + trainNumber +
               "\nTrain Name: " + trainName +
               "\nClass: " + classType +
               "\nDate of Journey: " + dateOfJourney +
               "\nFrom: " + from +
               "\nTo: " + to;
    }
}
