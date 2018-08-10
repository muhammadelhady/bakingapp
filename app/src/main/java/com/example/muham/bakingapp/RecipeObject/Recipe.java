package com.example.muham.bakingapp.RecipeObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {

    private int id;
    private String name;
    private ArrayList<Ingredients> ingredients;
    private ArrayList<Step> steps;
    private int servings;
    private String image;

public Recipe()
{

}
    public Recipe(int id, String name, ArrayList<Ingredients> ingredients, ArrayList<Step> steps, int servings, String image )
    {
        this.id=id;
        this.name=name;
        this.ingredients=ingredients;
        this.steps=steps;
        this.servings=servings;
        this.image=image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
