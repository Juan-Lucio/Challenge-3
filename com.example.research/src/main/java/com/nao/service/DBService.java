package com.nao.service;

import com.nao.dao.ArticleDAO;
import com.nao.dao.DatabaseConfig;
import com.nao.model.Article;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Business rules:
 * - Only articles are persisted.
 * - Max 3 articles per author.
 * - Max 2 distinct authors in articles table.
 */
public class DBService {

    private final DataSource ds;
    private final ArticleDAO articleDAO;

    public DBService() {
        this.ds = DatabaseConfig.getDataSource();
        this.articleDAO = new ArticleDAO(ds);
    }

    public void addArticleWithConstraints(Article article) throws Exception {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false);

            boolean exists = articleDAO.authorExists(article.getAuthorId());
            if (!exists) {
                int distinct = articleDAO.countDistinctAuthors();
                if (distinct >= 2) {
                    throw new IllegalStateException("Database already contains maximum number of distinct authors (2).");
                }
            }

            int current = articleDAO.countArticlesByAuthor(article.getAuthorId());
            if (current >= 3) {
                throw new IllegalStateException("Author already has 3 articles stored in database.");
            }

            articleDAO.insertArticle(conn, article);
            conn.commit();
        } catch (SQLException | RuntimeException ex) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ignored) {}
            throw ex;
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ignored) {}
        }
    }
}
