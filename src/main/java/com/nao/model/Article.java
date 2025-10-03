package com.nao.model;

public class Article {
    private String title;
    private int year;
    private int citations;

    // Getters y Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public int getCitations() {
        return citations;
    }
    public void setCitations(int citations) {
        this.citations = citations;
    }
}