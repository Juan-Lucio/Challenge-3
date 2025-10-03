package com.nao.model;

import com.nao.model.Article;

import java.util.List;

public class Researcher {
    private String id;
    private String name;
    private int citations;
    private List<Article> articles;

    // Getters y Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getCitations() {
        return citations;
    }
    public void setCitations(int citations) {
        this.citations = citations;
    }

    public List<Article> getArticles() {
        return articles;
    }
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}

