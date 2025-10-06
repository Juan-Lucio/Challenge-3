package com.nao.model;

/**
 * Article model — persisted in DB.
 */
public class Article {
    private Integer id;
    private final String authorId;
    private final String title;
    private final String authors;
    private final String publicationDate; // yyyy-MM-dd or null
    private final String summary;
    private final String link;
    private final String keywords;
    private final Integer citedBy;

    public Article(String authorId, String title, String authors, String publicationDate,
                   String summary, String link, String keywords, Integer citedBy) {
        this.id = null;
        this.authorId = authorId;
        this.title = title;
        this.authors = authors;
        this.publicationDate = publicationDate;
        this.summary = summary;
        this.link = link;
        this.keywords = keywords;
        this.citedBy = (citedBy == null) ? 0 : citedBy;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getAuthorId() { return authorId; }
    public String getTitle() { return title; }
    public String getAuthors() { return authors; }
    public String getPublicationDate() { return publicationDate; }
    public String getSummary() { return summary; }
    public String getLink() { return link; }
    public String getKeywords() { return keywords; }
    public Integer getCitedBy() { return citedBy; }
}
