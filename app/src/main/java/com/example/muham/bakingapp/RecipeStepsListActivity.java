package com.example.muham.bakingapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
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

import java.io.Serializable;

public class RecipeStepsListActivity extends AppCompatActivity  {

    Recipe recipe;
    RecipeStepsFragment recipeStepsFragment;
    android.support.v4.app.FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps_list);
        Intent intent =getIntent();
         recipe = (Recipe)intent.getSerializableExtra("recipe");


             recipeStepsFragment = new RecipeStepsFragment();


Bundle bundle = new Bundle();
bundle.putSerializable("recipe",recipe);
recipeStepsFragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.containerr,recipeStepsFragment).commit();
    }


}
