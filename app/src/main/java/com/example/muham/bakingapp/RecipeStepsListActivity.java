package com.example.muham.bakingapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.muham.bakingapp.Adapters.GreenAdapter;
import com.example.muham.bakingapp.Adapters.RecipesStepsListGreenAdapter;
import com.example.muham.bakingapp.RecipeObject.Recipe;

public class RecipeStepsListActivity extends AppCompatActivity  {

    Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps_list);
        Intent intent =getIntent();
         recipe = (Recipe)intent.getSerializableExtra("recipe");

RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
Bundle bundle = new Bundle();
bundle.putSerializable("recipe",recipe);
recipeStepsFragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.containerr,recipeStepsFragment).commit();
    }








}
