package com.nao.view;

import com.nao.controller.ResearcherController;
import com.nao.model.Article;
import com.nao.model.Researcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ResearcherGUI extends JFrame {

    private JTextField authorIdField;
    private JButton searchButton;
    private JLabel nameLabel, affiliationLabel, emailLabel, websiteLabel, interestsLabel, photoLabel;
    private JTable articlesTable;
    private ArticleTableModel articleTableModel;
    private ResearcherController controller;
    private Researcher currentResearcher;

    public ResearcherGUI() {
        setTitle("Google Scholar Author Lookup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(780, 640);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Author ID:"));
        authorIdField = new JTextField(30);
        top.add(authorIdField);
        searchButton = new JButton("Search");
        top.add(searchButton);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        JPanel info = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(140, 140));
        info.add(photoLabel);

        JPanel labels = new JPanel();
        labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
        nameLabel = new JLabel("Name: ");
        affiliationLabel = new JLabel("Affiliations: ");
        emailLabel = new JLabel("Email: ");
        websiteLabel = new JLabel("Website: ");
        interestsLabel = new JLabel("Interests: ");
        Font b = new Font("SansSerif", Font.BOLD, 14);
        nameLabel.setFont(b); affiliationLabel.setFont(b);
        emailLabel.setFont(b); websiteLabel.setFont(b);
        interestsLabel.setFont(b);
        labels.add(nameLabel); labels.add(Box.createVerticalStrut(6));
        labels.add(affiliationLabel); labels.add(Box.createVerticalStrut(6));
        labels.add(emailLabel); labels.add(Box.createVerticalStrut(6));
        labels.add(websiteLabel); labels.add(Box.createVerticalStrut(6));
        labels.add(interestsLabel);
        info.add(labels);
        center.add(info);

        articleTableModel = new ArticleTableModel();
        articlesTable = new JTable(articleTableModel);
        articlesTable.setRowHeight(60);
        articlesTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        JScrollPane sp = new JScrollPane(articlesTable);
        sp.setPreferredSize(new Dimension(740, 320));
        center.add(Box.createVerticalStrut(8));
        center.add(sp);
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton clear = new JButton("New Search");
        JButton exit = new JButton("Exit");
        bottom.add(clear); bottom.add(exit);
        add(bottom, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            String id = authorIdField.getText().trim();
            if (controller == null) { showError("Controller not initialized."); return; }
            if (id.isBlank()) { showError("Please enter a valid Author ID."); return; }
            controller.searchById(id);
        });

        clear.addActionListener(e -> clearAll());
        exit.addActionListener(e -> System.exit(0));
    }

    public void setController(ResearcherController controller) { this.controller = controller; }

    public void showResearcher(Researcher r, List<Article> articles) {
        this.currentResearcher = r;
        nameLabel.setText("Name: " + r.getName());
        affiliationLabel.setText("Affiliations: " + r.getAffiliations());
        emailLabel.setText("Email: " + r.getEmail());
        websiteLabel.setText("Website: " + r.getWebsite());
        interestsLabel.setText("Interests: " + String.join(", ", r.getInterests()));

        try {
            if (r.getThumbnail() != null && !r.getThumbnail().isBlank() && !r.getThumbnail().equals("N/A")) {
                Image img = ImageIO.read(new URL(r.getThumbnail())).getScaledInstance(140, 140, Image.SCALE_SMOOTH);
                photoLabel.setIcon(new ImageIcon(img));
            } else photoLabel.setIcon(null);
        } catch (IOException ex) {
            photoLabel.setIcon(null);
        }

        articleTableModel.setArticles(articles);

        SwingUtilities.invokeLater(() -> {
            if (articlesTable.getColumnModel().getColumnCount() >= 7) {
                articlesTable.getColumnModel().getColumn(4).setCellRenderer(new TextAreaRenderer());
                articlesTable.getColumnModel().getColumn(5).setCellRenderer(new TextAreaRenderer());

                articlesTable.getColumnModel().getColumn(0).setPreferredWidth(220);
                articlesTable.getColumnModel().getColumn(1).setPreferredWidth(160);
                articlesTable.getColumnModel().getColumn(2).setPreferredWidth(80);
                articlesTable.getColumnModel().getColumn(3).setPreferredWidth(60);
                articlesTable.getColumnModel().getColumn(4).setPreferredWidth(300);
                articlesTable.getColumnModel().getColumn(5).setPreferredWidth(180);
                articlesTable.getColumnModel().getColumn(6).setPreferredWidth(60);

                articlesTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
                articlesTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(modelRow -> {
                    Article a = articleTableModel.getArticleAt(modelRow);
                    if (controller != null && currentResearcher != null) controller.requestAddArticle(currentResearcher, a);
                    else showError("Controller or researcher missing.");
                }));
            }
        });
    }

    public void showError(String message) { JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE); }
    public void showInfo(String message) { JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE); }
    public void runOnUiThread(Runnable r) { SwingUtilities.invokeLater(r); }

    private void clearAll() {
        authorIdField.setText("");
        nameLabel.setText("Name: ");
        affiliationLabel.setText("Affiliations: ");
        emailLabel.setText("Email: ");
        websiteLabel.setText("Website: ");
        interestsLabel.setText("Interests: ");
        photoLabel.setIcon(null);
        articleTableModel.setArticles(null);
        currentResearcher = null;
    }
}
