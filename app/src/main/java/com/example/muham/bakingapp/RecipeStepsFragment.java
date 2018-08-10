package com.example.muham.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.muham.bakingapp.Adapters.RecipesStepsListGreenAdapter;
import com.example.muham.bakingapp.RecipeObject.Recipe;

public class RecipeStepsFragment extends Fragment implements RecipesStepsListGreenAdapter.ListItemClickListener {
    RecyclerView recyclerView;
    RecipesStepsListGreenAdapter adapter;
    Button showIngredientsButton;
    Recipe recipe;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_steps_fragment,container,false);
Bundle bundle= getArguments();
        recipe=(Recipe) bundle.getSerializable("recipe");
        recyclerView = (RecyclerView)rootView.findViewById(R.id.stepsRecyclerView);
        ConfigRecyclerView();
        showIngredientsButton = (Button)rootView.findViewById(R.id.ingreadientsButton);
        ShowIngredients();
        return rootView;

    }

    public  RecipeStepsFragment()
    {

    }


    private void ConfigRecyclerView()
    {

        adapter=new RecipesStepsListGreenAdapter(recipe.getSteps(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
    void ShowIngredients()
    {
        showIngredientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getActivity(),IngredientsActivity.class);
               intent.putExtra("ingredients",recipe.getIngredients());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {

        if (GetDiagonalInches()<6.5)
        {
            Intent intent =new Intent(getActivity(),StepActivity.class);
            intent.putExtra("steps",recipe.getSteps());
            intent.putExtra("position",clickedItemIndex);
            startActivity(intent);
        }
        else {
            Bundle bundle =new Bundle();
            bundle.putSerializable("steps",recipe.getSteps());
            bundle.putInt("position",clickedItemIndex);
            StepFragment stepFragment = new StepFragment();
            stepFragment.setArguments(bundle);
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.tabletStepContainer,stepFragment
            ).commit();


        }

    }


    public  Double GetDiagonalInches()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        return  Math.sqrt(xInches*xInches + yInches*yInches);

    }
}
