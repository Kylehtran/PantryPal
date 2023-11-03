package cse110_projectstuff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecipeCreator {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";
    private static final int MAX_TOKENS = 500;
    private static final String PROMPT_FORMATTING = "Give me a step-by-step recipe "
            + "for these ingredients, with a newline between step. ";
    private static final String RECIPE_HEADER =  " Before printing the recipe, generate a title for the "
            + "recipe. Then print a list of all ingredients used, including seasonings, condiments, oils etc., "
            + "without specifying quantities. Format the title as 'Title: (name of dish)', "
                    + "and put a dash and a space in front of every ingredient.";
   

    public static void generateRecipe() throws IOException, InterruptedException {
        FileReader fr
        = new FileReader("prompt.txt"); // PLACEHOLDER NAME
        BufferedReader br = new BufferedReader(fr);
        String prompt = PROMPT_FORMATTING+br.readLine() + RECIPE_HEADER;
     // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", MAX_TOKENS);
        requestBody.put("temperature", 0.5);
        
     // Create the HTTP Client


        HttpClient client = HttpClient.newHttpClient();


        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT))    
        .header("Content-Type", "application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();
     // Send the request and receive the response
        HttpResponse<String> response = client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );
        String responseBody = response.body();
        
        JSONObject responseJson = new JSONObject(responseBody);
        JSONArray choices = responseJson.getJSONArray("choices");
        String generatedText = choices.getJSONObject(0).getString("text");

        
        System.out.println(generatedText);
        br.close();
    }
    
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException
    {
        for (int i = 0; i < 5;i++)
            RecipeCreator.generateRecipe();
        
        
    }
}
