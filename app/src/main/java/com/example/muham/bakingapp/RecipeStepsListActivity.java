package com.example.muham.bakingapp;

import android.app.FragmentManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.muham.bakingapp.Adapters.GreenAdapter;
import com.example.muham.bakingapp.Adapters.RecipesStepsListGreenAdapter;
import com.example.muham.bakingapp.RecipeObject.Recipe;

import java.io.Serializable;

public class RecipeStepsListActivity extends AppCompatActivity  {

    Recipe recipe;
    Fragment recipeStepsFragment;
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
UpdateWidget();
recipeStepsFragment.setArguments(bundle);
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            recipeStepsFragment = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");

        }
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.containerr,recipeStepsFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "myFragmentName", recipeStepsFragment);
    }


    public void UpdateWidget ()
    {
       Intent intent = new Intent("com.example.muham.bakingapp.RICEPE_CHANGED");
        intent.putExtra("recipe",recipe);
        getApplicationContext().sendBroadcast(intent);
        UpdateWidget2();
    }

    void UpdateWidget2()
    {
        Intent intent = new Intent(this, GradieantsWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
// since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), GradieantsWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      finish();

        return true;
    }
}
