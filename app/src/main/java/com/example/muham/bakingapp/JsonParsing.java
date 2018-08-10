package com.example.muham.bakingapp;

import com.example.muham.bakingapp.RecipeObject.Ingredients;
import com.example.muham.bakingapp.RecipeObject.Recipe;
import com.example.muham.bakingapp.RecipeObject.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParsing {

    public static ArrayList<Recipe> ParseJson(String Json) throws JSONException {
        ArrayList <Recipe>recipes=new ArrayList<>();

        JSONArray jsonData= new JSONArray(Json);
        for (int i = 0 ; i <jsonData.length();i++)
        {
            Recipe recipe= new Recipe();
           JSONObject recipeJsonObject= jsonData.getJSONObject(i);

           int id = recipeJsonObject.getInt("id");
            recipe.setId(id);


           String name=recipeJsonObject.getString("name");
            recipe.setName(name);

           int servings=recipeJsonObject.getInt("servings");
            recipe.setServings(servings);

           String image=recipeJsonObject.getString("image");
            recipe.setImage(image);


           JSONArray ingreadiantsArray=recipeJsonObject.getJSONArray("ingredients");
           ArrayList<Ingredients> ingredients = new ArrayList<>();
           for (int j=0;j<ingreadiantsArray.length();j++)
           {
            JSONObject ingredintobject= ingreadiantsArray.getJSONObject(j);
            String quantity= ingredintobject.getString("quantity");
            String measure= ingredintobject.getString("measure");
            String ingredient=ingredintobject.getString("ingredient");
            ingredients.add(new Ingredients(quantity,measure,ingredient));

           }
            recipe.setIngredients(ingredients);





           JSONArray stepsJsonArray=recipeJsonObject.getJSONArray("steps");
            ArrayList<Step> steps = new ArrayList<>();
            for (int j  =  0 ;  j  < stepsJsonArray.length();j++)
            {
                JSONObject stepObject= stepsJsonArray.getJSONObject(j);
                int stepId = stepObject.getInt("id");
                String shortDescription=stepObject.getString("shortDescription");
                String description=stepObject.getString("description");
                String videoURL = stepObject.getString("videoURL");
                String thumbnailURL=stepObject.getString("thumbnailURL");
                steps.add(new Step(stepId,shortDescription,description,videoURL,thumbnailURL));

            }
            recipe.setSteps(steps);

            recipes.add(recipe);

        }

        return recipes;
    }
}
