package com.nao.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nao.model.Article;
import com.nao.model.Researcher;
import org.apache.hc.client5.http.fluent.Request;

import java.util.ArrayList;
import java.util.List;

public class ScholarAPIService {

    // 🔑 Solo contiene lo común, el authorId se agrega después
    private static final String BASE_URL = "https://serpapi.com/search?engine=google_scholar_author";
    private static final String API_KEY = "c890bf91b6d489022daec82e89d99fe9741682c5b2c61922652a76cd47dda02b"; // cámbialo por tu API Key real

    public Researcher getResearcherData(String authorId) throws Exception {
        // Construyo la URL de manera dinámica
        String url = BASE_URL + "&author_id=" + authorId + "&api_key=" + API_KEY;

        // Hago la petición GET
        String response = Request.get(url)
                .execute()
                .returnContent()
                .asString();

        // 🔎 Debug: imprime el JSON de respuesta (útil para pruebas)
        System.out.println("DEBUG JSON Response:\n" + response);

        System.out.println("DEBUG - Respuesta de API:\n" + response);

        // Parseo JSON con Jackson
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);

        // ⚠️ Dependerá de la estructura del JSON devuelto por SerpAPI

        JsonNode authorNode = root.path("author");
        if (authorNode.isMissingNode()) {
            throw new Exception("No se encontró información del autor con ID: " + authorId);
        }

        Researcher researcher = new Researcher();
        researcher.setId(authorId);
        researcher.setName(authorNode.path("name").asText("Desconocido"));

// Aquí debes revisar si el JSON tiene esta estructura
        researcher.setCitations(
                authorNode.path("cited_by").path("table").path(0).path("citations").asInt(0)
        );

// Artículos
        List<Article> articles = new ArrayList<>();
        JsonNode articlesNode = root.path("articles");
        if (articlesNode.isArray()) {
            for (JsonNode paper : articlesNode) {
                Article article = new Article();
                article.setTitle(paper.path("title").asText("Título no disponible"));
                article.setYear(paper.path("year").asInt(0));
                article.setCitations(paper.path("cited_by").path("value").asInt(0));
                articles.add(article);
            }
        }
        researcher.setArticles(articles);


        return researcher;
    }
}
