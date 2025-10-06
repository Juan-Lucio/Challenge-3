package com.nao.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nao.model.Article;
import com.nao.model.Researcher;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Robust ScholarAPIService:
 * - Tries many JSON paths for articles, summary and keywords.
 * - Optionally prints raw response when DEBUG_SERPAPI env var is "true".
 * - Keeps retry/backoff and timeouts.
 */
public class ScholarAPIService {

    private static final String BASE_URL = "https://serpapi.com/search?engine=google_scholar_author";
    private static final String API_KEY = System.getenv().getOrDefault("c890bf91b6d489022daec82e89d99fe9741682c5b2c61922652a76cd47dda02b", "c890bf91b6d489022daec82e89d99fe9741682c5b2c61922652a76cd47dda02b");
    private static final boolean DEBUG = "true".equalsIgnoreCase(System.getenv().getOrDefault("DEBUG_SERPAPI", "false"));

    private final ObjectMapper mapper = new ObjectMapper();

    private String textOr(JsonNode node, String def) {
        if (node == null || node.isMissingNode() || node.isNull()) return def;
        String s = node.asText();
        return (s == null || s.isBlank()) ? def : s;
    }

    private List<String> arrayToStringList(JsonNode node) {
        List<String> out = new ArrayList<>();
        if (node == null || node.isMissingNode() || node.isNull()) return out;
        if (node.isArray()) {
            for (JsonNode n : node) {
                String v = textOr(n, null);
                if (v != null && !v.isBlank()) out.add(v);
            }
        } else {
            String s = textOr(node, null);
            if (s != null && !s.isBlank()) out.add(s);
        }
        return out;
    }

    public Researcher getResearcherById(String authorId) throws IOException, InterruptedException {
        ensureApiKey();
        String url = BASE_URL + "&author_id=" + URLEncoder.encode(authorId, StandardCharsets.UTF_8) + "&api_key=" + API_KEY;
        String response = httpGetWithRetry(url);
        if (DEBUG) {
            System.out.println("DEBUG_SERPAPI_RESPONSE (researcher):");
            System.out.println(response);
        }
        JsonNode root = mapper.readTree(response);
        JsonNode authorNode = root.path("author");
        if (authorNode.isMissingNode() || authorNode.path("name").isMissingNode()) {
            throw new IOException("No author found with ID: " + authorId);
        }

        String name = textOr(authorNode.path("name"), "Unknown");
        String affiliations = textOr(authorNode.path("affiliations"), "N/A");
        String email = textOr(authorNode.path("email"), "N/A");
        String website = textOr(authorNode.path("website"), "N/A");
        String thumbnail = textOr(authorNode.path("thumbnail"), "N/A");

        List<String> interests = new ArrayList<>();
        JsonNode arr = authorNode.path("interests");
        if (arr.isArray()) {
            for (JsonNode i : arr) interests.add(textOr(i.path("title"), textOr(i, "N/A")));
        }

        return new Researcher(authorId, name, affiliations, email, website, thumbnail, interests);
    }

    /**
     * Main entry: returns list of Article objects parsed from many possible JSON shapes.
     */
    public List<Article> getArticlesByAuthorId(String authorId) throws IOException, InterruptedException {
        ensureApiKey();
        String url = BASE_URL + "&author_id=" + URLEncoder.encode(authorId, StandardCharsets.UTF_8) + "&api_key=" + API_KEY;
        String response = httpGetWithRetry(url);
        if (DEBUG) {
            System.out.println("DEBUG_SERPAPI_RESPONSE (articles):");
            System.out.println(response);
        }
        JsonNode root = mapper.readTree(response);

        // Candidate nodes where SerpApi (or other wrappers) might put publications
        List<JsonNode> candidateNodes = new ArrayList<>();
        Collections.addAll(candidateNodes,
                root.path("articles"),
                root.path("papers"),
                root.path("publications"),
                root.path("publications_list"),
                root.path("items"),
                root.path("results"),
                root.path("author").path("papers"),
                root.path("author").path("publications")
        );

        List<Article> out = new ArrayList<>();
        Set<JsonNode> seen = new HashSet<>(); // avoid duplicates

        for (JsonNode candidate : candidateNodes) {
            if (candidate == null || candidate.isMissingNode() || !candidate.isArray()) continue;
            for (JsonNode an : candidate) {
                if (an == null || an.isMissingNode()) continue;
                if (seen.contains(an)) continue;
                seen.add(an);

                String title = findTitle(an);
                String authors = findAuthors(an);
                String pubDate = findPubDate(an);
                String summary = findSummary(an);
                String link = findLink(an);
                String keywords = findKeywords(an);
                int citedBy = findCitedBy(an);

                Article art = new Article(authorId, title, authors, pubDate, summary, link, keywords, citedBy);
                out.add(art);
            }
        }

        // Fallback: if none found in candidates, try scanning root for any array of objects that look like articles
        if (out.isEmpty()) {
            Iterator<JsonNode> fields = root.elements();
            while (fields.hasNext()) {
                JsonNode node = fields.next();
                if (node.isArray()) {
                    for (JsonNode an : node) {
                        if (an.isObject()) {
                            String title = findTitle(an);
                            if (title != null && !title.isBlank() && !title.equalsIgnoreCase("Untitled")) {
                                String authors = findAuthors(an);
                                String pubDate = findPubDate(an);
                                String summary = findSummary(an);
                                String link = findLink(an);
                                String keywords = findKeywords(an);
                                int citedBy = findCitedBy(an);
                                Article art = new Article(authorId, title, authors, pubDate, summary, link, keywords, citedBy);
                                out.add(art);
                            }
                        }
                    }
                }
            }
        }

        return out;
    }

    /* ----------------- helper extraction methods ----------------- */

    private String findTitle(JsonNode an) {
        return firstNonBlank(
                textOr(an.path("title"), null),
                textOr(an.path("paper_title"), null),
                textOr(an.path("name"), null),
                textOr(an.path("heading"), null),
                textOr(an.path("paper").path("title"), null),
                textOr(an.path("metadata").path("title"), null),
                "Untitled"
        );
    }

    private String findAuthors(JsonNode an) {
        // authors may be array or string or nested
        List<String> candidates = new ArrayList<>();
        JsonNode authorsNode = an.path("authors");
        if (authorsNode.isArray()) {
            for (JsonNode a : authorsNode) {
                String n = textOr(a.path("name"), textOr(a, null));
                if (n != null && !n.isBlank()) candidates.add(n);
            }
        } else {
            String s = textOr(authorsNode, null);
            if (s != null && !s.isBlank()) candidates.add(s);
        }
        if (candidates.isEmpty()) {
            // try other keys
            String s = firstNonBlank(
                    textOr(an.path("author"), null),
                    textOr(an.path("creator"), null),
                    textOr(an.path("byline"), null),
                    textOr(an.path("metadata").path("authors"), null)
            );
            if (s != null && !s.isBlank()) return s;
        }
        return String.join(", ", candidates);
    }

    private String findPubDate(JsonNode an) {
        String pd = firstNonBlank(
                textOr(an.path("publication_date"), null),
                textOr(an.path("pub_date"), null),
                textOr(an.path("date"), null),
                textOr(an.path("year"), null),
                textOr(an.path("metadata").path("date"), null)
        );
        if (pd == null) return null;
        // normalize year-only to yyyy-01-01
        if (pd.matches("\\d{4}")) return pd + "-01-01";
        return pd;
    }

    private String findSummary(JsonNode an) {
        // try many keys and nested possibilities
        String s = firstNonBlank(
                textOr(an.path("abstract"), null),
                textOr(an.path("paper_abstract"), null),
                textOr(an.path("snippet"), null),
                textOr(an.path("summary"), null),
                textOr(an.path("description"), null),
                textOr(an.path("meta_description"), null),
                textOr(an.path("excerpt"), null),
                textOr(an.path("short_description"), null),
                textOr(an.path("metadata").path("abstract"), null),
                textOr(an.path("metadata").path("description"), null)
        );
        if (s == null || s.isBlank()) {
            // sometimes the JSON stores a 'snippet' inside nested objects: try to concat candidates
            List<String> parts = new ArrayList<>();
            String p1 = textOr(an.path("snippet"), null);
            String p2 = textOr(an.path("summary"), null);
            if (p1 != null && !p1.isBlank()) parts.add(p1);
            if (p2 != null && !p2.isBlank()) parts.add(p2);
            if (!parts.isEmpty()) return String.join(" ", parts);
            return "";
        }
        return s;
    }

    private String findKeywords(JsonNode an) {
        // Several possible shapes: array of strings, array of objects, single string, nested fields
        List<String> kw = new ArrayList<>();

        JsonNode kns = an.path("keywords");
        if (kns.isArray()) {
            for (JsonNode kn : kns) {
                String k = textOr(kn, null);
                if (k != null && !k.isBlank()) kw.add(k);
                else {
                    // object form {title:..., value:...}
                    String t = textOr(kn.path("title"), textOr(kn.path("value"), null));
                    if (t != null && !t.isBlank()) kw.add(t);
                }
            }
        } else {
            // try tags, categories, topics, fields_of_study, labels
            String joined = firstNonBlank(
                    textOr(an.path("tags"), null),
                    textOr(an.path("categories"), null),
                    textOr(an.path("keyword_string"), null),
                    textOr(an.path("topics"), null),
                    textOr(an.path("labels"), null),
                    textOr(an.path("fields_of_study"), null)
            );
            if (joined != null && !joined.isBlank()) {
                // if it's comma/semicolon separated, keep as-is; otherwise return as single keyword
                return joined;
            }

            // try nested arrays
            JsonNode tagsNode = an.path("tags");
            if (tagsNode.isArray()) {
                for (JsonNode t : tagsNode) {
                    String v = textOr(t, null);
                    if (v != null && !v.isBlank()) kw.add(v);
                }
            }

            JsonNode topicsNode = an.path("topics");
            if (topicsNode.isArray()) {
                for (JsonNode t : topicsNode) {
                    String v = textOr(t.path("name"), textOr(t, null));
                    if (v != null && !v.isBlank()) kw.add(v);
                }
            }
        }

        // As last resort, attempt to extract keywords from title by selecting nouns / words (very rough): NOT implemented to avoid false data.
        if (kw.isEmpty()) return "";

        // deduplicate and return CSV
        LinkedHashSet<String> dedup = new LinkedHashSet<>();
        for (String k : kw) {
            String cleaned = k.trim();
            if (!cleaned.isEmpty()) dedup.add(cleaned);
        }
        return String.join(", ", dedup);
    }

    private String findLink(JsonNode an) {
        return firstNonBlank(
                textOr(an.path("link"), null),
                textOr(an.path("url"), null),
                textOr(an.path("source"), null),
                textOr(an.path("metadata").path("url"), null),
                ""
        );
    }

    private int findCitedBy(JsonNode an) {
        int v = 0;
        if (an.has("cited_by") && an.path("cited_by").has("value")) {
            v = an.path("cited_by").path("value").asInt(0);
        } else if (an.has("citation_count")) {
            v = an.path("citation_count").asInt(0);
        } else if (an.has("cited") && an.path("cited").has("count")) {
            v = an.path("cited").path("count").asInt(0);
        }
        return v;
    }

    private String firstNonBlank(String... candidates) {
        for (String s : candidates) {
            if (s != null && !s.isBlank()) return s;
        }
        return null;
    }

    /* ----------------- networking ----------------- */

    private void ensureApiKey() {
        if (API_KEY == null || API_KEY.isBlank()) throw new IllegalStateException("SERPAPI_KEY environment variable is not set.");
    }

    private String httpGetWithRetry(String url) throws IOException, InterruptedException {
        int attempts = 0;
        int max = 3;
        long wait = 1000L;
        while (true) {
            attempts++;
            try {
                return Request.get(url)
                        .connectTimeout(Timeout.ofSeconds(5))
                        .responseTimeout(Timeout.ofSeconds(10))
                        .execute()
                        .returnContent()
                        .asString();
            } catch (IOException ex) {
                if (attempts >= max) throw ex;
                Thread.sleep(wait);
                wait *= 2;
            }
        }
    }

    // compatibility alias
    public List<Article> getArticlesByResearcher(String authorId) throws IOException, InterruptedException {
        return getArticlesByAuthorId(authorId);
    }
}
