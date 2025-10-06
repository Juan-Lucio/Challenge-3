package com.nao.controller;

import com.nao.model.Article;
import com.nao.model.Researcher;
import com.nao.service.DBService;
import com.nao.service.ScholarAPIService;
import com.nao.view.ResearcherGUI;

import java.util.List;

/**
 * Controller mediates between GUI and services.
 * It fetches researcher + articles via ScholarAPIService and delegates DB persistence to DBService.
 * Researcher objects are NOT stored in DB (only displayed).
 */
public class ResearcherController {

    private final ScholarAPIService service;
    private final DBService dbService;
    private final ResearcherGUI view;

    public ResearcherController(ScholarAPIService service, DBService dbService, ResearcherGUI view) {
        this.service = service;
        this.dbService = dbService;
        this.view = view;
    }

    /**
     * Search by author ID: fetch researcher info and articles, then update view.
     */
    public void searchById(String authorId) {
        try {
            Researcher researcher = service.getResearcherById(authorId);
            List<Article> articles = service.getArticlesByAuthorId(authorId);
            view.showResearcher(researcher, articles);
        } catch (Exception e) {
            view.showError("Error retrieving data: " + e.getMessage());
        }
    }

    /**
     * Request to persist an article under DB rules.
     * Researcher is NOT persisted.
     */
    public void requestAddArticle(Researcher researcher, Article article) {
        try {
            dbService.addArticleWithConstraints(article);
            view.showInfo("Article added to database successfully.");
        } catch (IllegalStateException ex) {
            view.showError(ex.getMessage());
        } catch (Exception ex) {
            view.showError("Error saving to DB: " + ex.getMessage());
        }
    }
}
