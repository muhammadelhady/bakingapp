package com.example.muham.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.muham.bakingapp.Adapters.IngredientGreenAdapter;
import com.example.muham.bakingapp.Adapters.RecipesStepsListGreenAdapter;
import com.example.muham.bakingapp.RecipeObject.Ingredients;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {
ArrayList<Ingredients> ingredients=new ArrayList<>();
RecyclerView ingredientsRecyclerView;
IngredientGreenAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
       ingredients=(ArrayList<Ingredients>) getIntent().getSerializableExtra("ingredients");
       ingredientsRecyclerView = (RecyclerView)findViewById(R.id.ingredientsRecyclerView);
        ConfigRecyclerView();
    }

    private void ConfigRecyclerView()
    {

        adapter=new IngredientGreenAdapter(ingredients);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ingredientsRecyclerView.setHasFixedSize(false);
        ingredientsRecyclerView.setAdapter(adapter);
    }

}
