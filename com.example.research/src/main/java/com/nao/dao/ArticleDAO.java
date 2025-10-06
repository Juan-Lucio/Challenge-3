package com.nao.dao;

import com.nao.model.Article;

import javax.sql.DataSource;
import java.sql.*;

/**
 * DAO for article operations.
 */
public class ArticleDAO {

    private final DataSource ds;

    public ArticleDAO(DataSource ds) {
        this.ds = ds;
    }

    public int countArticlesByAuthor(String authorId) throws SQLException {
        String q = "SELECT COUNT(*) FROM articles WHERE author_id = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(q)) {
            ps.setString(1, authorId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public int countDistinctAuthors() throws SQLException {
        String q = "SELECT COUNT(DISTINCT author_id) FROM articles";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(q);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    public boolean authorExists(String authorId) throws SQLException {
        String q = "SELECT 1 FROM articles WHERE author_id = ? LIMIT 1";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(q)) {
            ps.setString(1, authorId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void insertArticle(Connection txConn, Article a) throws SQLException {
        String q = "INSERT INTO articles(author_id, title, authors, publication_date, summary, link, keywords, cited_by) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = txConn.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getAuthorId());
            ps.setString(2, a.getTitle());
            ps.setString(3, a.getAuthors());
            if (a.getPublicationDate() == null || a.getPublicationDate().isBlank()) {
                ps.setNull(4, Types.DATE);
            } else {
                ps.setDate(4, Date.valueOf(a.getPublicationDate()));
            }
            ps.setString(5, a.getSummary());
            ps.setString(6, a.getLink());
            ps.setString(7, a.getKeywords());
            ps.setInt(8, a.getCitedBy());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    a.setId(keys.getInt(1));
                }
            }
        }
    }
}
