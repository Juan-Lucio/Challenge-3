package com.nao;

import com.nao.controller.ResearcherController;
import com.nao.service.DBService;
import com.nao.service.ScholarAPIService;
import com.nao.view.ResearcherGUI;

import javax.swing.*;

/**
 * Application entry point.
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScholarAPIService api = new ScholarAPIService();
            DBService db = new DBService();
            ResearcherGUI gui = new ResearcherGUI();
            ResearcherController controller = new ResearcherController(api, db, gui);
            gui.setController(controller);
            gui.setVisible(true);
        });
    }
}
