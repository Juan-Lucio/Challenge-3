package com.nao.controller;

import com.nao.model.Researcher;
import com.nao.service.ScholarAPIService;
import com.nao.view.ResearcherView;
import com.nao.view.ResearcherGUI;

public class ResearcherController {
    private ScholarAPIService service;
    private ResearcherView consoleView;
    private ResearcherGUI gui;

    public ResearcherController(ScholarAPIService service, ResearcherView consoleView, ResearcherGUI gui) {
        this.service = service;
        this.consoleView = consoleView;
        this.gui = gui;
    }

    public void showResearcher(String authorId) {
        try {
            Researcher researcher = service.getResearcherData(authorId);

            // Mostrar en consola
            consoleView.displayResearcher(researcher);

            // Mostrar en GUI
            gui.displayResearcher(researcher);

        } catch (Exception e) {
            gui.displayError("Error: " + e.getMessage());
            System.err.println("Error: " + e.getMessage());
        }
    }
}
