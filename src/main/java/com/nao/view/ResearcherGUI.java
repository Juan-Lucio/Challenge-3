package com.nao.view;

import com.nao.model.Researcher;
import com.nao.controller.ResearcherController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * ResearcherGUI builds a Swing-based graphical interface
 * for interacting with the ResearcherController.
 * It allows users to input an author ID, trigger searches,
 * and display the retrieved researcher data.
 */
public class ResearcherGUI extends JFrame {

    // GUI input field for entering the Google Scholar author ID
    private JTextField authorIdField;
    // Button for initiating the search action
    private JButton searchButton;
    // Labels for displaying retrieved researcher details
    private JLabel nameLabel;
    private JLabel affiliationLabel;
    private JLabel emailLabel;
    private JLabel websiteLabel;
    private JLabel interestsLabel;
    private JLabel photoLabel;
    // Control buttons for repeating or exiting the program
    private JButton repeatButton;
    private JButton exitButton;

    // Controller reference for delegating logic
    private ResearcherController controller;

    /**
     * Constructor sets up and initializes the Swing window layout.
     */
    public ResearcherGUI() {
        setTitle("Google Scholar Author Lookup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null); // Centers the window on screen
        setBackground(Color.WHITE);

        // --- Top Panel: input field and search button ---
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Author ID:");
        authorIdField = new JTextField(20);
        searchButton = new JButton("Search");
        inputPanel.add(label);
        inputPanel.add(authorIdField);
        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.NORTH);

        // --- Center Panel: displays researcher info ---
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        // Label to show the researcher’s photo
        photoLabel = new JLabel();
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Labels initialized with placeholders
        nameLabel = new JLabel("Name: ");
        affiliationLabel = new JLabel("Affiliations: ");
        emailLabel = new JLabel("Email: ");
        websiteLabel = new JLabel("Website: ");
        interestsLabel = new JLabel("Interests: ");

        // Sets consistent font style for all labels
        Font bold = new Font("SansSerif", Font.BOLD, 14);
        nameLabel.setFont(bold);
        affiliationLabel.setFont(bold);
        emailLabel.setFont(bold);
        websiteLabel.setFont(bold);
        interestsLabel.setFont(bold);

        // Adds all components to the central info panel
        infoPanel.add(photoLabel);
        infoPanel.add(Box.createVerticalStrut(10)); // Adds spacing
        infoPanel.add(nameLabel);
        infoPanel.add(affiliationLabel);
        infoPanel.add(emailLabel);
        infoPanel.add(websiteLabel);
        infoPanel.add(interestsLabel);

        add(infoPanel, BorderLayout.CENTER);

        // --- Bottom Panel: control buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout());
        repeatButton = new JButton("New Search");
        exitButton = new JButton("Exit");
        buttonPanel.add(repeatButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- Event listeners for buttons ---
        searchButton.addActionListener(e -> {
            String id = authorIdField.getText().trim();

            // Ensures controller is linked before executing a search
            if (controller == null) {
                showError("Controller not initialized.");
                return;
            }

            // Executes search only if user entered a valid ID
            if (!id.isEmpty()) {
                controller.searchById(id);
            } else {
                showError("Please enter a valid Author ID.");
            }
        });

        // Clears the GUI for a new search
        repeatButton.addActionListener(e -> clearFields());
        // Exits the application
        exitButton.addActionListener(e -> System.exit(0));
    }

    /**
     * Links this view to its controller instance.
     */
    public void setController(ResearcherController controller) {
        this.controller = controller;
    }

    /**
     * Displays researcher data retrieved from the controller.
     */
    public void showResearcher(Researcher r) {
        nameLabel.setText("Name: " + r.getName());
        affiliationLabel.setText("Affiliations: " + r.getAffiliations());
        emailLabel.setText("Email: " + r.getEmail());
        websiteLabel.setText("Website: " + r.getWebsite());
        interestsLabel.setText("Interests: " + String.join(", ", r.getInterests()));

        try {
            // Loads the researcher’s image if available
            if (!r.getThumbnail().equals("N/A")) {
                Image img = ImageIO.read(new URL(r.getThumbnail()))
                        .getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                photoLabel.setIcon(new ImageIcon(img));
            } else {
                photoLabel.setIcon(null);
            }
        } catch (IOException e) {
            photoLabel.setIcon(null);
        }
    }

    /**
     * Displays an error popup with the provided message.
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Clears all displayed fields for a new search session.
     */
    private void clearFields() {
        authorIdField.setText("");
        nameLabel.setText("Name: ");
        affiliationLabel.setText("Affiliations: ");
        emailLabel.setText("Email: ");
        websiteLabel.setText("Website: ");
        interestsLabel.setText("Interests: ");
        photoLabel.setIcon(null);
    }
}
