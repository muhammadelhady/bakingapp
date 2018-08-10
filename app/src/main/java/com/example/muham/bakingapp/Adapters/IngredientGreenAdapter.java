package com.example.muham.bakingapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.muham.bakingapp.R;
import com.example.muham.bakingapp.RecipeObject.Ingredients;
import com.example.muham.bakingapp.RecipeObject.Recipe;

import java.util.ArrayList;

public class IngredientGreenAdapter extends RecyclerView.Adapter<IngredientGreenAdapter.IngredientsViewHolder> {

ArrayList<Ingredients> ingredients=new ArrayList<>();

    public IngredientGreenAdapter(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;

    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredientrow,parent,false);
        IngredientGreenAdapter.IngredientsViewHolder ingredientsViewHolder = new IngredientsViewHolder(view);
        return  ingredientsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {

        String ingredient = ingredients.get(position).getIngredient();
        String measure=ingredients.get(position).getMeasure();
        String quantity=ingredients.get(position).getQuantity();
        holder.Bind(ingredient,measure,quantity);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder
    {

        TextView ingredientTextView,measureTextView,quantityTextView;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ingredientTextView=(TextView)itemView.findViewById(R.id.ingredientTextView);
            measureTextView=(TextView)itemView.findViewById(R.id.measureTextView);
            quantityTextView=(TextView)itemView.findViewById(R.id.quantityTextView);


        }

        void Bind(String ingredient,String measure, String quantity)
        {

          ingredientTextView.setText(ingredient);
          measureTextView.setText(measure);
          quantityTextView.setText(quantity);
        }


    }
}
