package com.nao.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nao.model.Researcher;
import org.apache.hc.client5.http.fluent.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ScholarAPIService handles communication with the external Google Scholar API
 * using SerpApi. It parses JSON responses into usable Java objects.
 */
public class ScholarAPIService {

    // Base endpoint for Google Scholar Author search via SerpApi
    private static final String BASE_URL = "https://serpapi.com/search?engine=google_scholar_author";

    // API key required for authenticating with SerpApi
    private static final String API_KEY = "c890bf91b6d489022daec82e89d99fe9741682c5b2c61922652a76cd47dda02b";

    /**
     * Fetches author data from the SerpApi service based on author ID.
     * @param authorId Google Scholar author ID
     * @return Researcher object with parsed author information
     * @throws IOException if the API call or parsing fails
     */
    public Researcher getResearcherById(String authorId) throws IOException {

        // Constructs the full API request URL including parameters
        String url = BASE_URL + "&author_id=" + authorId + "&api_key=" + API_KEY;

        // Executes HTTP GET request and retrieves the response as a String
        String response = Request.get(url).execute().returnContent().asString();

        // Jackson ObjectMapper for parsing JSON data into a tree structure
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);

        // Extracts the "author" node from the JSON response
        JsonNode authorNode = root.path("author");

        // Validates that the response contains an author object
        if (authorNode.isMissingNode() || authorNode.path("name").isMissingNode()) {
            throw new IOException("Author not found with ID: " + authorId);
        }

        // Extracts author data fields with default fallbacks
        String name = authorNode.path("name").asText("Unknown");
        String affiliations = authorNode.path("affiliations").asText("N/A");
        String email = authorNode.path("email").asText("N/A");
        String website = authorNode.path("website").asText("N/A");
        String thumbnail = authorNode.path("thumbnail").asText("N/A");

        // Parses the array of research interests, if available
        List<String> interests = new ArrayList<>();
        JsonNode interestsArray = authorNode.path("interests");
        if (interestsArray.isArray()) {
            for (JsonNode i : interestsArray) {
                // Extracts each interest’s title text
                interests.add(i.path("title").asText("N/A"));
            }
        }

        // Returns a populated Researcher model instance
        return new Researcher(authorId, name, affiliations, email, website, thumbnail, interests);
    }
}
