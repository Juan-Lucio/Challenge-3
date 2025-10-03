package com.nao;

import com.nao.controller.ResearcherController;
import com.nao.service.ScholarAPIService;
import com.nao.view.ResearcherGUI;
import com.nao.view.ResearcherView;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        ScholarAPIService service = new ScholarAPIService();
        ResearcherView consoleView = new ResearcherView();

        SwingUtilities.invokeLater(() -> {
            // 1. Crear GUI vacía
            ResearcherGUI gui = new ResearcherGUI();

            // 2. Crear controlador y enlazar con la GUI
            ResearcherController controller = new ResearcherController(service, consoleView, gui);

            // 3. Inyectar controller en la GUI
            gui.setController(controller);

            // 4. Mostrar GUI
            gui.setVisible(true);
        });
    }
}
