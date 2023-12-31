/**
 * Goal of RecipeParser.java:
 * 
 * This should parse the recipes generated from the GPT response and convert it
 *  into strings, into MongoDB . Ideally, this should be called when we press 
 * "save recipe" after create it and already shouldnt have duplicates
 */

package pantryPal.client.Backend;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RecipeParser {
    private String TITLE;
    private String ingredients = "Ingredients: \n";
    private String steps = "Steps: \n";
    private final String RECIPE_FILE = "src/main/resources/recipe.txt";
    public String id = RecipeManager.stringID;

    // getter methods for private variables
    public String getID() {
        return id;
    } 
    public String getTitle() {
        return TITLE;
    }
    public String getStringSteps() {
        
        return steps;
    }
    public String getStringIngredients() {
        return ingredients;
    }

    /**
     * Parses through ChatGPT generated messages and gets recipe title, ingredients, and steps
     * @throws IOException
     * @throws InterruptedException
     */
    public void parse() throws IOException, InterruptedException {
        steps = "Steps: \n";
        ingredients = "Ingredients: \n";
        FileReader fr
        = new FileReader(RECIPE_FILE); // reads recipes text generated by RecipeCreator
        BufferedReader br = new BufferedReader(fr);

        while (br.ready()) {
            String line = br.readLine();
            if (!line.isEmpty()){ // checks for non-empty line
                char first = line.charAt(0);
                switch(first) {
                    case 'T': // Title
                        TITLE = line.substring(7); // start of recipe title
                        break;
                    case '-': // Ingredients
                        //line = line.substring(1); // gets rid of dash
                        first = line.charAt(0); // check for space
                        if (first == ' ') {
                            line = line.substring(1); // gets rid of space
                        }
                        ingredients += line + "\n";
                        break;
                    case '#': // Steps
                        steps += line + "\n";
                }
            }   
        }
        System.out.println("Parsing recipe...");
        br.close();
    }
}