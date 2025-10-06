package com.nao.view;

import com.nao.model.Article;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Table model for articles. Columns:
 * Title | Authors | Pub Date | Cited By | Summary | Keywords | Add
 */
public class ArticleTableModel extends AbstractTableModel {

    private final String[] columns = {"Title", "Authors", "Pub Date", "Cited By", "Summary", "Keywords", "Add"};
    private final List<Article> articles = new ArrayList<>();

    @Override
    public int getRowCount() {
        return articles.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Article a = articles.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> a.getTitle();
            case 1 -> a.getAuthors();
            case 2 -> a.getPublicationDate();
            case 3 -> a.getCitedBy();
            case 4 -> a.getSummary();
            case 5 -> a.getKeywords();
            case 6 -> "Add";
            default -> "";
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 6; // only "Add" column editable
    }

    public Article getArticleAt(int row) {
        return articles.get(row);
    }

    public void setArticles(List<Article> newArticles) {
        articles.clear();
        if (newArticles != null) articles.addAll(newArticles);
        fireTableDataChanged();
    }
}
