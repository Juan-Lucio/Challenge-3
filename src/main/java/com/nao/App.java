package com.nao;

import com.nao.controller.ResearcherController;
import com.nao.service.ScholarAPIService;
import com.nao.view.ResearcherGUI;

import javax.swing.*;

/**
 * Entry point for the application.
 * Initializes the MVC components and launches the GUI on the Event Dispatch Thread.
 */
public class App {
    public static void main(String[] args) {

        // Ensures all Swing UI actions run on the dedicated Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {

            // Creates the service layer (handles API calls)
            ScholarAPIService service = new ScholarAPIService();

            // Creates the GUI (view layer)
            ResearcherGUI gui = new ResearcherGUI();

            // Creates the controller and injects dependencies
            ResearcherController controller = new ResearcherController(service, gui);

            // Links the controller to the GUI
            gui.setController(controller);

            // Displays the window
            gui.setVisible(true);
        });
    }
}
