package diskschedulinggui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class DiskSchedulingGUI extends JFrame {
    private JLabel tracksLabel;
    private JTextField tracksField;
    private JLabel requestLabel;
    private JTextField requestField;
    private JLabel currentPositionLabel;
    private JTextField currentPositionField;
    private JLabel movementLabel;
    private JTextField movementField;
    private JButton calculateButton;
    private JLabel resultLabel;
    private JTextField resultField;
    private JButton exitButton;

    public DiskSchedulingGUI() {
setTitle("C-Scan Disk Scheduling");
    setSize(1366, 768);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridBagLayout());
    getContentPane().setBackground(new Color(216, 191, 216));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); 

        // Heading Label
        JLabel headingLabel = new JLabel("C-Scan Disk Scheduling");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_START;
        add(headingLabel, gbc);

        // Create and configure UI components
        tracksLabel = new JLabel("Number of Tracks:");
        tracksLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tracksField = new JTextField();
        tracksField.setFont(new Font("Arial", Font.PLAIN, 24));
        tracksField.setPreferredSize(new Dimension(400, 40));
        requestLabel = new JLabel("Request Queue (Comma-separated):");
        requestLabel.setFont(new Font("Arial", Font.BOLD, 24));
        requestField = new JTextField();
        requestField.setFont(new Font("Arial", Font.PLAIN, 24));
        requestField.setPreferredSize(new Dimension(400, 40));
        currentPositionLabel = new JLabel("Current Position:");
        currentPositionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        currentPositionField = new JTextField();
        currentPositionField.setFont(new Font("Arial", Font.PLAIN, 24));
        currentPositionField.setPreferredSize(new Dimension(400, 40));
        movementLabel = new JLabel("Movement (L/R):");
        movementLabel.setFont(new Font("Arial", Font.BOLD, 24));
        movementField = new JTextField();
        movementField.setFont(new Font("Arial", Font.PLAIN, 24));
        movementField.setPreferredSize(new Dimension(400, 40));
        calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 32));
        calculateButton.setBackground(new Color(0, 128, 0)); 
        calculateButton.setForeground(Color.WHITE); 
        resultLabel = new JLabel("Total Track Movement:");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resultField = new JTextField();
        resultField.setFont(new Font("Arial", Font.PLAIN, 24));
        resultField.setEditable(false);
        resultField.setPreferredSize(new Dimension(400, 40)); 
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 32));
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE); 

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int totalTracks = Integer.parseInt(tracksField.getText());
                String[] requestQueue = requestField.getText().split(",");
                int currentPosition = Integer.parseInt(currentPositionField.getText());
                String movement = movementField.getText();

                int totalMovement = calculateCScan(totalTracks, requestQueue, currentPosition, movement);
                resultField.setText(String.valueOf(totalMovement));
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the JFrame
            }
        });

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(tracksLabel, gbc);
        gbc.gridx = 1;
        add(tracksField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(requestLabel, gbc);
        gbc.gridx = 1;
        add(requestField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(currentPositionLabel, gbc);
        gbc.gridx = 1;
        add(currentPositionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(movementLabel, gbc);
        gbc.gridx = 1;
        add(movementField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(calculateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(resultLabel, gbc);

        gbc.gridx = 1;
        add(resultField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(exitButton, gbc);

        setVisible(true);
    }

    private int calculateCScan(int totalTracks, String[] requestQueue, int currentPosition, String movement) {
        int[] requests = new int[requestQueue.length];
        for (int i = 0; i < requestQueue.length; i++) {
            requests[i] = Integer.parseInt(requestQueue[i]);
        }

        Arrays.sort(requests);

        int totalMovement = 0;
        int index = Arrays.binarySearch(requests, currentPosition);

        if (index < 0) {
            index = -index - 1;
        }

        if (movement.equalsIgnoreCase("R")) {
            for (int i = index; i < requests.length; i++) {
                totalMovement += Math.abs(requests[i] - currentPosition);
                currentPosition = requests[i];
            }

            totalMovement += Math.abs(totalTracks - currentPosition);
            currentPosition = 0;

            for (int i = 0; i < index; i++) {
                totalMovement += Math.abs(requests[i] - currentPosition);
                currentPosition = requests[i];
            }
        } else if (movement.equalsIgnoreCase("L")) {
            for (int i = index - 1; i >= 0; i--) {
                totalMovement += Math.abs(requests[i] - currentPosition);
                currentPosition = requests[i];
            }

            totalMovement += Math.abs(currentPosition);
            currentPosition = totalTracks;

            for (int i = requests.length - 1; i >= index; i--) {
                totalMovement += Math.abs(requests[i] - currentPosition);
                currentPosition = requests[i];
            }
        }

        return totalMovement;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DiskSchedulingGUI();
            }
        });
    }

}



    


    

