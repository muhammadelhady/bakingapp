package com.example.muham.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.example.muham.bakingapp.Adapters.GreenAdapter;
import com.example.muham.bakingapp.RecipeObject.Recipe;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GreenAdapter.ListItemClickListener{
    ArrayList<Recipe> recipes ;
GreenAdapter  greenAdapter;
Toast toast;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FetchData();
        recyclerView=findViewById(R.id.recipesRecyclerView);


    }
    public void UpdateWidget ()
    {
        Intent intent = new Intent("com.example.muham.bakingapp.RICEPE_CHANGED");
        intent.putExtra("recipe",recipes.get(0));
        getApplicationContext().sendBroadcast(intent);
    }
    private void ConfigRecyclerView()
    {
        greenAdapter=new GreenAdapter(recipes,this);
        DetrmineDeviceScreenSize();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(greenAdapter);
    }

    private void DetrmineDeviceScreenSize()
    {
        double diagonalInches =GetDiagonalInches();
        if (diagonalInches>=6.5){
            //tablet Size
            recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        }else{
            // smaller device
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        }



    private void FetchData()
    {
        if(isOnline()==true)
        {
            new conection().execute();
        }
        else
        {
            Toast.makeText(this,"No InterNet Connection",Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected() ;
    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {



        double diagonalInches =GetDiagonalInches();
        Recipe recipe = recipes.get(clickedItemIndex);
        Intent intent;
        if (diagonalInches>=6.5){
            //tablet Size
             intent =  new Intent(this,TabletActivity.class);
        }else{
            // smaller device
             intent =new Intent(this,RecipeStepsListActivity.class);
        }

        intent.putExtra("recipe",recipe);
        startActivity(intent);


    }
   public  Double GetDiagonalInches()
   {
       DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

       float yInches= metrics.heightPixels/metrics.ydpi;
       float xInches= metrics.widthPixels/metrics.xdpi;
       return  Math.sqrt(xInches*xInches + yInches*yInches);

   }


    class conection extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {

            try {
                recipes = JsonParsing.ParseJson(HttpConnections.ConnectHttpUrl(getResources().getString(R.string.API_URL)));
                AddingPhotos();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

     @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
      ConfigRecyclerView();
         UpdateWidget();

        }



        void AddingPhotos()
        {
            for (int i = 0 ; i < recipes.size();i++)
            {
                Recipe recipe= new Recipe();
                recipe=recipes.get(i);
                switch (recipes.get(i).getName())
                {
                    case "Nutella Pie":
                        recipe.setImage(R.drawable.nutellapie+"");

                        break;
                    case "Brownies":
                        recipe.setImage(R.drawable.brownies+"");
                        break;
                    case "Yellow Cake":
                        recipe.setImage(R.drawable.yellowcake+"");
                        break;
                    case "Cheesecake":
                        recipe.setImage(R.drawable.cheesecake+"");
                        break;
                }
                recipes.remove(i);
                recipes.add(i,recipe);
            }
        }
    }
}

