package com.nao.view;

import com.nao.model.Article;
import com.nao.model.Researcher;

public class ResearcherView {
    public void displayResearcher(Researcher researcher) {
        System.out.println("Researcher: " + researcher.getName());
        System.out.println("Citations: " + researcher.getCitations());
        System.out.println("Articles:");
        for (Article article : researcher.getArticles()) {
            System.out.println(" - " + article.getTitle() + " (" + article.getYear() + ")");
        }
    }
}