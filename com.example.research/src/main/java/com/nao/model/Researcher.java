package com.nao.model;

import java.util.List;

/**
 * Researcher model — displayed in GUI only (not persisted).
 */
public class Researcher {
    private final String id;
    private final String name;
    private final String affiliations;
    private final String email;
    private final String website;
    private final String thumbnail;
    private final List<String> interests;

    public Researcher(String id, String name, String affiliations, String email,
                      String website, String thumbnail, List<String> interests) {
        this.id = id;
        this.name = name;
        this.affiliations = affiliations;
        this.email = email;
        this.website = website;
        this.thumbnail = thumbnail;
        this.interests = interests;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAffiliations() { return affiliations; }
    public String getEmail() { return email; }
    public String getWebsite() { return website; }
    public String getThumbnail() { return thumbnail; }
    public List<String> getInterests() { return interests; }
}
