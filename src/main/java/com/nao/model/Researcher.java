package com.nao.model;

import java.util.List;

/**
 * The Researcher class is a plain data model (POJO)
 * representing a Google Scholar author and their key information.
 */
public class Researcher {

    // Unique identifier assigned to the author by Google Scholar
    private String id;
    // Author’s full name
    private String name;
    // Author’s institutional or organizational affiliations
    private String affiliations;
    // Author’s verified email address (if available)
    private String email;
    // Author’s personal or institutional website
    private String website;
    // URL to the author's profile picture
    private String thumbnail;
    // List of author’s research interests or fields
    private List<String> interests;

    /**
     * Constructs a fully defined Researcher object.
     */
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

    // Getter methods — provide read-only access to private fields
    public String getId() { return id; }
    public String getName() { return name; }
    public String getAffiliations() { return affiliations; }
    public String getEmail() { return email; }
    public String getWebsite() { return website; }
    public String getThumbnail() { return thumbnail; }
    public List<String> getInterests() { return interests; }
}
