package com.example.muham.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.muham.bakingapp.RecipeObject.Ingredients;
import com.example.muham.bakingapp.RecipeObject.Recipe;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class GradieantsWidget extends AppWidgetProvider {
   static ArrayList<Recipe> recipes;
  static   Recipe recipe;
 static Context ccontext;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        ccontext = context;
if (recipe==null)
return;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.gradieants_widget);


        ArrayList<Ingredients> ingredients=recipe.getIngredients();
String text="";
        for (int i = 0; i < recipe.getIngredients().size(); i++){
           text+="\n"+ingredients.get(i).getIngredient()+"  "+ingredients.get(i).getQuantity()+" "+ingredients.get(i).getMeasure();
    }
        views.setTextViewText(R.id.test, text);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals("com.example.muham.bakingapp.RICEPE_CHANGED")) {
            // handle intent here
             recipe = (Recipe) intent.getSerializableExtra("recipe");
        }


    }


}

