package tn.elearning.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

public class TranslationService {

    private static final String API_URL = "http://127.0.0.1:5000/translate";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public String translate(String text, String targetLang) throws IOException, InterruptedException {
        // Créer le corps JSON de la requête
        JsonObject requestJson = new JsonObject();
        JsonArray textsArray = new JsonArray();
        textsArray.add(text);
        requestJson.add("texts", textsArray);
        requestJson.addProperty("target_language", targetLang);

        // Créer la requête HTTP POST
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(requestJson)))
                .build();

        // Envoyer la requête
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Erreur API: " + response.body());
        }

        // Lire la réponse JSON
        JsonObject responseJson = gson.fromJson(response.body(), JsonObject.class);
        return responseJson.getAsJsonArray("translated_texts").get(0).getAsString();
    }
}
