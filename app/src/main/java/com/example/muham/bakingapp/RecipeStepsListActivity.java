package com.example.muham.bakingapp;

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

public class RecipeStepsListActivity extends AppCompatActivity implements RecipesStepsListGreenAdapter.ListItemClickListener {
RecyclerView recyclerView;
RecipesStepsListGreenAdapter adapter;
Button showIngredientsButton;
    Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps_list);
        Intent intent =getIntent();
         recipe = (Recipe)intent.getSerializableExtra("recipe");
recyclerView = (RecyclerView)findViewById(R.id.stepsRecyclerView);
ConfigRecyclerView();
showIngredientsButton = (Button)findViewById(R.id.ingreadientsButton);
ShowIngredients();
    }



    private void ConfigRecyclerView()
    {

        adapter=new RecipesStepsListGreenAdapter(recipe.getSteps(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {
Intent intent =new Intent(this,StepActivity.class);
intent.putExtra("steps",recipe.getSteps());
intent.putExtra("position",clickedItemIndex);
startActivity(intent);
    }

    void ShowIngredients()
    {
        showIngredientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RecipeStepsListActivity.this,IngredientsActivity.class);
                intent.putExtra("ingredients",recipe.getIngredients());
                startActivity(intent);
            }
        });

    }
}
