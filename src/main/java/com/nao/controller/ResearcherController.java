package com.nao.controller;

// Imports the Researcher model class to represent author data
import com.nao.model.Researcher;
// Imports the service layer that interacts with the Scholar API
import com.nao.service.ScholarAPIService;
// Imports the GUI class for displaying researcher information
import com.nao.view.ResearcherGUI;

/**
 * The ResearcherController class acts as the intermediary between
 * the service layer (data retrieval) and the view layer (GUI display).
 * It follows the MVC (Model–View–Controller) design pattern.
 */
public class ResearcherController {

    // Service responsible for making API requests to Google Scholar
    private final ScholarAPIService service;
    // View responsible for user interface interactions
    private final ResearcherGUI view;

    /**
     * Constructor initializes the controller with references
     * to the service and the GUI view components.
     */
    public ResearcherController(ScholarAPIService service, ResearcherGUI view) {
        this.service = service;
        this.view = view;
    }

    /**
     * Handles the logic of searching for a researcher by their author ID.
     * Delegates data retrieval to the service and passes results to the view.
     */
    public void searchById(String authorId) {
        try {
            // Requests researcher data from the API using the service layer
            Researcher researcher = service.getResearcherById(authorId);

            // Sends the retrieved data to the GUI for visualization
            view.showResearcher(researcher);

        } catch (Exception e) {
            // Displays an error message in the GUI if something goes wrong
            view.showError("Error retrieving data: " + e.getMessage());
        }
    }
}
