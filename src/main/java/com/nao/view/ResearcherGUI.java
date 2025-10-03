package com.nao.view;

import com.nao.controller.ResearcherController;
import com.nao.model.Article;
import com.nao.model.Researcher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ResearcherGUI extends JFrame {
    private JTextField authorIdField;
    private JButton searchButton;
    private JLabel researcherNameLabel;
    private JLabel citationsLabel;
    private JTable articlesTable;
    private DefaultTableModel tableModel;
    private ResearcherController controller; // se inyecta luego

    public ResearcherGUI() {
        // Configuración de la ventana
        setTitle("Google Scholar Author Search");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana
        setLayout(new BorderLayout(10, 10));

        // --- Panel superior ---
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Author ID:");
        authorIdField = new JTextField(20);
        searchButton = new JButton("Buscar Autor");

        topPanel.add(label);
        topPanel.add(authorIdField);
        topPanel.add(searchButton);

        // --- Panel central con info del autor ---
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        researcherNameLabel = new JLabel("Researcher: -");
        citationsLabel = new JLabel("Citations: -");
        infoPanel.add(researcherNameLabel);
        infoPanel.add(citationsLabel);

        // --- Tabla para artículos ---
        String[] columnNames = {"Título", "Año", "Citas"};
        tableModel = new DefaultTableModel(columnNames, 0);
        articlesTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(articlesTable);

        // Añadir componentes al frame
        add(topPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.WEST);     // panel con datos del autor
        add(tableScrollPane, BorderLayout.CENTER); // tabla expandible en el centro

        // Acción del botón
        searchButton.addActionListener(e -> {
            String authorId = authorIdField.getText().trim();
            if (!authorId.isEmpty() && controller != null) {
                controller.showResearcher(authorId);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un Author ID válido.");
            }
        });
    }

    // Inyección del controlador después de crear la GUI
    public void setController(ResearcherController controller) {
        this.controller = controller;
    }

    // Método para mostrar resultados en la GUI
    public void displayResearcher(Researcher researcher) {
        if (researcher == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el autor.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar etiquetas
        researcherNameLabel.setText("Researcher: " + researcher.getName());
        citationsLabel.setText("Citations: " + researcher.getCitations());

        // Limpiar tabla antes de cargar nuevos artículos
        tableModel.setRowCount(0);

        // Llenar tabla con artículos
        for (Article article : researcher.getArticles()) {
            tableModel.addRow(new Object[]{
                    article.getTitle(),
                    article.getYear(),
                    article.getCitations()
            });
        }
    }

    // Método para mostrar errores
    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
